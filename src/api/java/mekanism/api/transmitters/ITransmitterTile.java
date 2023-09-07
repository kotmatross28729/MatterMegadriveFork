package mekanism.api.transmitters;

public interface ITransmitterTile<A, N extends DynamicNetwork<A, N>> {
    IGridTransmitter<A, N> getTransmitter();
}
