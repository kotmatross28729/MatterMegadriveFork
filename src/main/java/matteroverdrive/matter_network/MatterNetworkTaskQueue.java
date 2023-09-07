package matteroverdrive.matter_network;

import cpw.mods.fml.common.network.ByteBufUtils;
import io.netty.buffer.ByteBuf;
import matteroverdrive.api.network.IMatterNetworkConnection;
import matteroverdrive.api.network.MatterNetworkTask;
import matteroverdrive.api.network.MatterNetworkTaskState;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;


public class MatterNetworkTaskQueue<T extends MatterNetworkTask> extends MatterNetworkQueue<T> {
    public MatterNetworkTaskQueue(IMatterNetworkConnection connection, int capacity) {
        super("Tasks", connection, capacity);
    }

    public void drop() {
        for (MatterNetworkTask task : elements) {
            task.setState(MatterNetworkTaskState.INVALID);
        }

        elements.clear();
    }

    public T dropWithID(long id) {
        for (int i = 0; i < elements.size(); i++) {
            if (elements.get(i).getId() == id) {
                return elements.remove(i);
            }
        }
        return null;
    }

    public void tickAllAlive(World world, boolean alive) {
        for (int i = 0; i < elements.size(); i++) {
            if (elements.get(i).isValid(world)) {
                elements.get(i).setAlive(alive);
            }
        }
    }

    public T getWithID(long id) {
        for (int i = 0; i < elements.size(); i++) {
            if (elements.get(i).getId() == id) {
                return elements.get(i);
            }
        }
        return null;
    }

    @Override
    protected void readElementFromNBT(NBTTagCompound tagCompound, MatterNetworkTask element) {
        element.readFromNBT(tagCompound);
    }

    @Override
    protected void writeElementToNBT(NBTTagCompound tagCompound, MatterNetworkTask element) {
        element.writeToNBT(tagCompound);
        tagCompound.setInteger("Type", MatterNetworkRegistry.getTaskID(element.getClass()));
    }

    @Override
    protected void readElementFromBuffer(ByteBuf byteBuf, T element) {
        element.readFromNBT(ByteBufUtils.readTag(byteBuf));
    }

    @Override
    protected void writeElementToBuffer(ByteBuf byteBuf, T element) {
        NBTTagCompound tagCompound = new NBTTagCompound();
        byteBuf.writeInt(MatterNetworkRegistry.getTaskID(element.getClass()));
        element.writeToNBT(tagCompound);
        ByteBufUtils.writeTag(byteBuf, tagCompound);
    }

    @Override
    protected Class getElementClassFromNBT(NBTTagCompound tagCompound) {
        return MatterNetworkRegistry.getTaskClass(tagCompound.getInteger("Type"));
    }

    @Override
    protected Class getElementClassFromBuffer(ByteBuf byteBuf) {
        return MatterNetworkRegistry.getTaskClass(byteBuf.readInt());
    }
}
