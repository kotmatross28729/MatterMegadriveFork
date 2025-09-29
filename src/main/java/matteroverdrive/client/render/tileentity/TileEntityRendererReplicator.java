package matteroverdrive.client.render.tileentity;

import static matteroverdrive.client.render.TE_Resource_Manager.BASE_TEXTURE;
import static matteroverdrive.client.render.TE_Resource_Manager.NETWORK_PORT_TEXTURE;
import static matteroverdrive.client.render.TE_Resource_Manager.REPLICATOR_MODEL;
import static matteroverdrive.client.render.TE_Resource_Manager.REPLICATOR_TEXTURE;
import static matteroverdrive.client.render.TE_Resource_Manager.VENT_TEXTURE;
import matteroverdrive.tile.TileEntityMachineReplicator;
import matteroverdrive.util.RenderUtils;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import org.lwjgl.opengl.GL11;
import static org.lwjgl.opengl.GL11.glTranslated;

public class TileEntityRendererReplicator extends TileEntitySpecialRenderer {
    EntityItem itemEntity;

    @Override
    public void renderTileEntityAt(TileEntity entity, double x, double y, double z, float ticks) {
        TileEntityMachineReplicator replicator = (TileEntityMachineReplicator) entity;
        if (replicator != null) {
            GL11.glPushMatrix();
            renderItem(replicator, x, y, z);
            glTranslated(x + 0.5f, y, z + 0.5f);
            glTranslated(0, 0.5f, 0);
            RenderUtils.rotateFromBlock(replicator.getWorldObj(), replicator.xCoord, replicator.yCoord, replicator.zCoord);
            
            bindTexture(REPLICATOR_TEXTURE);
            REPLICATOR_MODEL.renderPart("front");
            REPLICATOR_MODEL.renderPart("inside");
            
            bindTexture(VENT_TEXTURE);
            REPLICATOR_MODEL.renderPart("vents");
            
            bindTexture(BASE_TEXTURE);
            REPLICATOR_MODEL.renderPart("shell");
            
            bindTexture(NETWORK_PORT_TEXTURE);
            REPLICATOR_MODEL.renderPart("back");
            
            GL11.glPopMatrix();
        }
    }

    private void renderItem(TileEntityMachineReplicator replicator, double x, double y, double z) {
        ItemStack stack = replicator.getStackInSlot(replicator.OUTPUT_SLOT_ID);
        if (stack != null) {
            if (itemEntity == null) {
                itemEntity = new EntityItem(replicator.getWorldObj(), x, y, z, stack);
            } else if (!ItemStack.areItemStacksEqual(itemEntity.getEntityItem(), stack)) {
                itemEntity.setEntityItemStack(stack);
            }

            itemEntity.hoverStart = (float) (Math.PI / 2);

            RenderManager.instance.renderEntityWithPosYaw(itemEntity, x + 0.5d, y + 0.25, z + 0.5, 0, 0);
        }
    }
}
