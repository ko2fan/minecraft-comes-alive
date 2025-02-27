package net.mca.network.s2c;

import net.mca.ClientProxy;
import net.mca.cobalt.network.Message;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;

import java.io.Serial;

public class ShowToastRequest implements Message {
    @Serial
    private static final long serialVersionUID = 1055734972572313374L;

    private final String title;
    private final String message;

    public ShowToastRequest(String title, String message) {
        this.title = title;
        this.message = message;
    }

    public Text getTitle() {
        return new TranslatableText(title);
    }

    public Text getMessage() {
        return new TranslatableText(message);
    }

    @Override
    public void receive() {
        ClientProxy.getNetworkHandler().handleToastMessage(this);
    }
}
