package matteroverdrive.data.transport;

import matteroverdrive.api.matter.IMatterHandler;
import matteroverdrive.api.transport.IPipe;
import net.minecraft.tileentity.TileEntity;

public interface IFluidPipe extends IPipe<FluidPipeNetwork>, IMatterHandler {
    TileEntity getTile();

    void onNetworkUpdate();
}
