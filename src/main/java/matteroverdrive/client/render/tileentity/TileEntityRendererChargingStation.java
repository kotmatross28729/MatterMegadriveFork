package matteroverdrive.client.render.tileentity;

import matteroverdrive.client.render.IInventoryRender;
import matteroverdrive.client.render.ItemRenderBase;
import static matteroverdrive.client.render.TE_Resource_Manager.CHARGING_STATION_MODEL;
import static matteroverdrive.client.render.TE_Resource_Manager.CHARGING_STATION_TEXTURE;
import matteroverdrive.init.MatterOverdriveBlocks;
import matteroverdrive.util.RenderUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.item.Item;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.client.IItemRenderer;
import org.lwjgl.opengl.GL11;
import static org.lwjgl.opengl.GL11.GL_LIGHTING;
import static org.lwjgl.opengl.GL11.glColor3d;
import static org.lwjgl.opengl.GL11.glDisable;
import static org.lwjgl.opengl.GL11.glEnable;
import static org.lwjgl.opengl.GL11.glPopMatrix;
import static org.lwjgl.opengl.GL11.glPushMatrix;
import static org.lwjgl.opengl.GL11.glTranslated;

public class TileEntityRendererChargingStation extends TileEntitySpecialRenderer implements IInventoryRender {
    
    @Override
    public void renderTileEntityAt(TileEntity tileEntity, double x, double y, double z, float partialTicks) {
        if (tileEntity != null) {
            glPushMatrix();
            glTranslated(x + 0.5f, y, z + 0.5f);
            RenderUtils.rotateFromBlock(tileEntity.getWorldObj(), tileEntity.xCoord, tileEntity.yCoord, tileEntity.zCoord);
 
            bindTexture(CHARGING_STATION_TEXTURE);
            CHARGING_STATION_MODEL.renderPart("Base");
            
            float colorMul =  0.85f + 0.15f * (float) (Math.sin(Minecraft.getMinecraft().theWorld.getWorldTime() * 0.2));
            glColor3d(colorMul, colorMul, colorMul);
            glDisable(GL_LIGHTING);
            RenderUtils.disableLightmap();
            CHARGING_STATION_MODEL.renderPart("Rod");
            RenderUtils.enableLightmap();
            glEnable(GL_LIGHTING);
            
            glPopMatrix();
        }
    }
    
    @Override
    public Item getItemForRenderer() {
        return Item.getItemFromBlock(MatterOverdriveBlocks.chargingStation);
    }
    
    @Override
    public IItemRenderer getRenderer() {
        return new ItemRenderBase() {
            public void renderInventory() {
                GL11.glTranslated(0, -3, 0);
                GL11.glScaled(2.5, 2.5, 2.5);
            }
            public void renderCommon() {
                GL11.glScaled(2, 2, 2);
                GL11.glRotated(90, 0, 1, 0);
                
                bindTexture(CHARGING_STATION_TEXTURE);
                CHARGING_STATION_MODEL.renderPart("Base");
                glDisable(GL_LIGHTING);
                RenderUtils.disableLightmap();
                CHARGING_STATION_MODEL.renderPart("Rod");
                RenderUtils.enableLightmap();
                glEnable(GL_LIGHTING);
            }};
    }
}
