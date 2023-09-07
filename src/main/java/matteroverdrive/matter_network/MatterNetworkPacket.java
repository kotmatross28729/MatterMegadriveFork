package matteroverdrive.matter_network;

import matteroverdrive.api.network.IMatterNetworkConnection;
import matteroverdrive.data.BlockPos;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

import java.util.HashSet;

public abstract class MatterNetworkPacket {
    protected BlockPos senderPos;
    protected NBTTagCompound filter;
    protected HashSet<MatterNetworkPathNode> path;


    public MatterNetworkPacket() {
        path = new HashSet<>();
    }

    public MatterNetworkPacket(BlockPos senderPos, ForgeDirection port) {
        this();
        this.senderPos = senderPos;
        this.senderPos.setOrientation(port);
    }

    public MatterNetworkPacket(BlockPos senderPos, ForgeDirection port, NBTTagCompound filter) {
        this();
        this.senderPos = senderPos;
        this.senderPos.setOrientation(port);
        this.filter = filter;
    }

    public MatterNetworkPacket addToPath(IMatterNetworkConnection connection, ForgeDirection recivedFrom) {
        MatterNetworkPathNode node = new MatterNetworkPathNode(connection.getPosition(), recivedFrom.getOpposite());
        if (!path.contains(node)) {
            path.add(node);
        }
        return this;
    }

    public boolean hasPassedTrough(IMatterNetworkConnection connection) {
        return path.contains(new MatterNetworkPathNode(connection.getPosition()));
    }

    public IMatterNetworkConnection getSender(World world) {
        if (world != null) {
            TileEntity tileEntity = senderPos.getTileEntity(world);
            if (tileEntity != null && tileEntity instanceof IMatterNetworkConnection)
                return (IMatterNetworkConnection) tileEntity;
        }
        return null;
    }

    public void readFromNBT(NBTTagCompound tagCompound) {
        if (tagCompound != null) {
            if (tagCompound.hasKey("Sender", 10)) {
                senderPos = new BlockPos(tagCompound.getCompoundTag("Sender"));
            }
            if (tagCompound.hasKey("Filter", 10)) {
                filter = tagCompound.getCompoundTag("Filter");
            }
        }
    }

    public void writeToNBT(NBTTagCompound tagCompound) {
        if (tagCompound != null && senderPos != null) {
            if (senderPos != null) {
                NBTTagCompound sender = new NBTTagCompound();
                senderPos.writeToNBT(sender);
                tagCompound.setTag("Sender", sender);
            }
            if (filter != null) {
                tagCompound.setTag("Filter", filter);
            }
        }
    }

    public abstract boolean isValid(World world);

    public NBTTagCompound getFilter() {
        return filter;
    }

    public abstract String getName();

    public ForgeDirection getSenderPort() {
        return senderPos.orientation;
    }

    public void tickAlive(World world, boolean isAlive) {

    }

    public void onAddedToQueue(World world, MatterNetworkPacketQueue packetQueue, int queueID) {

    }
}
