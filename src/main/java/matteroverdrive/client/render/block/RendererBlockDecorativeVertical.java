package matteroverdrive.client.render.block;

import cpw.mods.fml.client.registry.RenderingRegistry;
import matteroverdrive.blocks.BlockDecorative;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.world.IBlockAccess;

public class RendererBlockDecorativeVertical extends MOBlockRenderer {
    public static int renderID;

    public RendererBlockDecorativeVertical() {
        renderID = RenderingRegistry.getNextAvailableRenderId();
    }

    @Override
    public boolean renderWorldBlock(IBlockAccess world, int x, int y, int z, Block block, int modelId, RenderBlocks renderer) {
        int meta = world.getBlockMetadata(x, y, z);
        if (block instanceof BlockDecorative) {
            if (((BlockDecorative) block).canBeRotated()) {
                renderer.uvRotateBottom = meta;
                renderer.uvRotateTop = meta;
                renderer.uvRotateEast = meta;
                renderer.uvRotateWest = meta;
                renderer.uvRotateNorth = meta;
                renderer.uvRotateSouth = meta;
            } else {
                //GuiColor color = new GuiColor(block.getRenderColor(meta));
                //renderer.colorRedTopRight = renderer.colorRedTopLeft = renderer.colorRedBottomLeft = renderer.colorRedBottomRight = color.getFloatR();
            }
        }
        renderer.renderStandardBlock(block, x, y, z);
        renderer.uvRotateBottom = 0;
        renderer.uvRotateTop = 0;
        renderer.uvRotateEast = 0;
        renderer.uvRotateWest = 0;
        renderer.uvRotateNorth = 0;
        renderer.uvRotateSouth = 0;
        return true;
    }

    @Override
    public int getRenderId() {
        return renderID;
    }
}
