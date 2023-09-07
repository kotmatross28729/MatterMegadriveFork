package matteroverdrive.client.render.tileentity;

import matteroverdrive.Reference;
import matteroverdrive.init.MatterOverdriveIcons;
import matteroverdrive.util.RenderUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.AdvancedModelLoader;
import net.minecraftforge.client.model.IModelCustom;
import net.minecraftforge.client.model.obj.WavefrontObject;
import org.lwjgl.util.vector.Matrix4f;

import static org.lwjgl.opengl.GL11.*;

public class TileEntityRendererChargingStation extends TileEntitySpecialRenderer {
    private IModelCustom model;

    public TileEntityRendererChargingStation() {
        model = AdvancedModelLoader.loadModel(new ResourceLocation(Reference.MODEL_CHARGING_STATION));
    }

    @Override
    public void renderTileEntityAt(TileEntity tileEntity, double x, double y, double z, float partialTicks) {
        if (tileEntity != null) {
            glPushMatrix();
            float colorMul = (float) (Math.sin(Minecraft.getMinecraft().theWorld.getWorldTime() * 0.2));
            glColor3d(colorMul, colorMul, colorMul);
            glTranslated(x, y, z);
            bindTexture(TextureMap.locationBlocksTexture);
            glDisable(GL_LIGHTING);
            RenderUtils.disableLightmap();
            Tessellator.instance.startDrawingQuads();
            Matrix4f mat = new Matrix4f();
            RenderUtils.rotateFromBlock(mat, tileEntity.getWorldObj(), tileEntity.xCoord, tileEntity.yCoord, tileEntity.zCoord);
            RenderUtils.tesseleteModelAsBlock(mat, ((WavefrontObject) model).groupObjects.get(1), MatterOverdriveIcons.charging_station, 0, 0, 0, (int) (colorMul * 20) + 220, false, null);
            Tessellator.instance.draw();
            RenderUtils.enableLightmap();
            glEnable(GL_LIGHTING);
            glPopMatrix();
        }
    }
}
