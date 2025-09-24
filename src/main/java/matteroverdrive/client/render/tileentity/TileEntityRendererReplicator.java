package matteroverdrive.client.render.tileentity;

import matteroverdrive.Reference;
import matteroverdrive.init.MatterOverdriveIcons;
import matteroverdrive.tile.TileEntityMachineReplicator;
import matteroverdrive.util.RenderUtils;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.AdvancedModelLoader;
import net.minecraftforge.client.model.IModelCustom;
import net.minecraftforge.client.model.obj.GroupObject;
import net.minecraftforge.client.model.obj.WavefrontObject;
import org.lwjgl.opengl.GL11;
import static org.lwjgl.opengl.GL11.glTranslated;
import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector3f;

public class TileEntityRendererReplicator extends TileEntitySpecialRenderer {
    EntityItem itemEntity;
    private final IModelCustom model;
    
    public TileEntityRendererReplicator() {
        model = AdvancedModelLoader.loadModel(new ResourceLocation(Reference.MODEL_REPLICATOR));
    }
    
    protected void renderBlock(Matrix4f mat, int x, int y, int z, int brightness) {
        Tessellator.instance.startDrawing(GL11.GL_TRIANGLES);
        GroupObject front = ((WavefrontObject) model).groupObjects.get(0);
        GroupObject inside = ((WavefrontObject) model).groupObjects.get(1);
        GroupObject shell = ((WavefrontObject) model).groupObjects.get(2);
        GroupObject vents = ((WavefrontObject) model).groupObjects.get(3);
        GroupObject back = ((WavefrontObject) model).groupObjects.get(4);
        
        RenderUtils.tesseleteModelAsBlock(mat, front, MatterOverdriveIcons.replicator, x, y, z, brightness, true, null);
        RenderUtils.tesseleteModelAsBlock(mat, inside, MatterOverdriveIcons.replicator, x, y, z, brightness, true, null);
        RenderUtils.tesseleteModelAsBlock(mat, vents, MatterOverdriveIcons.Vent, x, y, z, brightness, true, null);
        RenderUtils.tesseleteModelAsBlock(mat, shell, MatterOverdriveIcons.Base, x, y, z, brightness, true, null);
        RenderUtils.tesseleteModelAsBlock(mat, back, MatterOverdriveIcons.Network_port_square, x, y, z, brightness, true, null);
        Tessellator.instance.draw();
    }
    
    @Override
    public void renderTileEntityAt(TileEntity entity, double x, double y, double z, float ticks) {
        TileEntityMachineReplicator replicator = (TileEntityMachineReplicator) entity;
        if (replicator != null) {
            GL11.glPushMatrix();
            renderItem(replicator, x, y, z);
            
            glTranslated(x, y, z);
            bindTexture(TextureMap.locationBlocksTexture);
            Matrix4f rot = new Matrix4f();
            rot.translate(new Vector3f(0, 0.5f, 0));
            Block block = replicator.getWorldObj().getBlock(replicator.xCoord, replicator.yCoord, replicator.zCoord);
            RenderUtils.rotateFromBlock(rot, replicator.getWorldObj(), replicator.xCoord, replicator.yCoord, replicator.zCoord);
            renderBlock(rot, 0, 0, 0, block.getMixedBrightnessForBlock(replicator.getWorldObj(), replicator.xCoord, replicator.yCoord, replicator.zCoord));
            
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
