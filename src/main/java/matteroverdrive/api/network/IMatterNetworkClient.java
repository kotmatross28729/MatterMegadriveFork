package matteroverdrive.api.network;

import matteroverdrive.matter_network.MatterNetworkPacket;
import matteroverdrive.matter_network.MatterNetworkPacketQueue;
import net.minecraftforge.common.util.ForgeDirection;

public interface IMatterNetworkClient extends IMatterNetworkConnection, IMatterNetworkHandler {
    /**
     * Can the given packet be preformed by the machine.
     * This is also where the packet filtering is handled.
     * This determines if a packet should be queued for processing in the client.
     * Processing of packets does not mean that the packet has to be preformed bt the client. Packets can just be ignored in the processing stage.
     *
     * @param packet The packet.
     * @return can the packet be preformed.
     */
    boolean canPreform(MatterNetworkPacket packet);

    /**
     * Used to queue the received packet into the machine's queue.
     * In most machines queueing is the same. Packets are added to the queue and later processed.
     * {@link #canPreform(MatterNetworkPacket)} determines if the packet can even reach the queueing stage.
     *
     * @param packet the packet being queued.
     * @param from   the side from which the packed was received.
     */
    void queuePacket(MatterNetworkPacket packet, ForgeDirection from);

    /**
     * Gets the Packet Queue of the machine at the specified queue ID.
     * This allows the implementation of multiple packet queues in the client.
     *
     * @param queueID the ID of the Queue.
     * @return the Packet Queue at the given ID.
     */
    MatterNetworkPacketQueue<MatterNetworkPacket> getPacketQueue(int queueID);

    /**
     * @return the number of queues in the machine.
     */
    int getPacketQueueCount();
}
