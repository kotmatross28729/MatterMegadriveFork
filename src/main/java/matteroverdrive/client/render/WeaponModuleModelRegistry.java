package matteroverdrive.client.render;

import matteroverdrive.api.weapon.IWeaponModule;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.AdvancedModelLoader;
import net.minecraftforge.client.model.IModelCustom;
import net.minecraftforge.client.model.obj.WavefrontObject;

import java.util.HashMap;
import java.util.Map;

public class WeaponModuleModelRegistry {
    Map<String, WavefrontObject> models;

    public WeaponModuleModelRegistry() {
        models = new HashMap<>();
    }

    public void registerModule(IWeaponModule module) {
        IModelCustom m = AdvancedModelLoader.loadModel(new ResourceLocation(module.getModelPath()));
        if (m instanceof WavefrontObject) {
            models.put(module.getModelPath(), (WavefrontObject) m);
        }
    }

    public WavefrontObject getModel(String model) {
        return models.get(model);
    }
}
