package matteroverdrive.client.render.tileentity;

import matteroverdrive.Reference;
import matteroverdrive.util.RenderUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;

import static org.lwjgl.opengl.GL11.glColor3f;

public class TileEntityRendererContractMarket extends TileEntityRendererMonitor {
    public static ResourceLocation screenTexture = new ResourceLocation(Reference.PATH_BLOCKS + "contract_station_holo.png");

    @Override
    public void drawScreen(TileEntity tileEntity, float ticks) {
        Minecraft.getMinecraft().renderEngine.bindTexture(screenTexture);
        glColor3f(Reference.COLOR_HOLO.getFloatR() * 0.7f, Reference.COLOR_HOLO.getFloatG() * 0.7f, Reference.COLOR_HOLO.getFloatB() * 0.7f);

        RenderUtils.drawPlane(1);
    }
}
