package matteroverdrive.client.render;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import matteroverdrive.client.data.Color;
import matteroverdrive.util.MOPhysicsHelper;
import matteroverdrive.util.RenderUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Vec3;

import java.util.Random;

import static org.lwjgl.opengl.GL11.*;

@SideOnly(Side.CLIENT)
public abstract class RenderBeam<T extends EntityLivingBase> implements IWorldLastRenderer {
    protected final Random random = new Random();

    protected abstract boolean shouldRenderBeam(T entity);

    protected abstract void onBeamRaycastHit(MovingObjectPosition hit, T caster);

    protected abstract void onBeamRender(T caster);

    protected abstract Color getBeamColor(T caster);

    protected abstract ResourceLocation getBeamTexture(T caster);

    protected abstract float getBeamMaxDistance(T caster);

    protected abstract float getBeamThickness(T caster);

    protected boolean renderRaycastedBeam(Vec3 direction, Vec3 offset, T caster) {
        return renderRaycastedBeam(caster.getPosition(1), direction, offset, caster);
    }

    protected boolean renderRaycastedBeam(Vec3 position, Vec3 direction, Vec3 offset, T caster) {
        double maxDistance = getBeamMaxDistance(caster);

        MovingObjectPosition hit = MOPhysicsHelper.rayTrace(position, caster.worldObj, maxDistance, 0, Vec3.createVectorHelper(0, 0, 0), false, true, direction, caster);
        if (hit != null && hit.typeOfHit != MovingObjectPosition.MovingObjectType.MISS) {
            renderBeam(position, hit.hitVec, offset, getBeamColor(caster), getBeamTexture(caster), getBeamThickness(caster), caster);
            onBeamRender(caster);
            onBeamRaycastHit(hit, caster);
            return true;
        } else {

            renderBeam(position, position.addVector(direction.xCoord * maxDistance, direction.yCoord * maxDistance, direction.zCoord * maxDistance), offset, getBeamColor(caster), getBeamTexture(caster), getBeamThickness(caster), caster);
            onBeamRender(caster);
        }
        return false;
    }

    protected void renderBeam(Vec3 from, Vec3 to, Vec3 offest, Color color, ResourceLocation texture, float tickness, T viewer) {
        if (texture != null)
            Minecraft.getMinecraft().renderEngine.bindTexture(texture);

        RenderUtils.applyColor(color);
        glDisable(GL_CULL_FACE);
        glEnable(GL_BLEND);
        glBlendFunc(GL_ONE, GL_ONE);
        glDisable(GL_LIGHTING);
        double distance = from.subtract(to).lengthVector();
        double v = -viewer.worldObj.getWorldTime() * 0.2;

        glPushMatrix();
        glTranslated(from.xCoord, from.yCoord, from.zCoord);
        glRotated(-viewer.getRotationYawHead(), 0, 1, 0);
        glRotated(viewer.rotationPitch, 1, 0, 0);
        glTranslated(offest.xCoord, offest.yCoord, offest.zCoord);
        Tessellator t = Tessellator.instance;
        t.startDrawingQuads();
        t.addVertexWithUV(tickness, 0, 0, 0, v);
        t.addVertexWithUV(tickness, 0, distance, 0, v + distance * 1.5);
        t.addVertexWithUV(-tickness, 0, distance, 1, v + distance * 1.5);
        t.addVertexWithUV(-tickness, 0, 0, 1, v);

        t.addVertexWithUV(0, tickness, 0, 0, v);
        t.addVertexWithUV(0, tickness, distance, 0, v + distance * 1.5);
        t.addVertexWithUV(0, -tickness, distance, 1, v + distance * 1.5);
        t.addVertexWithUV(0, -tickness, 0, 1, v);
        t.draw();
        glPopMatrix();

        glEnable(GL_CULL_FACE);
        glDisable(GL_BLEND);
        glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
    }
}
