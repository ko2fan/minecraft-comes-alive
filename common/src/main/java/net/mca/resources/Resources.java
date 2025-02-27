package net.mca.resources;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonParseException;
import net.mca.MCA;
import net.mca.entity.interaction.InteractionPredicate;
import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Serial;
import java.lang.reflect.Type;

public interface Resources {
    String RESOURCE_PREFIX = "assets/mca/";

    Gson GSON = new GsonBuilder()
            //.registerTypeAdapterFactory(RecordTypeAdapterFactory.INSTANCE)
            .registerTypeAdapter(InteractionPredicate.class, InteractionPredicateTypeAdapter.INSTANCE)
            .create();

    static String read(String path) throws IOException {
        return IOUtils.toString(new InputStreamReader(MCA.class.getClassLoader().getResourceAsStream(RESOURCE_PREFIX + path)));
    }

    static <T> T read(String path, Type type) throws BrokenResourceException {
        try {
            return GSON.fromJson(Resources.read(path), type);
        } catch (IOException | JsonParseException e) {
            throw new BrokenResourceException(path, e);
        }
    }

    static <T> T read(String path, Class<T> type) throws BrokenResourceException {
        return read(path, (Type)type);
    }

    class BrokenResourceException extends Exception {
        @Serial
        private static final long serialVersionUID = -7371322414731622879L;

        BrokenResourceException(String path, Throwable cause) {
            super("Unable to load resource from path " + path, cause);
        }
    }
}
