package matteroverdrive.client.resources.data;

import com.google.gson.*;
import net.minecraft.client.resources.data.BaseMetadataSectionSerializer;
import net.minecraft.util.JsonUtils;
import net.minecraft.util.Vec3;

import java.lang.reflect.Type;

public class WeaponMetadataSectionSerializer extends BaseMetadataSectionSerializer implements JsonSerializer<WeaponMetadataSection> {

    @Override
    public String getSectionName() {
        return "weapon";
    }

    @Override
    public WeaponMetadataSection deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        JsonObject jsonobject = JsonUtils.getJsonElementAsJsonObject(json, "metadata section");
        Vec3 scopePosition = null;
        try {
            JsonArray array = jsonobject.getAsJsonArray("scope_position");
            if (array.size() >= 3) {
                scopePosition = Vec3.createVectorHelper(array.get(0).getAsDouble(), array.get(1).getAsDouble(), array.get(2).getAsDouble());
            }
        } catch (ClassCastException classcastexception) {
            throw new JsonParseException("Invalid weapon->scope_position: expected array, was " + jsonobject.get("scope_position"), classcastexception);
        }
        return new WeaponMetadataSection(scopePosition);
    }

    @Override
    public JsonElement serialize(WeaponMetadataSection section, Type type, JsonSerializationContext context) {
        JsonObject jsonobject = new JsonObject();
        JsonArray scopePosition = new JsonArray();
        scopePosition.add(new JsonPrimitive(section.getScopePosition().xCoord));
        scopePosition.add(new JsonPrimitive(section.getScopePosition().yCoord));
        scopePosition.add(new JsonPrimitive(section.getScopePosition().zCoord));
        jsonobject.add("scope_position", scopePosition);
        return jsonobject;
    }
}
