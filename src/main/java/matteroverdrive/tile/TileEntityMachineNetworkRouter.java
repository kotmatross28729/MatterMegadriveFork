package matteroverdrive.tile;

public class TileEntityMachineNetworkRouter extends TileEntityMachinePacketQueue {

    public TileEntityMachineNetworkRouter() {
        super(4);
        playerSlotsHotbar = true;
    }

    @Override
    protected void onActiveChange() {

    }
}
