package matteroverdrive.entity.ai;

import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.ai.RandomPositionGenerator;
import net.minecraft.util.Vec3;

public class EntityAIRangedRunFromMelee extends EntityAIBase {
    private double minDistanceSq;
    private EntityCreature theEntity;
    private double moveSpeed;
    Vec3 destinaton;

    public EntityAIRangedRunFromMelee(EntityCreature theEntity, double moveSpeed) {
        this.theEntity = theEntity;
        this.moveSpeed = moveSpeed;
        //setMutexBits(1);
    }

    @Override
    public boolean shouldExecute() {
        if (this.theEntity.getAttackTarget() != null && this.theEntity.getNavigator().noPath()) {
            double sqDistanceToTargetSq = this.theEntity.getDistanceSqToEntity(this.theEntity.getAttackTarget());
            if (sqDistanceToTargetSq + 4 < minDistanceSq) {
                int distanceToRun = (int) Math.sqrt(minDistanceSq - sqDistanceToTargetSq);
                destinaton = RandomPositionGenerator.findRandomTargetBlockAwayFrom(this.theEntity, distanceToRun, 4, Vec3.createVectorHelper(this.theEntity.getAttackTarget().posX, this.theEntity.getAttackTarget().posY, this.theEntity.getAttackTarget().posZ));
                return destinaton != null;
            }
        }
        return false;
    }

    @Override
    public void startExecuting() {
        if (destinaton != null) {
            this.theEntity.getNavigator().tryMoveToXYZ(destinaton.xCoord, destinaton.yCoord, destinaton.zCoord, moveSpeed);
        }
    }

    @Override
    public boolean continueExecuting() {
        return !this.theEntity.getNavigator().noPath();
    }

    public void setMinDistance(double minDistance) {
        this.minDistanceSq = minDistance * minDistance;
    }

    public void setMoveSpeed(double moveSpeed) {
        this.moveSpeed = moveSpeed;
    }
}
