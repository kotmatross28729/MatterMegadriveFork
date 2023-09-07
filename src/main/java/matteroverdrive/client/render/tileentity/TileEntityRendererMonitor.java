package matteroverdrive.client.render.tileentity;

import matteroverdrive.Reference;
import matteroverdrive.util.RenderUtils;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.util.ForgeDirection;

import static org.lwjgl.opengl.GL11.*;

public abstract class TileEntityRendererMonitor extends TileEntitySpecialRenderer {
    public static ResourceLocation screenTextureBack = new ResourceLocation(Reference.PATH_BLOCKS + "pattern_monitor_holo_back.png");
    public static ResourceLocation screenTextureGlow = new ResourceLocation(Reference.PATH_FX + "holo_monitor_glow.png");

    @Override
    public void renderTileEntityAt(TileEntity tileEntity, double x, double y, double z, float ticks) {
        glPushMatrix();

        int meta = tileEntity.getWorldObj().getBlockMetadata(tileEntity.xCoord, tileEntity.yCoord, tileEntity.zCoord);
        ForgeDirection direction = ForgeDirection.getOrientation(meta);

        glDisable(GL_LIGHTING);
        glDisable(GL_CULL_FACE);
        glEnable(GL_BLEND);
        glBlendFunc(GL_ONE, GL_ONE_MINUS_SRC_COLOR);
        RenderUtils.disableLightmap();

        RenderUtils.beginDrawinngBlockScreen(x, y, z, direction, Reference.COLOR_HOLO, tileEntity, -0.65, 1f);
        glTranslated(0, 0, -0.05);
        drawScreen(tileEntity, ticks);
        RenderUtils.endDrawinngBlockScreen();

        glDisable(GL_BLEND);
        glEnable(GL_CULL_FACE);
        glEnable(GL_LIGHTING);
        RenderUtils.enableLightmap();
        glPopMatrix();
    }

    public abstract void drawScreen(TileEntity tileEntity, float ticks);
}
