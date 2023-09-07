package matteroverdrive.client.render.block;

import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;
import cpw.mods.fml.client.registry.RenderingRegistry;
import matteroverdrive.Reference;
import matteroverdrive.init.MatterOverdriveIcons;
import matteroverdrive.util.RenderUtils;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.client.model.AdvancedModelLoader;
import net.minecraftforge.client.model.IModelCustom;
import net.minecraftforge.client.model.obj.GroupObject;
import net.minecraftforge.client.model.obj.WavefrontObject;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector3f;

public class RendererBlockPatternStorage implements ISimpleBlockRenderingHandler {
    public static int renderID;
    private IModelCustom model;

    public RendererBlockPatternStorage() {
        renderID = RenderingRegistry.getNextAvailableRenderId();
        model = AdvancedModelLoader.loadModel(new ResourceLocation(Reference.MODEL_PATTERN_STORAGE));
    }

    @Override
    public void renderInventoryBlock(Block block, int metadata, int modelId, RenderBlocks renderer) {
        Tessellator.instance.startDrawing(GL11.GL_TRIANGLES);
        Matrix4f rot = new Matrix4f();
        rot.translate(new Vector3f(-0.5f, -0.5f, -0.5f));
        renderBlock(rot, 0, 0, 0, -1);
        Tessellator.instance.draw();
    }

    @Override
    public boolean renderWorldBlock(IBlockAccess world, int x, int y, int z, Block block, int modelId, RenderBlocks renderer) {
        Matrix4f rot = new Matrix4f();
        RenderUtils.rotateFromBlock(rot, world, x, y, z);
        renderBlock(rot, x, y, z, block.getMixedBrightnessForBlock(world, x, y, z));
        return true;
    }

    protected void renderBlock(Matrix4f mat, int x, int y, int z, int brightness) {
        Tessellator.instance.draw();
        Tessellator.instance.startDrawing(GL11.GL_TRIANGLES);
        GroupObject base = ((WavefrontObject) model).groupObjects.get(1);
        GroupObject vent = ((WavefrontObject) model).groupObjects.get(0);


        RenderUtils.tesseleteModelAsBlock(mat, base, MatterOverdriveIcons.pattern_storage, x, y, z, brightness, true, null);
        RenderUtils.tesseleteModelAsBlock(mat, vent, MatterOverdriveIcons.Vent, x, y, z, brightness, true, null);
        Tessellator.instance.draw();
        Tessellator.instance.startDrawingQuads();
    }

    @Override
    public boolean shouldRender3DInInventory(int modelId) {
        return true;
    }

    @Override
    public int getRenderId() {
        return renderID;
    }
}
