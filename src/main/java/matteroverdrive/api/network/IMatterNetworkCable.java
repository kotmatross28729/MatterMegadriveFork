package matteroverdrive.api.network;

import matteroverdrive.matter_network.MatterNetworkPacket;
import net.minecraftforge.common.util.ForgeDirection;

/**
 * Used by Matter Network Cables
 */
public interface IMatterNetworkCable extends IMatterNetworkConnection {
    /**
     * @return is the cable valid.
     */
    boolean isValid();

    /**
     * Called when the cable needs to broadcast a packet.
     *
     * @param packet the packet being broadcasted.
     * @param from   the direction of the broadcast.
     */
    void broadcast(MatterNetworkPacket packet, ForgeDirection from);
}
