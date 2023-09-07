package matteroverdrive.api.entity;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Vec3;

public interface IRangedEnergyWeaponAttackMob {
    ItemStack getWeapon();

    void attackEntityWithRangedAttack(EntityLivingBase target, Vec3 lastSeenPosition, boolean canSee);
}
