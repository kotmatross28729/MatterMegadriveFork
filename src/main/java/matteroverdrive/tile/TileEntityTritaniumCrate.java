package matteroverdrive.tile;

import cpw.mods.fml.relauncher.Side;
import matteroverdrive.data.TileEntityInventory;
import matteroverdrive.data.inventory.CrateSlot;
import matteroverdrive.machines.MachineNBTCategory;
import matteroverdrive.util.MOStringHelper;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

import java.util.EnumSet;

public class TileEntityTritaniumCrate extends MOTileEntity implements IInventory {
    TileEntityInventory inventory;

    public TileEntityTritaniumCrate() {
        inventory = new TileEntityInventory(this, MOStringHelper.translateToLocal("container.tritanium_crate"));
        for (int i = 0; i < 54; i++) {
            CrateSlot slot = new CrateSlot(false);
            inventory.AddSlot(slot);
        }
    }

    @Override
    public void writeCustomNBT(NBTTagCompound nbt, EnumSet<MachineNBTCategory> categories, boolean toDisk) {
        if (categories.contains(MachineNBTCategory.INVENTORY) && toDisk) {
            inventory.writeToNBT(nbt, true);
        }
    }

    @Override
    public void readCustomNBT(NBTTagCompound nbt, EnumSet<MachineNBTCategory> categories) {
        if (categories.contains(MachineNBTCategory.INVENTORY)) {
            inventory.readFromNBT(nbt);
        }
    }

    @Override
    protected void onAwake(Side side) {

    }

    @Override
    public void onAdded(World world, int x, int y, int z) {

    }

    @Override
    public void onPlaced(World world, EntityLivingBase entityLiving) {

    }

    @Override
    public void onDestroyed() {

    }

    @Override
    public void onNeighborBlockChange() {

    }

    @Override
    public void writeToDropItem(ItemStack itemStack) {
        if (!itemStack.hasTagCompound()) {
            itemStack.setTagCompound(new NBTTagCompound());
        }

        inventory.writeToNBT(itemStack.getTagCompound(), true);
    }

    @Override
    public void readFromPlaceItem(ItemStack itemStack) {
        if (itemStack.hasTagCompound()) {
            inventory.readFromNBT(itemStack.getTagCompound());
        }
    }

    public TileEntityInventory getInventory() {
        return inventory;
    }

    @Override
    public int getSizeInventory() {
        return inventory.getSizeInventory();
    }

    @Override
    public ItemStack getStackInSlot(int slot) {
        return inventory.getStackInSlot(slot);
    }

    @Override
    public ItemStack decrStackSize(int slot, int amount) {
        return inventory.decrStackSize(slot, amount);
    }

    @Override
    public ItemStack getStackInSlotOnClosing(int slot) {
        return inventory.getStackInSlotOnClosing(slot);
    }

    @Override
    public void setInventorySlotContents(int slot, ItemStack stack) {
        inventory.setInventorySlotContents(slot, stack);
    }

    @Override
    public String getInventoryName() {
        return inventory.getInventoryName();
    }

    @Override
    public boolean hasCustomInventoryName() {
        return true;
    }

    @Override
    public int getInventoryStackLimit() {
        return inventory.getInventoryStackLimit();
    }

    @Override
    public boolean isUseableByPlayer(EntityPlayer p_70300_1_) {
        return true;
    }

    @Override
    public void openInventory() {
        inventory.openInventory();
    }

    @Override
    public void closeInventory() {
        inventory.closeInventory();
    }

    @Override
    public boolean isItemValidForSlot(int slot, ItemStack stack) {
        return inventory.isItemValidForSlot(slot, stack);
    }
}
