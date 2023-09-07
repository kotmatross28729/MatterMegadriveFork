package mekanism.api.transmitters;

import net.minecraftforge.common.util.ForgeDirection;

public interface IBlockableConnection {
    boolean canConnectMutual(ForgeDirection side);

    boolean canConnect(ForgeDirection side);
}
