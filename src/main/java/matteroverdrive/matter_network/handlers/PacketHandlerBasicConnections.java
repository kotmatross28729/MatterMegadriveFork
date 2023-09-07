package matteroverdrive.matter_network.handlers;

import matteroverdrive.Reference;
import matteroverdrive.matter_network.MatterNetworkPacket;
import matteroverdrive.matter_network.packets.MatterNetworkRequestPacket;
import matteroverdrive.util.MatterNetworkHelper;

public class PacketHandlerBasicConnections extends AbstractMatterNetworkPacketHandler {
    @Override
    public void processPacket(MatterNetworkPacket packet, AbstractMatterNetworkPacketHandler.Context context) {
        if (packet instanceof MatterNetworkRequestPacket) {
            MatterNetworkRequestPacket requestPacket = (MatterNetworkRequestPacket) packet;
            if (requestPacket.getRequestType() == Reference.PACKET_REQUEST_NEIGHBOR_CONNECTION || requestPacket.getRequestType() == Reference.PACKET_REQUEST_CONNECTION) {

                if (requestPacket.getRequest() instanceof Class) {
                    if (((Class) requestPacket.getRequest()).isInstance(context.connection)) {
                        MatterNetworkHelper.respondToRequest(context.world, context.connection, requestPacket, Reference.PACKET_RESPONCE_VALID, null);
                    }
                } else {
                    MatterNetworkHelper.respondToRequest(context.world, context.connection, requestPacket, Reference.PACKET_RESPONCE_VALID, null);
                }
            }
        }
    }
}
