package serializations;
import com.google.gson.*;
import java.lang.reflect.Type;

public class AbstractClassAdapter implements
        JsonSerializer<Object>, JsonDeserializer<Object> {

    private static final String ClASS_TYPE = "CLASS_TYPE";

    @Override
    public Object deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
            JsonObject jsonObj = jsonElement.getAsJsonObject();
            String className = jsonObj.get(ClASS_TYPE).getAsString();
            try {
                Class<?> clz = Class.forName(className);
                return jsonDeserializationContext.deserialize(jsonElement, clz);
            } catch (ClassNotFoundException e) {
                throw new JsonParseException(e);
            }
    }

    @Override
    public JsonElement serialize(Object object, Type type, JsonSerializationContext jsonSerializationContext) {
        JsonElement jsonElement = jsonSerializationContext.serialize(object, object.getClass());
        jsonElement.getAsJsonObject().addProperty(ClASS_TYPE,
                object.getClass().getCanonicalName());
        return jsonElement;
    }



}