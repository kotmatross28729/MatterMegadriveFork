package matteroverdrive.client.render.tileentity;

import matteroverdrive.Reference;
import matteroverdrive.init.MatterOverdriveIcons;
import matteroverdrive.tile.TileEntityMachinePatternStorage;
import matteroverdrive.util.RenderUtils;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.client.model.AdvancedModelLoader;
import net.minecraftforge.client.model.IModelCustom;
import net.minecraftforge.client.model.obj.GroupObject;
import net.minecraftforge.client.model.obj.WavefrontObject;
import net.minecraftforge.common.util.ForgeDirection;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.vector.Matrix4f;

public class TileEntityRendererPatterStorage extends TileEntitySpecialRenderer {
    private final IModelCustom model;
    private final ResourceLocation texture;

    public TileEntityRendererPatterStorage() {
        texture = new ResourceLocation(Reference.PATH_BLOCKS + "pattern_storage.png");
        model = AdvancedModelLoader.loadModel(new ResourceLocation(Reference.MODEL_PATTERN_STORAGE));
    }
    
    protected void renderBlock(Matrix4f mat, int x, int y, int z, int brightness) {
        Tessellator.instance.startDrawing(GL11.GL_TRIANGLES);
        GroupObject vent = ((WavefrontObject) model).groupObjects.get(0);
        GroupObject base = ((WavefrontObject) model).groupObjects.get(1);
        
        RenderUtils.tesseleteModelAsBlock(mat, vent, MatterOverdriveIcons.Vent, x, y, z, brightness, true, null);
        RenderUtils.tesseleteModelAsBlock(mat, base, MatterOverdriveIcons.pattern_storage, x, y, z, brightness, true, null);

        Tessellator.instance.draw();
    }
    
    @Override
    public void renderTileEntityAt(TileEntity entity, double x, double y, double z, float ticks) {
        TileEntityMachinePatternStorage patternStorage = (TileEntityMachinePatternStorage) entity;
        if (patternStorage != null) {
            GL11.glPushMatrix();
            GL11.glTranslated(x, y, z);
            Matrix4f rot = new Matrix4f();
            
            Block block = patternStorage.getWorldObj().getBlock(patternStorage.xCoord, patternStorage.yCoord, patternStorage.zCoord);
            
            RenderUtils.rotateFromBlock(rot, patternStorage.getWorldObj(), patternStorage.xCoord, patternStorage.yCoord, patternStorage.zCoord);
            bindTexture(TextureMap.locationBlocksTexture);
            renderBlock(rot, 0, 0, 0, block.getMixedBrightnessForBlock(patternStorage.getWorldObj(), patternStorage.xCoord, patternStorage.yCoord, patternStorage.zCoord));
            
            bindTexture(texture);
            GL11.glTranslated(0.5f, 0.5f, 0.5f);
            RenderUtils.rotateFromBlock(patternStorage.getWorldObj(), patternStorage.xCoord, patternStorage.yCoord, patternStorage.zCoord);
            
            for (int i = 0; i < patternStorage.pattern_storage_slots.length; i++) {
                ItemStack drive = patternStorage.getStackInSlot(patternStorage.pattern_storage_slots[i]);
                if (drive != null) {
                    GL11.glPushMatrix();
                    GL11.glTranslatef(i >= 3 ? -0.3f : 0.3f, 0.1f - 0.2f * (i % 3), -0.2f);
                    model.renderPart("drive");
                    GL11.glPopMatrix();
                }
            }
            GL11.glPopMatrix();
        }
    }
}
