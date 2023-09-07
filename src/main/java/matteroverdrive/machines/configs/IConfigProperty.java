package matteroverdrive.machines.configs;

import net.minecraft.nbt.NBTTagCompound;

public interface IConfigProperty {
    String getKey();

    String getUnlocalizedName();

    Object getValue();

    void setValue(Object value);

    void writeToNBT(NBTTagCompound nbt);

    void readFromNBT(NBTTagCompound nbt);

    Class getType();
}
