package matteroverdrive.entity.ai;

import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.ai.EntityAIAttackOnCollide;

public class EntityAIAndroidAttackOnCollide extends EntityAIAttackOnCollide {
    public EntityAIAndroidAttackOnCollide(EntityCreature p_i1635_1_, Class p_i1635_2_, double p_i1635_3_, boolean p_i1635_5_) {
        super(p_i1635_1_, p_i1635_2_, p_i1635_3_, p_i1635_5_);
    }

    public EntityAIAndroidAttackOnCollide(EntityCreature p_i1636_1_, double p_i1636_2_, boolean p_i1636_4_) {
        super(p_i1636_1_, p_i1636_2_, p_i1636_4_);
    }

    @Override
    public void resetTask() {

    }
}
