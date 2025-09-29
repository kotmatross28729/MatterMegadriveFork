package matteroverdrive.client.render.tileentity;

import static matteroverdrive.client.render.TE_Resource_Manager.PATTERN_STORAGE_MODEL;
import static matteroverdrive.client.render.TE_Resource_Manager.PATTERN_STORAGE_TEXTURE;
import static matteroverdrive.client.render.TE_Resource_Manager.VENT_TEXTURE;
import matteroverdrive.tile.TileEntityMachinePatternStorage;
import matteroverdrive.util.RenderUtils;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import org.lwjgl.opengl.GL11;

public class TileEntityRendererPatterStorage extends TileEntitySpecialRenderer {
    
    @Override
    public void renderTileEntityAt(TileEntity entity, double x, double y, double z, float ticks) {
        TileEntityMachinePatternStorage patternStorage = (TileEntityMachinePatternStorage) entity;
        if (patternStorage != null) {
            GL11.glPushMatrix();
            GL11.glTranslated(x + 0.5f, y, z + 0.5f);
            RenderUtils.rotateFromBlock(patternStorage.getWorldObj(), patternStorage.xCoord, patternStorage.yCoord, patternStorage.zCoord);
            
            bindTexture(PATTERN_STORAGE_TEXTURE);
            PATTERN_STORAGE_MODEL.renderPart("pattern_storage");
            
            bindTexture(VENT_TEXTURE);
            PATTERN_STORAGE_MODEL.renderPart("Vents");
            
            bindTexture(PATTERN_STORAGE_TEXTURE);
            
            GL11.glTranslated(0.5f, 0.5f, 0.5f);
            RenderUtils.rotateFromBlock(patternStorage.getWorldObj(), patternStorage.xCoord, patternStorage.yCoord, patternStorage.zCoord);
            
            for (int i = 0; i < patternStorage.pattern_storage_slots.length; i++) {
                ItemStack drive = patternStorage.getStackInSlot(patternStorage.pattern_storage_slots[i]);
                if (drive != null) {
                    GL11.glPushMatrix();
                    GL11.glTranslatef(i >= 3 ? -0.3f : 0.3f, 0.1f - 0.2f * (i % 3), -0.2f);
                    PATTERN_STORAGE_MODEL.renderPart("drive");
                    GL11.glPopMatrix();
                }
            }
            GL11.glPopMatrix();
        }
    }
}
