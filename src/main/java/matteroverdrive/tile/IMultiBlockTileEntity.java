package matteroverdrive.tile;

import matteroverdrive.data.BlockPos;

import java.util.List;

/**
 * Used by TileEntities belonging to blocks with bounding boxes greater than 1m^3
 */
public interface IMultiBlockTileEntity {

    List<BlockPos> getBoundingBlocks();

}
