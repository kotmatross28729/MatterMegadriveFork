package matteroverdrive.multiblock;

public interface IMultiBlockTile {
    boolean canJoinMultiBlockStructure(IMultiBlockTileStructure structure);

    boolean isMultiblockInvalid();

    IMultiBlockTileStructure getMultiBlockHandler();

    void setMultiBlockTileStructure(IMultiBlockTileStructure structure);
}
