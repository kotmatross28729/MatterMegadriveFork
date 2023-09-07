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

public class RendererBlockReplicator implements ISimpleBlockRenderingHandler {
    public static int renderID;
    private IModelCustom model;

    public RendererBlockReplicator() {
        renderID = RenderingRegistry.getNextAvailableRenderId();
        model = AdvancedModelLoader.loadModel(new ResourceLocation(Reference.MODEL_REPLICATOR));
    }

    @Override
    public void renderInventoryBlock(Block block, int metadata, int modelId, RenderBlocks renderer) {
        Tessellator.instance.startDrawing(GL11.GL_TRIANGLES);
        Matrix4f mat = new Matrix4f();
        mat.translate(new Vector3f(-0.5f, 0, -0.5f));
        renderBlock(mat, 0, 0, 0, -1);
        Tessellator.instance.draw();
    }

    @Override
    public boolean renderWorldBlock(IBlockAccess world, int x, int y, int z, Block block, int modelId, RenderBlocks renderer) {
        Matrix4f rot = new Matrix4f();
        rot.translate(new Vector3f(0, 0.5f, 0));
        RenderUtils.rotateFromBlock(rot, world, x, y, z);
        renderBlock(rot, x, y, z, block.getMixedBrightnessForBlock(world, x, y, z));
        return true;
    }

    protected void renderBlock(Matrix4f mat, int x, int y, int z, int brightness) {
        Tessellator.instance.draw();
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
