package matteroverdrive.entity.ai;

import matteroverdrive.api.entity.IPathableMob;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.ai.RandomPositionGenerator;
import net.minecraft.util.Vec3;

public class EntityAIMoveAlongPath extends EntityAIBase {
    private IPathableMob pathableMob;
    private double movePosX;
    private double movePosY;
    private double movePosZ;
    private double movementSpeed;
    private static final String __OBFID = "CL_00001598";

    public EntityAIMoveAlongPath(IPathableMob pathableMob, double moveSpeedMultiply) {
        this.pathableMob = pathableMob;
        this.movementSpeed = moveSpeedMultiply;
        this.setMutexBits(1);
    }

    /**
     * Returns whether the EntityAIBase should begin execution.
     */
    public boolean shouldExecute() {
        if (pathableMob.getEntity().getAttackTarget() != null) {
            return false;
        } else if (pathableMob.getCurrentTarget() != null) {
            if (!pathableMob.getEntity().getNavigator().noPath())
                return true;

            if (pathableMob.isNearTarget(pathableMob.getCurrentTarget())) {
                pathableMob.onTargetReached(pathableMob.getCurrentTarget());
            } else {
                if (!pathableMob.getEntity().getNavigator().tryMoveToXYZ(pathableMob.getCurrentTarget().xCoord, pathableMob.getCurrentTarget().yCoord, pathableMob.getCurrentTarget().zCoord, this.movementSpeed)) {
                    Vec3 vec3 = RandomPositionGenerator.findRandomTargetBlockTowards(pathableMob.getEntity(), 8, 2, pathableMob.getCurrentTarget());

                    if (vec3 == null) {
                        return false;
                    } else {
                        this.movePosX = vec3.xCoord;
                        this.movePosY = vec3.yCoord;
                        this.movePosZ = vec3.zCoord;
                        pathableMob.getEntity().getNavigator().tryMoveToXYZ(this.movePosX, this.movePosY, this.movePosZ, this.movementSpeed);
                        return true;
                    }
                } else {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Returns whether an in-progress EntityAIBase should continue executing
     */
    public boolean continueExecuting() {
        return !pathableMob.getEntity().getNavigator().noPath();
    }

    /**
     * Execute a one shot task or start executing a continuous task
     */
    public void startExecuting() {

    }
}
