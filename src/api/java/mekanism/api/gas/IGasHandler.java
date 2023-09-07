package mekanism.api.gas;

import net.minecraftforge.common.util.ForgeDirection;

/**
 * Implement this if your tile entity accepts gas from an external source.
 */
public interface IGasHandler {
    /**
     * Transfer a certain amount of gas to this block.
     *
     * @param stack - gas to add
     * @return gas added
     */
    int receiveGas(ForgeDirection side, GasStack stack, boolean doTransfer);

    @Deprecated
    int receiveGas(ForgeDirection side, GasStack stack);

    /**
     * Draws a certain amount of gas from this block.
     *
     * @param amount - amount to draw
     * @return gas drawn
     */
    GasStack drawGas(ForgeDirection side, int amount, boolean doTransfer);

    @Deprecated
    GasStack drawGas(ForgeDirection side, int amount);

    /**
     * Whether or not this block can accept gas from a certain side.
     *
     * @param side - side to check
     * @param type - type of gas to check
     * @return if block accepts gas
     */
    boolean canReceiveGas(ForgeDirection side, Gas type);

    /**
     * Whether or not this block can be drawn of gas from a certain side.
     *
     * @param side - side to check
     * @param type - type of gas to check
     * @return if block can be drawn of gas
     */
    boolean canDrawGas(ForgeDirection side, Gas type);
}
