package mekanism.api;

/**
 * Implement this class in a TileEntity if you wish for it to be able to heat up a Salination Plant.
 */
public interface ISalinationSolar {
    boolean seesSun();
}
