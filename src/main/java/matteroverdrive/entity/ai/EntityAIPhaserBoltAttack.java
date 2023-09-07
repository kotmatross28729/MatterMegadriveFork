package matteroverdrive.entity.ai;

import matteroverdrive.api.entity.IRangedEnergyWeaponAttackMob;
import matteroverdrive.items.weapon.EnergyWeapon;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.item.ItemStack;
import net.minecraft.pathfinding.PathEntity;
import net.minecraft.util.Vec3;

public class EntityAIPhaserBoltAttack extends EntityAIBase {
    /**
     * The entity the AI instance has been applied to
     */
    private final EntityLiving entityHost;
    /**
     * The entity (as a RangedAttackMob) the AI instance has been applied to.
     */
    private final IRangedEnergyWeaponAttackMob rangedAttackEntityHost;
    private EntityLivingBase attackTarget;
    private Vec3 lastKnownShootLocation;
    /**
     * A decrementing tick that spawns a ranged attack once this value reaches 0. It is then set back to the
     * maxRangedAttackDelay.
     */
    private int rangedAttackDelayTime;
    private double entityMoveSpeed;
    private int pathRetryTimer;
    private int shootPatienceTime;
    /**
     * The maximum time the AI has to wait before peforming another ranged attack.
     */
    private int maxRangedAttackDelay;
    private float maxChaseDistance;
    private float maxChaseDistanceSq;
    private PathEntity lastChasePath;

    public EntityAIPhaserBoltAttack(IRangedEnergyWeaponAttackMob rangedAttackEntityHost, double entityMoveSpeed, int maxRangedAttackDelay, float maxChaseDistance) {
        this(rangedAttackEntityHost, entityMoveSpeed, maxRangedAttackDelay, maxRangedAttackDelay, maxChaseDistance);
    }

    public EntityAIPhaserBoltAttack(IRangedEnergyWeaponAttackMob rangedAttackEntityHost, double entityMoveSpeed, int p_i1650_4_, int maxRangedAttackDelay, float maxChaseDistance) {
        this.rangedAttackDelayTime = -1;

        if (!(rangedAttackEntityHost instanceof EntityLivingBase)) {
            throw new IllegalArgumentException("EntityAIPhaserBoltAttack requires Mob implements IRangedEnergyWeaponAttackMob");
        } else {
            this.rangedAttackEntityHost = rangedAttackEntityHost;
            this.entityHost = (EntityLiving) rangedAttackEntityHost;
            this.entityMoveSpeed = entityMoveSpeed;
            this.maxRangedAttackDelay = maxRangedAttackDelay;
            this.maxChaseDistance = maxChaseDistance;
            this.maxChaseDistanceSq = maxChaseDistance * maxChaseDistance;
            this.setMutexBits(3);
        }
    }

    /**
     * Returns whether the EntityAIBase should begin execution.
     */
    public boolean shouldExecute() {
        EntityLivingBase entitylivingbase = this.entityHost.getAttackTarget();

        if (entitylivingbase == null) {
            return false;
        } else if (!entitylivingbase.isDead) {
            this.attackTarget = entitylivingbase;
            return true;
        }
        return false;
    }

    /**
     * Returns whether an in-progress EntityAIBase should continue executing
     */
    public boolean continueExecuting() {
        return this.shouldExecute() || !this.entityHost.getNavigator().noPath();
    }

    /**
     * Resets the task
     */
    public void resetTask() {
        this.attackTarget = null;
        this.pathRetryTimer = 0;
        this.rangedAttackDelayTime = -1;
        this.shootPatienceTime = 0;
    }

    /**
     * Updates the task
     */
    public void updateTask() {
        double distanceToTargetSq = this.entityHost.getDistanceSq(this.attackTarget.posX, this.attackTarget.boundingBox.minY, this.attackTarget.posZ);
        boolean canSee = this.entityHost.getEntitySenses().canSee(this.attackTarget);

        if (canSee) {
            lastKnownShootLocation = Vec3.createVectorHelper(attackTarget.prevPosX, attackTarget.prevPosY, attackTarget.prevPosZ);
            shootPatienceTime = 60;
            ++this.pathRetryTimer;
        } else {
            this.pathRetryTimer = 0;
        }

        manageMovingToLastKnowMoveLocation(distanceToTargetSq);

        if (shootPatienceTime == 0) {
            lastKnownShootLocation = null;
        }
        if (lastKnownShootLocation != null) {
            manageShooting(canSee, distanceToTargetSq);
            shootPatienceTime--;
        } else {
            this.entityHost.getLookHelper().setLookPosition(attackTarget.posX, attackTarget.posY + attackTarget.getEyeHeight(), attackTarget.posZ, 30.0F, 30.0F);
        }
    }

    private void manageMovingToLastKnowMoveLocation(double distanceToTargetSq) {
        if (distanceToTargetSq <= (double) this.maxChaseDistanceSq && this.pathRetryTimer >= 20) {
            if (this.entityHost.getNavigator().getPath() != null && this.entityHost.getNavigator().getPath().equals(lastChasePath))
                this.entityHost.getNavigator().clearPathEntity();
        } else if (this.entityHost.getNavigator().noPath()) {
            lastChasePath = this.entityHost.getNavigator().getPathToEntityLiving(attackTarget);
            this.entityHost.getNavigator().setPath(lastChasePath, this.entityMoveSpeed);
        }
    }

    private void manageShooting(boolean canSeeTarget, double distanceToTargetSq) {
        this.entityHost.getLookHelper().setLookPosition(this.lastKnownShootLocation.xCoord, this.lastKnownShootLocation.yCoord + attackTarget.getEyeHeight(), this.lastKnownShootLocation.zCoord, 30.0F, 30.0F);
        float distancePercentage;
        ItemStack weapon = rangedAttackEntityHost.getWeapon();

        if (this.rangedAttackDelayTime == 0 && weapon != null && weapon.getItem() instanceof EnergyWeapon && ((EnergyWeapon) weapon.getItem()).canFire(weapon, entityHost.worldObj, entityHost)) {
            if (distanceToTargetSq > (double) this.maxChaseDistanceSq) {
                return;
            }

            this.rangedAttackEntityHost.attackEntityWithRangedAttack(this.attackTarget, lastKnownShootLocation, canSeeTarget);
            this.rangedAttackDelayTime = this.maxRangedAttackDelay;
        } else if (this.rangedAttackDelayTime < 0) {
            this.rangedAttackDelayTime = this.maxRangedAttackDelay;
        } else if (this.rangedAttackDelayTime > 0) {
            this.rangedAttackDelayTime--;
        }
    }

    public void setMaxRangedAttackDelay(int time) {
        this.maxRangedAttackDelay = time;
    }

    public void setMaxChaseDistance(int maxChaseDistance) {
        this.maxChaseDistance = maxChaseDistance;
        this.maxChaseDistanceSq = maxChaseDistance * maxChaseDistance;
    }
}
