package matteroverdrive.api.inventory;

import net.minecraft.item.ItemStack;

/**
 * Used by energy weapons as one use charging items (Ammo).
 */
public interface IEnergyPack {
    /**
     * The amount of energy the item restores.
     *
     * @param pack The Item Stack
     * @return The amount of energy restored.
     */
    int getEnergyAmount(ItemStack pack);
}
