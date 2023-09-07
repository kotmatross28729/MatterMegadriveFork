package matteroverdrive.matter_network;

import cpw.mods.fml.common.network.ByteBufUtils;
import io.netty.buffer.ByteBuf;
import matteroverdrive.api.network.IMatterNetworkConnection;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

public class MatterNetworkPacketQueue<T extends MatterNetworkPacket> extends MatterNetworkQueue<T> {

    public MatterNetworkPacketQueue(IMatterNetworkConnection connection, int capacity) {
        super("TaskPackets", connection, capacity);
    }

    public MatterNetworkPacketQueue(IMatterNetworkConnection connection) {
        this(connection, 256);
    }

    public void tickAllAlive(World world, boolean alive) {
        for (int i = 0; i < elements.size(); i++) {
            if (elements.get(i).isValid(world)) {
                elements.get(i).tickAlive(world, alive);
            }
        }
    }

    @Override
    protected void readElementFromNBT(NBTTagCompound tagCompound, MatterNetworkPacket element) {
        element.readFromNBT(tagCompound);
    }

    @Override
    protected void writeElementToNBT(NBTTagCompound tagCompound, MatterNetworkPacket element) {
        element.writeToNBT(tagCompound);
        tagCompound.setInteger("Type", MatterNetworkRegistry.getPacketID(element.getClass()));
    }

    @Override
    protected void readElementFromBuffer(ByteBuf byteBuf, T element) {
        element.readFromNBT(ByteBufUtils.readTag(byteBuf));
    }

    @Override
    protected void writeElementToBuffer(ByteBuf byteBuf, T element) {
        NBTTagCompound tagCompound = new NBTTagCompound();
        byteBuf.writeInt(MatterNetworkRegistry.getPacketID(element.getClass()));
        element.writeToNBT(tagCompound);
        ByteBufUtils.writeTag(byteBuf, tagCompound);
    }

    @Override
    protected Class getElementClassFromNBT(NBTTagCompound tagCompound) {
        return MatterNetworkRegistry.getPacketClass(tagCompound.getInteger("Type"));
    }

    @Override
    protected Class getElementClassFromBuffer(ByteBuf byteBuf) {
        return MatterNetworkRegistry.getPacketClass(byteBuf.readInt());
    }
}
