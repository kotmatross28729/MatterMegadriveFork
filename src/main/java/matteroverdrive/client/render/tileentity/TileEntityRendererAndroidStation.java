package matteroverdrive.client.render.tileentity;

import matteroverdrive.Reference;
import matteroverdrive.entity.monster.EntityMeleeRogueAndroidMob;
import matteroverdrive.proxy.ClientProxy;
import matteroverdrive.tile.TileEntityAndroidStation;
import matteroverdrive.util.RenderUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.EntityLivingBase;

import static org.lwjgl.opengl.GL11.*;

public class TileEntityRendererAndroidStation extends TileEntityRendererStation<TileEntityAndroidStation> {
    EntityMeleeRogueAndroidMob mob;

    public TileEntityRendererAndroidStation() {
        super();
    }

    @Override
    protected void renderHologram(TileEntityAndroidStation station, double x, double y, double z, float partialTicks, double noise) {
        if ((station).isUseableByPlayer(Minecraft.getMinecraft().thePlayer)) {
            if (mob == null) {
                mob = new EntityMeleeRogueAndroidMob(Minecraft.getMinecraft().theWorld);
                mob.getEntityData().setBoolean("Hologram", true);
            }

            //glDisable(GL_BLEND);
            //glDepthMask(true);
            glEnable(GL_BLEND);
            glBlendFunc(GL_ONE, GL_ONE);
            glPushMatrix();
            glTranslated(x + 0.5, y + 0.8, z + 0.5);
            rotate(station, noise);

            RenderUtils.applyColorWithMultipy(Reference.COLOR_HOLO, 0.3f);

            if (station.isUseableByPlayer(Minecraft.getMinecraft().thePlayer)) {
                ClientProxy.renderHandler.rendererRogueAndroidHologram.setRenderManager(RenderManager.instance);
                ClientProxy.renderHandler.rendererRogueAndroidHologram.doRender((EntityLivingBase) mob, 0, 0, 0, 0, 0);
                //Render render = RenderManager.instance.getEntityRenderObject(mob);
                //render.doRender(mob,0,0,0,0,0);
            }
            glPopMatrix();
        } else {
            super.renderHologram(station, x, y, z, partialTicks, noise);
        }
    }
}
