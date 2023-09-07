package mekanism.api.transmitters;

import mekanism.api.Coord4D;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

import java.util.Collection;

public interface IGridTransmitter<A, N extends DynamicNetwork<A, N>> extends ITransmitter {
    boolean hasTransmitterNetwork();

    /**
     * Gets the network currently in use by this transmitter segment.
     *
     * @return network this transmitter is using
     */
    N getTransmitterNetwork();

    /**
     * Sets this transmitter segment's network to a new value.
     *
     * @param network - network to set to
     */
    void setTransmitterNetwork(N network);

    int getTransmitterNetworkSize();

    int getTransmitterNetworkAcceptorSize();

    String getTransmitterNetworkNeeded();

    String getTransmitterNetworkFlow();

    String getTransmitterNetworkBuffer();

    double getTransmitterNetworkCapacity();

    int getCapacity();

    World world();

    Coord4D coord();

    Coord4D getAdjacentConnectableTransmitterCoord(ForgeDirection side);

    A getAcceptor(ForgeDirection side);

    boolean isValid();

    boolean isOrphan();

    void setOrphan(boolean orphaned);

    N createEmptyNetwork();

    N mergeNetworks(Collection<N> toMerge);

    N getExternalNetwork(Coord4D from);

    void takeShare();

    Object getBuffer();
}
