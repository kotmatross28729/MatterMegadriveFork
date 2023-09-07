package matteroverdrive.util;

import matteroverdrive.Reference;
import matteroverdrive.api.weapon.IWeapon;
import matteroverdrive.api.weapon.IWeaponColor;
import matteroverdrive.api.weapon.IWeaponModule;
import matteroverdrive.items.weapon.module.WeaponModuleColor;
import net.minecraft.item.ItemStack;

import java.util.List;

public class WeaponHelper {
    public static ItemStack getModuleAtSlot(int slot, ItemStack weapon) {
        if (isWeapon(weapon)) {
            return MOInventoryHelper.getStackInSlot(weapon, slot);
        }
        return null;
    }

    public static boolean hasModule(int module, ItemStack weapon) {
        return MOInventoryHelper.getStackInSlot(weapon, module) != null;
    }

    public static void setModuleAtSlot(int slot, ItemStack weapon, ItemStack module) {
        if (isWeapon(weapon) && module != null) {
            MOInventoryHelper.setInventorySlotContents(weapon, slot, module);
        }
    }

    public static int getColor(ItemStack weapon) {
        ItemStack module = getModuleAtSlot(Reference.MODULE_COLOR, weapon);
        if (module != null && isWeaponModule(module)) {
            return ((IWeaponColor) module.getItem()).getColor(module, weapon);
        }
        return WeaponModuleColor.defaultColor.getColor();
    }

    public static float modifyStat(int stat, ItemStack weapon, float original) {
        if (isWeapon(weapon)) {
            List<ItemStack> itemStacks = MOInventoryHelper.getStacks(weapon);
            if (itemStacks != null) {
                for (ItemStack module : itemStacks) {
                    if (module != null && module.getItem() instanceof IWeaponModule) {
                        original = ((IWeaponModule) module.getItem()).modifyWeaponStat(stat, module, weapon, original);
                    }
                }
            }
        }
        return original;
    }

    public static boolean hasStat(int stat, ItemStack weapon) {
        float statValue = 1f;
        if (isWeapon(weapon)) {
            for (ItemStack module : MOInventoryHelper.getStacks(weapon)) {
                if (module != null && module.getItem() instanceof IWeaponModule) {
                    statValue = ((IWeaponModule) module.getItem()).modifyWeaponStat(stat, module, weapon, statValue);
                }
            }
        }
        return statValue != 1f;
    }

    public static boolean isWeaponModule(ItemStack itemStack) {
        return itemStack != null && itemStack.getItem() != null && itemStack.getItem() instanceof IWeaponModule;
    }

    public static boolean isWeapon(ItemStack itemStack) {
        return itemStack != null && itemStack.getItem() != null && itemStack.getItem() instanceof IWeapon;
    }
}
