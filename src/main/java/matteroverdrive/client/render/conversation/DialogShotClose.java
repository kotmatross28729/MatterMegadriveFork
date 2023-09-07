package matteroverdrive.client.render.conversation;

import matteroverdrive.util.MOPhysicsHelper;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;

public class DialogShotClose extends DialogShot {
    private float maxZoom;
    private float minZoom;

    public DialogShotClose(float maxZoom, float minZoom) {
        this.maxZoom = maxZoom;
        this.minZoom = minZoom;
    }

    @Override
    public boolean positionCamera(EntityLivingBase active, EntityLivingBase other, float ticks, EntityRendererConversation rendererConversation) {
        Vec3 look = rendererConversation.getLook(other, active, ticks);
        double distance = look.lengthVector();
        double clammpedDistance = MathHelper.clamp_double(distance, minZoom, maxZoom);
        look.yCoord *= 0;
        look = look.normalize();

        Vec3 pos = rendererConversation.getPosition(active, ticks, false).addVector(0, active.getEyeHeight() - 0.1, 0).addVector(look.xCoord * clammpedDistance, look.yCoord * clammpedDistance, look.zCoord * clammpedDistance);
        MovingObjectPosition movingObjectPosition = MOPhysicsHelper.rayTrace(rendererConversation.getPosition(active, ticks, false), active.worldObj, maxZoom, ticks, Vec3.createVectorHelper(0, active.getEyeHeight(), 0), true, true, look.normalize(), active);
        if (movingObjectPosition != null) {
            pos = movingObjectPosition.hitVec;
        }
        Vec3 left = look.crossProduct(Vec3.createVectorHelper(0, 1, 0));
        float leftAmount = 0.1f;
        rendererConversation.setCameraPosition(pos.xCoord + left.xCoord * leftAmount, pos.yCoord + left.yCoord * leftAmount, pos.zCoord + left.zCoord * leftAmount);
        rendererConversation.rotateCameraYawTo(look.normalize(), 90);
        rendererConversation.setCameraPitch(10);
        return true;
    }
}
