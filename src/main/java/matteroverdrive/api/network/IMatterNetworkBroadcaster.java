package matteroverdrive.api.network;

import net.minecraft.nbt.NBTTagCompound;

/**
 * This is used by machines that can broadcast messaged over the Matter Network.
 */
public interface IMatterNetworkBroadcaster extends IMatterNetworkConnection {
    /**
     * Gets the filter used in the broadcasted packets.
     *
     * @return the broadcast filter.
     * This usually contains a list of Block coordinates the broadcaster can broadcast to.
     */
    NBTTagCompound getFilter();
}
