package matteroverdrive.api.matter;

import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.fluids.IFluidHandler;

/**
 * A wrapper for the {@link IFluidHandler}.
 */
public interface IMatterHandler extends IMatterProvider, IMatterReceiver, IFluidHandler {
    /**
     * @return the matter stored
     */
    @Override
    int getMatterStored();

    /**
     * @return the matter capacity.
     */
    @Override
    int getMatterCapacity();

    /**
     * Used to receive matter.
     *
     * @param side     side from which matter is received.
     * @param amount   amount of received matter.
     * @param simulate is this a simulation ?
     * @return amount of actually received matter.
     */
    @Override
    int receiveMatter(ForgeDirection side, int amount, boolean simulate);

    /**
     * Used to extract matter.
     *
     * @param direction side from which matter is extracted.
     * @param amount    amount of extracted matter.
     * @param simulate  is this a simulation ?
     * @return amount of actually extracted matter.
     */
    @Override
    int extractMatter(ForgeDirection direction, int amount, boolean simulate);
}
