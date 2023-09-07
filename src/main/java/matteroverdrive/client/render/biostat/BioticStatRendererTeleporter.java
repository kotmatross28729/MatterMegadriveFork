package matteroverdrive.client.render.biostat;

import matteroverdrive.Reference;
import matteroverdrive.api.renderer.IBioticStatRenderer;
import matteroverdrive.client.render.tileentity.TileEntityRendererGravitationalAnomaly;
import matteroverdrive.data.biostats.BioticStatTeleport;
import matteroverdrive.entity.player.AndroidPlayer;
import matteroverdrive.handler.KeyHandler;
import matteroverdrive.init.MatterOverdriveBioticStats;
import matteroverdrive.proxy.ClientProxy;
import matteroverdrive.util.RenderUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.util.Vec3;
import net.minecraftforge.client.event.RenderWorldLastEvent;

import static org.lwjgl.opengl.GL11.*;

public class BioticStatRendererTeleporter implements IBioticStatRenderer<BioticStatTeleport> {
    @Override
    public void onWorldRender(BioticStatTeleport stat, int level, RenderWorldLastEvent event) {
        AndroidPlayer androidPlayer = AndroidPlayer.get(Minecraft.getMinecraft().thePlayer);

        if (androidPlayer != null && androidPlayer.isAndroid() && androidPlayer.isUnlocked(MatterOverdriveBioticStats.teleport, MatterOverdriveBioticStats.teleport.maxLevel()) && MatterOverdriveBioticStats.teleport.isEnabled(androidPlayer, 0) && MatterOverdriveBioticStats.teleport.getHasPressedKey()) {
            Vec3 playerPos = androidPlayer.getPlayer().getPosition(event.partialTicks);
            if (ClientProxy.keyHandler.getBinding(KeyHandler.ABILITY_USE_KEY).getIsKeyPressed()) {
                glPushMatrix();
                glEnable(GL_BLEND);
                glBlendFunc(GL_ONE, GL_ONE);
                RenderUtils.applyColorWithMultipy(Reference.COLOR_HOLO, 0.5f);
                glTranslated(-playerPos.xCoord, -playerPos.yCoord, -playerPos.zCoord);

                //mob.rotationYawHead = androidPlayer.getPlayer().rotationYawHead;

                Vec3 pos = MatterOverdriveBioticStats.teleport.getPos(androidPlayer);
                if (pos != null) {
                    Minecraft.getMinecraft().renderEngine.bindTexture(TileEntityRendererGravitationalAnomaly.glow);
                    glTranslated(pos.xCoord, pos.yCoord, pos.zCoord);
                    glRotated(androidPlayer.getPlayer().rotationYaw, 0, -1, 0);
                    glRotated(androidPlayer.getPlayer().rotationPitch, 1, 0, 0);
                    glRotated(Minecraft.getMinecraft().theWorld.getWorldTime() * 10, 0, 0, 1);
                    glTranslated(-0.5, -0.5, 0);
                    RenderUtils.drawPlane(1);
                }

                glDisable(GL_BLEND);
                glPopMatrix();
            }
        }
    }
}
