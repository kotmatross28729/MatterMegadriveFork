package matteroverdrive.client.render.parts;

import matteroverdrive.api.renderer.IBionicPartRenderer;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.Vec3;
import org.lwjgl.opengl.GL11;

public abstract class BionicPartRenderer implements IBionicPartRenderer {
    protected void translateFromPlayer(EntityPlayer entityPlayer, float partialRenderTick) {
        if (entityPlayer != Minecraft.getMinecraft().thePlayer) {
            Vec3 clientPos = Minecraft.getMinecraft().thePlayer.getPosition(partialRenderTick);
            Vec3 pos = entityPlayer.getPosition(partialRenderTick);
            GL11.glTranslated(pos.xCoord - clientPos.xCoord, pos.yCoord - clientPos.yCoord + entityPlayer.getEyeHeight() - 0.2, pos.zCoord - clientPos.zCoord);
        }
    }
}
