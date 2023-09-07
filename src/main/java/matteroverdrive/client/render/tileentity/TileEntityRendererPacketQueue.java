package matteroverdrive.client.render.tileentity;

import matteroverdrive.Reference;
import matteroverdrive.blocks.BlockNetworkSwitch;
import matteroverdrive.init.MatterOverdriveIcons;
import matteroverdrive.tile.TileEntityMachinePacketQueue;
import matteroverdrive.util.RenderUtils;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;

import static org.lwjgl.opengl.GL11.*;

public class TileEntityRendererPacketQueue extends TileEntitySpecialRenderer {
    Block fakeBlock = new BlockNetworkSwitch(Material.iron, "fake_block");

    @Override
    public void renderTileEntityAt(TileEntity tileEntity, double x, double y, double z, float ticks) {
        glPushMatrix();
        glTranslated(x, y, z);
        if (tileEntity instanceof TileEntityMachinePacketQueue) {
            if (((TileEntityMachinePacketQueue) tileEntity).flashTime > 0) {
                renderBlock(fakeBlock, RenderBlocks.getInstance());
            }
        }

        glPopMatrix();
    }

    private void renderBlock(Block block, RenderBlocks renderer) {
        IIcon icon = MatterOverdriveIcons.packet_queue_active;
        float distance = 0.1f;

        glDisable(GL_LIGHTING);
        glEnable(GL_BLEND);
        glBlendFunc(GL_ONE, GL_ONE);
        RenderUtils.disableLightmap();
        RenderUtils.setBlockTextureSheet();
        RenderUtils.drawCube(-0.01, -0.01, -0.01, 1.02, 1.02, 1.02, icon.getMinU(), icon.getMinV(), icon.getMaxU(), icon.getMaxV(), Reference.COLOR_HOLO);
        glDisable(GL_BLEND);
        glEnable(GL_LIGHTING);
        RenderUtils.enableLightmap();
    }
}
