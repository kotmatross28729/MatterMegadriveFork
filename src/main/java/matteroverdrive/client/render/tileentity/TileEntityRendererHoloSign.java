package matteroverdrive.client.render.tileentity;

import matteroverdrive.Reference;
import matteroverdrive.blocks.BlockHoloSign;
import matteroverdrive.tile.TileEntityHoloSign;
import matteroverdrive.util.RenderUtils;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.util.ForgeDirection;

import static matteroverdrive.util.MOBlockHelper.getLeftSide;
import static matteroverdrive.util.MOBlockHelper.getRightSide;

public class TileEntityRendererHoloSign extends TileEntitySpecialRenderer {
    @Override
    public void renderTileEntityAt(TileEntity tileEntity, double x, double y, double z, float ticks) {
        int meta = tileEntity.getWorldObj().getBlockMetadata(tileEntity.xCoord, tileEntity.yCoord, tileEntity.zCoord);
        ForgeDirection side = ForgeDirection.getOrientation(meta);

        RenderUtils.beginDrawinngBlockScreen(x, y, z, side, Reference.COLOR_HOLO, tileEntity, -0.8375, 0.2f);

        if (tileEntity instanceof TileEntityHoloSign) {
            String text = ((TileEntityHoloSign) tileEntity).getText();
            if (text != null) {
                String[] infos = text.split("\n");
                int leftMargin = 10;
                int rightMargin = 10;
                float maxSize = 4f;
                ForgeDirection leftSide = ForgeDirection.getOrientation(getLeftSide(meta));
                if (tileEntity.getWorldObj().getBlock(tileEntity.xCoord + leftSide.offsetY, tileEntity.yCoord + leftSide.offsetY, tileEntity.zCoord + leftSide.offsetZ) instanceof BlockHoloSign) {
                    leftMargin = 0;
                    maxSize = 8;
                }
                ForgeDirection rightSide = ForgeDirection.getOrientation(getRightSide(meta));
                if (tileEntity.getWorldObj().getBlock(tileEntity.xCoord + rightSide.offsetY, tileEntity.yCoord + rightSide.offsetY, tileEntity.zCoord + rightSide.offsetZ) instanceof BlockHoloSign) {
                    rightMargin = 0;
                    maxSize = 8;
                }

                if (((TileEntityHoloSign) tileEntity).getConfigs().getBoolean("AutoLineSize", false)) {
                    RenderUtils.drawScreenInfoWithLocalAutoSize(infos, Reference.COLOR_HOLO, side, leftMargin, rightMargin, maxSize);
                } else {
                    RenderUtils.drawScreenInfoWithGlobalAutoSize(infos, Reference.COLOR_HOLO, side, leftMargin, rightMargin, maxSize);
                }
            }
        }

        RenderUtils.endDrawinngBlockScreen();
    }
}
