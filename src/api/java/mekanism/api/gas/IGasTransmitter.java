package mekanism.api.gas;

import mekanism.api.transmitters.IGridTransmitter;
import net.minecraft.tileentity.TileEntity;

public interface IGasTransmitter extends IGridTransmitter<IGasHandler, GasNetwork> {
    boolean canTransferGasToTube(TileEntity tile);
}
