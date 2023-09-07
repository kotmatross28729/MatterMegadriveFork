package matteroverdrive.items.weapon.module;

import matteroverdrive.MatterOverdrive;
import matteroverdrive.Reference;
import matteroverdrive.api.weapon.IWeaponScope;
import matteroverdrive.items.includes.MOBaseItem;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

public class WeaponModuleSniperScope extends MOBaseItem implements IWeaponScope {
    public WeaponModuleSniperScope(String name) {
        super(name);
        setCreativeTab(MatterOverdrive.tabMatterOverdrive_modules);
        this.setMaxDamage(0);
        this.setMaxStackSize(1);
    }

    @Override
    public float getYOffset(ItemStack scopeStack, ItemStack weapon) {
        return -0.18f;
    }

    @Override
    public float getZoomAmount(ItemStack scopeStack, ItemStack weapon) {
        return 0.85f;
    }

    @Override
    public float getAccuracyModify(ItemStack scopeStack, ItemStack weaponStack, boolean zoomed, float originalAccuracy) {
        if (zoomed) {
            return originalAccuracy * 0.4f;
        }
        return originalAccuracy + 3f;
    }

    @Override
    public int getSlot(ItemStack module) {
        return Reference.MODULE_SIGHTS;
    }

    @Override
    public String getModelPath() {
        return Reference.PATH_MODEL_ITEMS + "sniper_scope.obj";
    }

    @Override
    public ResourceLocation getModelTexture(ItemStack module) {
        return new ResourceLocation(Reference.PATH_ITEM + "sniper_scope_texture.png");
    }

    @Override
    public String getModelName(ItemStack module) {
        return "sniper_scope";
    }

    @Override
    public float modifyWeaponStat(int statID, ItemStack module, ItemStack weapon, float originalStat) {
        if (statID == Reference.WS_ACCURACY) {
            return originalStat * 0.8f;
        } else if (statID == Reference.WS_RANGE) {
            return originalStat * 1.5f;
        }
        return originalStat;
    }
}
