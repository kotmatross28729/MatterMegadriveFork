package matteroverdrive.entity.ai;

import net.minecraft.command.IEntitySelector;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.ai.EntityAITarget;

import java.util.Collections;
import java.util.List;

public class EntityAINearestTarget extends EntityAITarget {
    private final Class targetClass;
    private final int targetChance;
    private final EntityAINearestAttackableTarget.Sorter theNearestAttackableTargetSorter;
    private final IEntitySelector targetEntitySelector;
    private EntityLivingBase targetEntity;

    public EntityAINearestTarget(EntityCreature theEntity, Class targetClass, int targetChance, boolean shouldCheckSight, boolean nearbyOnly, final IEntitySelector p_i1665_6_) {
        super(theEntity, shouldCheckSight, nearbyOnly);
        this.targetClass = targetClass;
        this.targetChance = targetChance;
        this.theNearestAttackableTargetSorter = new EntityAINearestAttackableTarget.Sorter(theEntity);
        this.setMutexBits(1);
        this.targetEntitySelector = new IEntitySelector() {

            /**
             * Return whether the specified entity is applicable to this filter.
             */
            public boolean isEntityApplicable(Entity p_82704_1_) {
                return p_82704_1_ instanceof EntityLivingBase && (!(p_i1665_6_ != null && !p_i1665_6_.isEntityApplicable(p_82704_1_)) && EntityAINearestTarget.this.isSuitableTarget((EntityLivingBase) p_82704_1_, false));
            }
        };
    }

    public boolean shouldExecute() {
        if (this.targetChance > 0 && this.taskOwner.getRNG().nextInt(this.targetChance) != 0) {
            return false;
        } else {
            double d0 = this.getTargetDistance();
            List list = this.taskOwner.worldObj.selectEntitiesWithinAABB(this.targetClass, this.taskOwner.boundingBox.expand(d0, 4.0D, d0), this.targetEntitySelector);
            Collections.sort(list, this.theNearestAttackableTargetSorter);

            if (list.isEmpty()) {
                return false;
            } else {
                this.targetEntity = (EntityLivingBase) list.get(0);
                return true;
            }
        }
    }

    public void startExecuting() {
        this.taskOwner.setAttackTarget(this.targetEntity);
        super.startExecuting();
    }
}
