package net.mca.network.c2s;

import net.mca.Config;
import net.mca.MCA;
import net.mca.cobalt.network.Message;
import net.mca.util.WorldUtils;
import net.mca.util.compat.FuzzyPositionsCompat;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.network.packet.s2c.play.PlayerPositionLookS2CPacket;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ChunkTicketType;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.Heightmap;

import java.io.Serial;
import java.util.EnumSet;

public class DestinyMessage implements Message {
    @Serial
    private static final long serialVersionUID = -782119062565197963L;

    private final String location;
    private final boolean isClosing;

    public DestinyMessage(String location, boolean isClosing) {
        this.location = location;
        this.isClosing = isClosing;
    }

    public DestinyMessage(String location) {
        this(location, false);
    }

    public DestinyMessage(boolean isClosing) {
        this(null, isClosing);
    }

    @Override
    public void receive(ServerPlayerEntity player) {
        if (isClosing) {
            player.removeStatusEffect(StatusEffects.INVISIBILITY);
            player.removeStatusEffect(StatusEffects.HEALTH_BOOST);
        }

        if (Config.getInstance().allowDestinyTeleportation && location != null) {
            MCA.executorService.execute(() -> {
                WorldUtils.getClosestStructurePosition(player.getWorld(), player.getBlockPos(), new Identifier(location), 128).ifPresent(pos -> {
                    player.getWorld().getWorldChunk(pos);
                    pos = player.getWorld().getTopPosition(Heightmap.Type.WORLD_SURFACE, pos);
                    pos = FuzzyPositionsCompat.upWhile(pos, player.getWorld().getHeight(), p -> player.getWorld().getBlockState(p).shouldSuffocate(player.getWorld(), p));
                    pos = FuzzyPositionsCompat.downWhile(pos, 1, p -> !player.getWorld().getBlockState(p.down()).isFullCube(player.getWorld(), p));

                    ChunkPos chunkPos = new ChunkPos(pos);
                    player.getWorld().getChunkManager().addTicket(ChunkTicketType.POST_TELEPORT, chunkPos, 1, player.getId());
                    player.networkHandler.requestTeleport(pos.getX(), pos.getY(), pos.getZ(), player.getYaw(), player.getPitch(), EnumSet.noneOf(PlayerPositionLookS2CPacket.Flag.class));

                    //set spawn
                    player.setSpawnPoint(player.world.getRegistryKey(), pos, 0.0f, true, false);
                    if (player.world.getServer() != null && player.world.getServer().isHost(player.getGameProfile())) {
                        player.getWorld().setSpawnPos(pos, 0.0f);
                    }
                });
            });
        }
    }
}
