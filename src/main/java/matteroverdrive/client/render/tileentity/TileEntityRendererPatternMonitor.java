package matteroverdrive.client.render.tileentity;

import matteroverdrive.Reference;
import matteroverdrive.tile.TileEntityMachinePatternMonitor;
import matteroverdrive.util.MOStringHelper;
import matteroverdrive.util.RenderUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;

import static org.lwjgl.opengl.GL11.*;

public class TileEntityRendererPatternMonitor extends TileEntityRendererMonitor {
    public static ResourceLocation screenTexture = new ResourceLocation(Reference.PATH_BLOCKS + "pattern_monitor_holo.png");

    @Override
    public void drawScreen(TileEntity tileEntity, float ticks) {
        Minecraft.getMinecraft().renderEngine.bindTexture(screenTexture);
        glColor3f(Reference.COLOR_HOLO.getFloatR() * 0.7f, Reference.COLOR_HOLO.getFloatG() * 0.7f, Reference.COLOR_HOLO.getFloatB() * 0.7f);

        RenderUtils.drawPlane(1);

        if (tileEntity instanceof TileEntityMachinePatternMonitor) {
            TileEntityMachinePatternMonitor monitor = (TileEntityMachinePatternMonitor) tileEntity;
            glPushMatrix();
            int countWitdth = Minecraft.getMinecraft().fontRenderer.getStringWidth(MOStringHelper.formatNumber(monitor.getGuiPatterns().size(), "0"));
            double scale = ((double) Minecraft.getMinecraft().fontRenderer.getStringWidth(MOStringHelper.formatNumber(10, "0")) / (double) countWitdth);
            scale = Math.min(scale, 1);
            glTranslated(0.47, 0.33 + (Minecraft.getMinecraft().fontRenderer.FONT_HEIGHT * 0.03) * (1 - scale) * 0.5, 0);
            glScaled(scale * 0.03, scale * 0.03, scale * 0.03);
            Minecraft.getMinecraft().fontRenderer.drawString(MOStringHelper.formatNumber(monitor.getGuiPatterns().size(), "0"), 0, 0, 0x78a1b3);
            glPopMatrix();
        }
    }
}
