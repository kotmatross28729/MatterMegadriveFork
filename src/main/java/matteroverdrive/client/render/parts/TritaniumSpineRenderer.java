package matteroverdrive.client.render.parts;

import matteroverdrive.Reference;
import matteroverdrive.entity.player.AndroidPlayer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.RenderPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

import static org.lwjgl.opengl.GL11.*;

public class TritaniumSpineRenderer extends BionicPartRenderer {
    public static ResourceLocation texture = new ResourceLocation(Reference.PATH_ARMOR + "tritanium_spine.png");

    @Override
    public void renderPart(ItemStack partStack, AndroidPlayer androidPlayer, RenderPlayer renderPlayer, float ticks) {
        translateFromPlayer(androidPlayer.getPlayer(), ticks);
        Minecraft.getMinecraft().getTextureManager().bindTexture(texture);

        for (int i = 0; i < 4; i++) {
            glPushMatrix();
            glRotated(androidPlayer.getPlayer().renderYawOffset, 0, -1, 0);
            glTranslated(-0.05, -0.75 + i * 0.15, -0.2 - Math.sin(((i + 1) / 5d) * Math.PI) * 0.05);
            glRotated(25, 1, 0, 0);
            renderSpline(0, 0, 0);
            glPopMatrix();
        }

    }

    @Override
    public void affectPlayerRenderer(ItemStack partStack, AndroidPlayer androidPlayer, RenderPlayer renderPlayer, float ticks) {

    }

    private void renderSpline(double x, double y, double z) {
        double minU = 0;
        double maxU = 1;
        double minV = 0;
        double maxV = 0.5;
        double sizeX = 0.1;
        double sizeY = 0.1;
        double sizeZ = 0.2;
        Tessellator tessellator = Tessellator.instance;

        tessellator.startDrawingQuads();

        //base
        tessellator.addVertexWithUV(x, y, z, minU, maxV);
        tessellator.addVertexWithUV(x + sizeX, y, z, minU, minV);
        tessellator.addVertexWithUV(x + sizeX, y, z + sizeZ, maxU, minV);
        tessellator.addVertexWithUV(x, y, z + sizeZ, maxU, maxV);

        //top
        tessellator.addVertexWithUV(x + sizeX, y + sizeY, z, maxU, maxV);
        tessellator.addVertexWithUV(x, y + sizeY, z, maxU, minV);
        tessellator.addVertexWithUV(x, y + sizeY, z + sizeZ, minU, minV);
        tessellator.addVertexWithUV(x + sizeX, y + sizeY, z + sizeZ, minU, maxV);

        //west
        tessellator.addVertexWithUV(x, y, z, minU, minV);
        tessellator.addVertexWithUV(x, y, z + sizeZ, maxU, minV);
        tessellator.addVertexWithUV(x, y + sizeY, z + sizeZ, maxU, maxV);
        tessellator.addVertexWithUV(x, y + sizeY, z, minU, maxV);

        //east
        tessellator.addVertexWithUV(x + sizeX, y, z + sizeZ, maxU, minV);
        tessellator.addVertexWithUV(x + sizeX, y, z, minU, minV);
        tessellator.addVertexWithUV(x + sizeX, y + sizeY, z, minU, maxV);
        tessellator.addVertexWithUV(x + sizeX, y + sizeY, z + sizeZ, maxU, maxV);

        //north
        tessellator.addVertexWithUV(x, y, z, 0, 0.5);
        tessellator.addVertexWithUV(x, y + sizeY, z, 0, 1);
        tessellator.addVertexWithUV(x + sizeX, y + sizeY, z, 0.5, 1);
        tessellator.addVertexWithUV(x + sizeX, y, z, 0.5, 0.5);

        //south
        //tessellator.addVertexWithUV(x, y + sizeY, z + sizeZ, minU, maxV);
        //tessellator.addVertexWithUV(x, y,z + sizeZ, minU, minV);
        //tessellator.addVertexWithUV(x + sizeX, y, z + sizeZ, maxU, minV);
        //tessellator.addVertexWithUV(x + sizeX, y + sizeY, z + sizeZ, maxU, maxV);

        tessellator.draw();
    }
}
