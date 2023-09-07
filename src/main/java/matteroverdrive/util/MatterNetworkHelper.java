package matteroverdrive.util;

import matteroverdrive.Reference;
import matteroverdrive.api.network.*;
import matteroverdrive.data.BlockPos;
import matteroverdrive.matter_network.MatterNetworkPacket;
import matteroverdrive.matter_network.packets.MatterNetworkBroadcastPacket;
import matteroverdrive.matter_network.packets.MatterNetworkRequestPacket;
import matteroverdrive.matter_network.packets.MatterNetworkResponsePacket;
import matteroverdrive.matter_network.packets.MatterNetworkTaskPacket;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.common.util.Constants;
import net.minecraftforge.common.util.ForgeDirection;

public class MatterNetworkHelper {
    public static boolean broadcastPacketInDirection(World world, MatterNetworkPacket packet, IMatterNetworkConnection source, ForgeDirection direction) {
        //if the source connection can connect From Side
        if (source.canConnectFromSide(direction)) {
            BlockPos position = source.getPosition().step(direction);
            if (world.getChunkProvider().chunkExists(position.x >> 4, position.z >> 4)) {
                ForgeDirection oppositeDirection = direction.getOpposite();
                TileEntity e = position.getTileEntity(world);
                //if there is any connection in that direction
                if (e instanceof IMatterNetworkConnection) {
                    IMatterNetworkConnection connection = (IMatterNetworkConnection) e;
                    //check if the packet has passed trough the connection or if it can connect from opposite source side
                    if (!packet.hasPassedTrough(connection) && connection.canConnectFromSide(oppositeDirection)) {
                        if (connection instanceof IMatterNetworkCable) {
                            //if the connection is a cable
                            IMatterNetworkCable cable = (IMatterNetworkCable) connection;
                            if (cable.isValid()) {
                                cable.broadcast(packet, direction);
                                return true;
                            }
                        } else if (connection instanceof IMatterNetworkClient) {
                            //if the connection is a client
                            IMatterNetworkClient c = (IMatterNetworkClient) connection;
                            if (c.canPreform(packet)) {
                                c.queuePacket(packet, oppositeDirection);
                                return true;
                            }
                        }
                    }
                }
            }
        }
        return false;
    }

    public static boolean broadcastPacketInDirection(World world, byte queueID, MatterNetworkTask task, IMatterNetworkDispatcher dispatcher, ForgeDirection direction) {
        return broadcastPacketInDirection(world, queueID, task, dispatcher, direction, null);
    }

    public static boolean broadcastPacketInDirection(World world, byte queueID, MatterNetworkTask task, IMatterNetworkDispatcher dispatcher, ForgeDirection direction, NBTTagCompound filter) {
        return broadcastPacketInDirection(world, new MatterNetworkTaskPacket(dispatcher, task, queueID, direction, filter), dispatcher, direction);
    }

    public static NBTTagCompound getFilterFromPositions(BlockPos... positions) {
        NBTTagCompound tagCompound = new NBTTagCompound();
        NBTTagList tagList = new NBTTagList();
        for (int i = 0; i < positions.length; i++) {
            NBTTagCompound positionNBT = new NBTTagCompound();
            positions[i].writeToNBT(positionNBT);
            tagList.appendTag(positionNBT);
        }
        tagCompound.setTag(IMatterNetworkFilter.CONNECTIONS_TAG, tagList);
        return tagCompound;
    }

    public static NBTTagCompound addPositionsToFilter(NBTTagCompound filter, BlockPos... positions) {
        if (filter == null) {
            filter = new NBTTagCompound();
        }

        NBTTagList tagList = filter.getTagList(IMatterNetworkFilter.CONNECTIONS_TAG, Constants.NBT.TAG_COMPOUND);
        for (int i = 0; i < positions.length; i++) {
            NBTTagCompound positionNBT = new NBTTagCompound();
            positions[i].writeToNBT(positionNBT);
            tagList.appendTag(positionNBT);
        }
        filter.setTag(IMatterNetworkFilter.CONNECTIONS_TAG, tagList);
        return filter;
    }

    public static void broadcastConnection(World world, IMatterNetworkConnection connection) {
        for (int i = 0; i < 6; i++) {
            MatterNetworkBroadcastPacket packet = new MatterNetworkBroadcastPacket(connection.getPosition(), Reference.PACKET_BROADCAST_CONNECTION, ForgeDirection.getOrientation(i));
            broadcastPacketInDirection(world, packet, connection, ForgeDirection.getOrientation(i));
        }
    }

    public static void requestNeighborConnections(World world, IMatterNetworkConnection connection) {
        for (int i = 0; i < 6; i++) {
            MatterNetworkRequestPacket packet = new MatterNetworkRequestPacket(connection, Reference.PACKET_REQUEST_NEIGHBOR_CONNECTION, ForgeDirection.getOrientation(i), null);
            broadcastPacketInDirection(world, packet, connection, ForgeDirection.getOrientation(i));
        }
    }

    public static void respondToRequest(World world, IMatterNetworkConnection sender, MatterNetworkRequestPacket packet, int responseType, NBTTagCompound data) {
        if (packet.getSender(world) instanceof IMatterNetworkClient) {
            MatterNetworkResponsePacket responsePacket = new MatterNetworkResponsePacket(sender, responseType, packet.getRequestType(), data, packet.getSenderPort());
            ((IMatterNetworkClient) packet.getSender(world)).queuePacket(responsePacket, packet.getSenderPort());
        }
    }
}
