package matteroverdrive.api.matter;

import net.minecraftforge.common.util.ForgeDirection;

/**
 * Used by matter receivers to receive matter.
 */
public interface IMatterReceiver {
    /**
     * @return the matter stored.
     */
    int getMatterStored();

    /**
     * @return matter capacity.
     */
    int getMatterCapacity();

    /**
     * Used to receive matter.
     *
     * @param side      the receiving side.
     * @param amount    amount of matter received.
     * @param simulated is this a simulation ?
     *                  No matter will be received.
     *                  Used to check if the specified amount of matter can be received.
     * @return the actual received amount of matter.
     */
    int receiveMatter(ForgeDirection side, int amount, boolean simulated);
}
