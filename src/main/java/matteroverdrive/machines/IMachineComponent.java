package matteroverdrive.machines;

import cpw.mods.fml.relauncher.Side;
import matteroverdrive.api.inventory.UpgradeTypes;
import matteroverdrive.data.Inventory;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

import java.util.EnumSet;

public interface IMachineComponent<T extends MOTileEntityMachine> {
    void readFromNBT(NBTTagCompound nbt, EnumSet<MachineNBTCategory> categories);

    void writeToNBT(NBTTagCompound nbt, EnumSet<MachineNBTCategory> categories, boolean toDisk);

    void registerSlots(Inventory inventory);

    void update(T machine);

    boolean isAffectedByUpgrade(UpgradeTypes type);

    boolean isActive();

    void onActiveChange(T machine);

    void onAwake(T machine, Side side);

    void onPlaced(World world, EntityLivingBase entityLiving, T machine);
}
