package matteroverdrive.api.network;

import matteroverdrive.data.BlockPos;
import net.minecraftforge.common.util.ForgeDirection;

/**
 * Used by all Machines that can Connect to a Matter Network.
 * This also handled if Matter Network cables will be connected to the machine.
 */
public interface IMatterNetworkConnection {
    /**
     * The block position of the Connection.
     * This is the MAC address equivalent.
     * Used mainly in Packet filters to filter the machines the packet can reach.
     *
     * @return the block position of the Matter Network connection.
     */
    BlockPos getPosition();

    /**
     * Can the Matter Connection connect form a given side.
     *
     * @param side the side of the tested connection.
     * @return can the connection be made trough the given side.
     */
    boolean canConnectFromSide(ForgeDirection side);
}
