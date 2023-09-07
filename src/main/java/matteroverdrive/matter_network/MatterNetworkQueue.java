package matteroverdrive.matter_network;

import io.netty.buffer.ByteBuf;
import matteroverdrive.MatterOverdrive;
import matteroverdrive.api.network.IMatterNetworkConnection;
import matteroverdrive.util.MOLog;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import org.apache.logging.log4j.Level;

import java.util.ArrayList;
import java.util.List;

public abstract class MatterNetworkQueue<T> {
    private IMatterNetworkConnection connection;
    protected List<T> elements;
    int capacity = 0;
    String name;

    public MatterNetworkQueue(String name, IMatterNetworkConnection connection, int capacity) {
        this.name = name;
        this.connection = connection;
        elements = new ArrayList<>(capacity);
        this.capacity = capacity;
    }

    public boolean queue(T element) {
        if (elements.size() > 0) {
            try {
                elements.add(elements.size(), element);
                return true;
            } catch (Exception e) {
                MOLog.error("Could not add element to queue", e);
                return false;
            }
        } else {
            return elements.add(element);
        }

    }

    public T dropAt(int i) {
        if (i < elements.size()) {
            return elements.remove(i);
        }
        return null;
    }

    public T dequeue() {
        if (elements.size() > 0) {
            return elements.remove(0);
        }
        return null;
    }

    public T peek() {
        if (elements.size() > 0) {
            return elements.get(0);
        }
        return null;
    }

    public int getLastIndex() {
        if (elements.size() > 0) {
            return elements.size() - 1;
        }
        return -1;
    }

    public T getAt(int i) {
        if (i >= 0 && i < elements.size()) {
            return elements.get(i);
        }
        return null;
    }

    public void clear() {
        elements.clear();
    }

    public boolean remove(T task) {
        return elements.remove(task);
    }

    public int size() {
        return elements.size();
    }

    public int remaintingCapacity() {
        return capacity - elements.size();
    }

    public void readFromNBT(NBTTagCompound tagCompound) {
        if (tagCompound == null)
            return;

        elements.clear();
        NBTTagList tagList = tagCompound.getTagList(name, 10);
        for (int i = 0; i < tagList.tagCount(); i++) {
            try {
                T element = (T) getElementClassFromNBT(tagList.getCompoundTagAt(i)).newInstance();
                readElementFromNBT(tagList.getCompoundTagAt(i), element);
                elements.add(element);
            } catch (InstantiationException e) {
                MOLog.log(Level.ERROR, e, "There was a problem while loading a packet of type %s", getElementClassFromNBT(tagList.getCompoundTagAt(i)));
            } catch (IllegalAccessException e) {
                MOLog.log(Level.ERROR, e, "There was a problem while loading a packet of type %s", getElementClassFromNBT(tagList.getCompoundTagAt(i)));
            }
        }
    }

    public void readFromBuffer(ByteBuf byteBuf) {
        elements.clear();
        int elementsCount = byteBuf.readInt();
        for (int i = 0; i < elementsCount; i++) {
            try {
                T element = (T) getElementClassFromBuffer(byteBuf).newInstance();
                readElementFromBuffer(byteBuf, element);
                elements.add(element);
            } catch (InstantiationException e) {
                MOLog.log(Level.ERROR, e, "There was a problem while loading a packet of type %s", getElementClassFromBuffer(byteBuf));
            } catch (IllegalAccessException e) {
                MOLog.log(Level.ERROR, e, "There was a problem while loading a packet of type %s", getElementClassFromBuffer(byteBuf));
            }
        }
    }

    protected abstract void readElementFromNBT(NBTTagCompound tagCompound, T element);

    protected abstract void readElementFromBuffer(ByteBuf byteBuf, T element);

    protected abstract void writeElementToNBT(NBTTagCompound tagCompound, T element);

    protected abstract void writeElementToBuffer(ByteBuf byteBuf, T element);

    protected abstract Class getElementClassFromNBT(NBTTagCompound tagCompound);

    protected abstract Class getElementClassFromBuffer(ByteBuf byteBuf);

    public void writeToNBT(NBTTagCompound tagCompound) {
        NBTTagList taskList = new NBTTagList();
        for (T element : elements) {
            NBTTagCompound taskNBT = new NBTTagCompound();
            writeElementToNBT(taskNBT, element);
            taskList.appendTag(taskNBT);
        }
        tagCompound.setTag(name, taskList);
    }

    public void writeToBuffer(ByteBuf byteBuf) {
        byteBuf.writeInt(elements.size());
        for (T element : elements) {
            writeElementToBuffer(byteBuf, element);
        }
    }

    public IMatterNetworkConnection getConnection() {
        return connection;
    }
}
