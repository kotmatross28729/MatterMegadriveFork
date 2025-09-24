package matteroverdrive.client.render.tileentity;

import matteroverdrive.Reference;
import matteroverdrive.init.MatterOverdriveIcons;
import matteroverdrive.init.MatterOverdriveItems;
import matteroverdrive.tile.TileEntityInscriber;
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
import static org.lwjgl.opengl.GL11.*;
import org.lwjgl.util.vector.Matrix4f;

public class TileEntityRendererInscriber extends TileEntitySpecialRenderer {
    private final IModelCustom model;
    private EntityItem item;
    private final ResourceLocation texture;

    public TileEntityRendererInscriber() {
        model = AdvancedModelLoader.loadModel(new ResourceLocation(Reference.MODEL_INSCRIBER));
        texture = new ResourceLocation(Reference.PATH_BLOCKS + "inscriber.png");
    }
    
    protected void renderBlock(Matrix4f mat, int x, int y, int z, int brightness, int color) {
        Tessellator.instance.startDrawing(GL11.GL_TRIANGLES);
        GroupObject base = ((WavefrontObject) model).groupObjects.get(0);
        
        RenderUtils.tesseleteModelAsBlock(mat, base, MatterOverdriveIcons.inscriber, x, y, z, brightness, true, null);
        
        Tessellator.instance.draw();
    }
    
    @Override
    public void renderTileEntityAt(TileEntity tileEntity, double x, double y, double z, float ticks) {
        if (item == null) {
            item = new EntityItem(tileEntity.getWorldObj());
            item.setEntityItemStack(new ItemStack(MatterOverdriveItems.isolinear_circuit, 1, 2));
        }

        if (tileEntity instanceof TileEntityInscriber) {
            double headX = 0.15 * ((TileEntityInscriber) tileEntity).geatHeadX() + 0.02;
            double headY = 0.1 * ((TileEntityInscriber) tileEntity).geatHeadY();
            
            glColor3f(1, 1, 1);
            glPushMatrix();
            glTranslated(x, y, z);
            
            bindTexture(TextureMap.locationBlocksTexture);
            
            Matrix4f rot = new Matrix4f();
            Block block = tileEntity.getWorldObj().getBlock(tileEntity.xCoord, tileEntity.yCoord, tileEntity.zCoord);
            RenderUtils.rotateFromBlock(rot, tileEntity.getWorldObj(), tileEntity.xCoord, tileEntity.yCoord, tileEntity.zCoord);
            renderBlock(rot, 0, 0, 0, block.getMixedBrightnessForBlock(tileEntity.getWorldObj(), tileEntity.xCoord, tileEntity.yCoord, tileEntity.zCoord), block.getBlockColor());
            
            bindTexture(texture);
            
            GL11.glTranslated(0.5f, 0, 0.5f);
            
            RenderUtils.rotateFromBlock(tileEntity.getWorldObj(), tileEntity.xCoord, tileEntity.yCoord, tileEntity.zCoord);
            glPushMatrix();
            glTranslated(0, 0.6, headX);
            model.renderPart("rail");
            glPopMatrix();

            glPushMatrix();
            glTranslated(headY, 0.84, headX - 0.06);
            model.renderPart("head");
            glPopMatrix();

            ItemStack newStack = ((TileEntityInscriber) tileEntity).getStackInSlot(TileEntityInscriber.MAIN_INPUT_SLOT_ID);
            if (newStack == null) {
                newStack = ((TileEntityInscriber) tileEntity).getStackInSlot(TileEntityInscriber.OUTPUT_SLOT_ID);
            }
            if (newStack != null) {
                item.setEntityItemStack(newStack);
                glPushMatrix();
                glTranslated(-0.23, 0.69, 0);
                glRotated(90, 0, 1, 0);
                glRotated(90, 1, 0, 0);
                item.hoverStart = 0f;
                RenderManager.instance.func_147939_a(item, 0, 0, 0, 0, 0, true);
                glPopMatrix();
            }
            glPopMatrix();
        }
    }
}
