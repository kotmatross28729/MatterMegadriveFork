package matteroverdrive.api.matter;

import net.minecraftforge.common.util.ForgeDirection;

/**
 * Used as a interface for all matter storage entities or components.
 */
public interface IMatterStorage {
    /**
     * @return the current matter stored.
     */
    int getMatterStored();

    /**
     * @return the maximum capacity.
     */
    int getCapacity();

    /**
     * Used to receive matter.
     *
     * @param side     the receiving side.
     * @param amount   the amount of received matter.
     * @param simulate is this a simulation.
     *                 No matter will be stored if this is true.
     *                 Used to check if the given amount of matter can be received.
     * @return the amount of matter received.
     * This is the same, not matter if the call is a simulation.
     */
    int receiveMatter(ForgeDirection side, int amount, boolean simulate);

    /**
     * Used to extract matter.
     *
     * @param direction the extraction side.
     * @param amount    the amount of matter extracted.
     * @param simulate  No matter will be stored if this is set to true.
     *                  Used to check if the specified amount of matter can be extracted.
     * @return the amount of matter extracted.
     * This is the same, no matter if the call is a simulation.
     */
    int extractMatter(ForgeDirection direction, int amount, boolean simulate);

    void setMatterStored(int amount);
}
