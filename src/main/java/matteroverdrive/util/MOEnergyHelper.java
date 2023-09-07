package matteroverdrive.util;

import cofh.api.energy.IEnergyContainerItem;
import cofh.api.energy.IEnergyProvider;
import cofh.api.energy.IEnergyReceiver;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.util.ForgeDirection;

public class MOEnergyHelper {
    public static final String ENERGY_UNIT = " RF";

    public static String formatEnergy(int energy, int capacity) {
        return MOStringHelper.formatNumber(energy) + " / " + MOStringHelper.formatNumber(capacity) + ENERGY_UNIT;
    }

    public static String formatEnergy(int energy) {
        return formatEnergy("Charge: ", energy);
    }

    public static String formatEnergy(String prefix, int energy) {
        return (prefix != null ? prefix : "") + MOStringHelper.formatNumber(energy) + ENERGY_UNIT;
    }

    public static boolean extractExactAmount(IEnergyProvider provider, ForgeDirection direction, int amount, boolean simulate) {
        int hasEnergy = provider.getEnergyStored(direction);
        if (hasEnergy >= amount) {
            while (amount > 0) {
                if (provider.extractEnergy(direction, amount, true) >= 0) {
                    amount -= provider.extractEnergy(direction, amount, simulate);
                } else {
                    return false;
                }
            }
        }
        return true;
    }

    public static ItemStack setDefaultEnergyTag(ItemStack itemStack, int energy) {
        if (itemStack.stackTagCompound == null) {
            itemStack.setTagCompound(new NBTTagCompound());
        }

        itemStack.stackTagCompound.setInteger("Energy", energy);
        return itemStack;
    }

    public static int extractEnergyFromContainer(ItemStack itemStack, int amount, boolean simulate) {
        return isEnergyContainerItem(itemStack) ? ((IEnergyContainerItem) itemStack.getItem()).extractEnergy(itemStack, amount, simulate) : 0;
    }

    public static int insertEnergyIntoContainer(ItemStack itemStack, int amount, boolean simulate) {
        return isEnergyContainerItem(itemStack) ? ((IEnergyContainerItem) itemStack.getItem()).receiveEnergy(itemStack, amount, simulate) : 0;
    }

    public static boolean isEnergyContainerItem(ItemStack itemStack) {
        return itemStack != null && itemStack.getItem() instanceof IEnergyContainerItem;
    }

    public static int insertEnergyIntoAdjacentEnergyReceiver(TileEntity tileEntity, int side, int amount, boolean simulate) {
        TileEntity var4 = MOBlockHelper.getAdjacentTileEntity(tileEntity, side);
        return var4 instanceof IEnergyReceiver ? ((IEnergyReceiver) var4).receiveEnergy(ForgeDirection.VALID_DIRECTIONS[side ^ 1], amount, simulate) : 0;
    }
}
