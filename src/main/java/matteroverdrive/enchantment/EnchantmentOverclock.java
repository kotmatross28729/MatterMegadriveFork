package matteroverdrive.enchantment;

import matteroverdrive.api.weapon.IWeapon;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnumEnchantmentType;
import net.minecraft.item.ItemStack;

public class EnchantmentOverclock extends Enchantment {
    public EnchantmentOverclock(int id) {
        super(id, 10, EnumEnchantmentType.all);
        setName("matteroverdrive.weapon.damage");
    }

    public boolean canApply(ItemStack itemStack) {
        return itemStack.getItem() instanceof IWeapon;
    }

    @Override
    public int getMinLevel() {
        return 1;
    }

    @Override
    public int getMaxLevel() {
        return 4;
    }
}
