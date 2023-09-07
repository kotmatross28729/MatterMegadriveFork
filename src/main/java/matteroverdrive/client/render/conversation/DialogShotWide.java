package matteroverdrive.client.render.conversation;

import matteroverdrive.util.MOPhysicsHelper;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;

public class DialogShotWide extends DialogShot {
    float distance;
    float heightOffset;
    boolean oppositeSide;

    public DialogShotWide(float heightOffset, boolean oppositeSide, float distance) {
        this.heightOffset = heightOffset;
        this.oppositeSide = oppositeSide;
        this.distance = distance;
    }

    @Override
    public boolean positionCamera(EntityLivingBase active, EntityLivingBase other, float ticks, EntityRendererConversation rendererConversation) {
        Vec3 centerDir = rendererConversation.getPosition(active, ticks, false).addVector(0, -active.getEyeHeight() + heightOffset, 0).subtract(rendererConversation.getPosition(other, ticks, false).addVector(0, -other.getEyeHeight() + heightOffset, 0));
        double distance = centerDir.lengthVector() / 2 * this.distance;
        Vec3 center = rendererConversation.getPosition(active, ticks, false).addVector(centerDir.xCoord / 2, centerDir.yCoord / 2, centerDir.zCoord / 2);
        Vec3 centerCross = centerDir.normalize().crossProduct(Vec3.createVectorHelper(0, oppositeSide ? -1 : 1, 0)).normalize();
        MovingObjectPosition hit = MOPhysicsHelper.rayTraceForBlocks(center, active.worldObj, distance, ticks, null, true, true, centerCross);
        Vec3 pos = center.addVector(centerCross.xCoord * distance, centerCross.yCoord * distance, centerCross.zCoord * distance);
        if (hit != null) {
            pos = hit.hitVec;
        }

        rendererConversation.setCameraPosition(pos.xCoord, pos.yCoord, pos.zCoord);
        rendererConversation.rotateCameraYawTo(centerCross, 90);
        return true;
    }
}
