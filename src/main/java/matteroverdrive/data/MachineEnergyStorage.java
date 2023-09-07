package matteroverdrive.data;

import cofh.api.energy.IEnergyStorage;
import matteroverdrive.api.inventory.UpgradeTypes;
import matteroverdrive.machines.MOTileEntityMachine;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.MathHelper;

public class MachineEnergyStorage<T extends MOTileEntityMachine> implements IEnergyStorage {
    protected int energy;
    protected int capacity;
    protected int maxReceive;
    protected int maxExtract;
    protected final T machine;

    public MachineEnergyStorage(T machine, int capacity) {
        this(machine, capacity, capacity, capacity);
    }

    public MachineEnergyStorage(T machine, int capacity, int maxReceive, int maxExtract) {
        this.machine = machine;
        this.capacity = capacity;
        this.maxReceive = maxReceive;
        this.maxExtract = maxExtract;
    }

    public void readFromNBT(NBTTagCompound tagCompound) {
        this.energy = tagCompound.getInteger("Energy");
    }

    public void writeToNBT(NBTTagCompound tagCompound) {
        tagCompound.setInteger("Energy", energy);
    }

    public int modifyEnergyStored(int amount) {
        int lastEnergy = this.energy;
        this.energy = MathHelper.clamp_int(this.energy + amount, 0, getMaxEnergyStored());
        return this.energy - lastEnergy;
    }

    @Override
    public int receiveEnergy(int amount, boolean simulate) {
        int clampedAmount = Math.min(getMaxEnergyStored() - this.energy, Math.min(getMaxReceive(), amount));
        if (!simulate) {
            this.energy += clampedAmount;
        }

        return clampedAmount;
    }

    @Override
    public int extractEnergy(int amount, boolean simulate) {
        int clampedAmount = Math.min(getMaxEnergyStored(), Math.min(getMaxExtract(), amount));
        if (!simulate) {
            this.energy -= clampedAmount;
        }

        return clampedAmount;
    }

    public int getMaxReceive() {
        return Math.max(0, (int) (maxReceive * machine.getUpgradeMultiply(UpgradeTypes.PowerTransfer)));
    }

    public int getMaxExtract() {
        return Math.max(0, (int) (maxExtract * machine.getUpgradeMultiply(UpgradeTypes.PowerTransfer)));
    }

    @Override
    public int getEnergyStored() {
        return energy;
    }

    @Override
    public int getMaxEnergyStored() {
        return Math.max(0, (int) (capacity * machine.getUpgradeMultiply(UpgradeTypes.PowerStorage)));
    }

    public void setMaxTransfer(int amount) {
        this.setMaxReceive(amount);
        this.setMaxExtract(amount);
    }

    public void setEnergyStored(int energy) {
        this.energy = energy;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public void setMaxReceive(int maxReceive) {
        this.maxReceive = maxReceive;
    }

    public void setMaxExtract(int maxExtract) {
        this.maxExtract = maxExtract;
    }
}
