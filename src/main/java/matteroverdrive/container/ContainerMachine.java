package matteroverdrive.container;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import matteroverdrive.container.slot.SlotInventory;
import matteroverdrive.data.Inventory;
import matteroverdrive.data.inventory.UpgradeSlot;
import matteroverdrive.machines.MOTileEntityMachine;
import matteroverdrive.util.MOInventoryHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.ICrafting;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

import java.util.List;

public class ContainerMachine<T extends MOTileEntityMachine> extends MOBaseContainer {
    protected T machine;
    protected int progressScaled;

    public ContainerMachine() {
        super();
    }

    public ContainerMachine(InventoryPlayer inventory, T machine) {
        super(inventory);
        this.machine = machine;
        init(inventory);
    }

    protected void init(InventoryPlayer inventory) {
    }

    public void detectAndSendChanges() {
        super.detectAndSendChanges();
        if (this.machine != null) {
            short progressScaled = (short) (this.machine.getProgress() * Short.MAX_VALUE);
            for (Object icrafting : this.crafters) {
                if (this.progressScaled != progressScaled) {
                    ((ICrafting) icrafting).sendProgressBarUpdate(this, 0, progressScaled);
                }
            }

            this.progressScaled = progressScaled;
        }
    }

    @SideOnly(Side.CLIENT)
    public void updateProgressBar(int slot, int newValue) {
        if (slot == 0)
            this.progressScaled = newValue;
    }

    @Override
    public boolean canInteractWith(EntityPlayer player) {
        return true;
    }

    public void addUpgradeSlots(Inventory inventory) {
        addUpgradeSlots(inventory, 0, 0);
    }

    public void addUpgradeSlots(Inventory inventory, int x, int y) {
        int upgradeSlotIndex = 0;

        for (int i = 0; i < inventory.getSizeInventory(); i++) {
            if (inventory.getSlot(i) instanceof UpgradeSlot) {
                addSlotToContainer(new SlotInventory(inventory, inventory.getSlot(i), x + (upgradeSlotIndex % 5) * 24, y + (upgradeSlotIndex / 5) * 24));
                upgradeSlotIndex++;
            }
        }
    }

    public void addAllSlotsFromInventory(Inventory inventory) {
        for (matteroverdrive.data.inventory.Slot slot : inventory.getSlots()) {
            addSlotToContainer(new SlotInventory(inventory, inventory.getSlot(slot.getId()), 0, 0));
        }
    }

    @Override
    public ItemStack transferStackInSlot(EntityPlayer player, int slotID) {
        ItemStack itemstack = null;
        Slot slot = (Slot) this.inventorySlots.get(slotID);

        if (slot != null && slot.getHasStack()) {
            ItemStack itemstack1 = slot.getStack();
            itemstack = itemstack1.copy();

            if (slotID < machine.getSizeInventory()) {
                putInPlayerInventory(itemstack1);
            } else if (slotID >= machine.getSizeInventory()) {
                tryAndPutInMachineSlots(itemstack1, machine);
            }

            if (itemstack1.stackSize == 0) {
                slot.putStack(null);
            } else {
                slot.onSlotChanged();
            }

            if (itemstack1.stackSize == itemstack.stackSize) {
                return null;
            }

            slot.onPickupFromSlot(player, itemstack1);
        }

        return itemstack;
    }

    protected boolean putInPlayerInventory(ItemStack itemStack) {
        return MOInventoryHelper.mergeItemStack((List<Slot>) inventorySlots, itemStack, machine.getSizeInventory(), inventorySlots.size() - machine.getSizeInventory(), true, true);
    }

    protected boolean tryAndPutInMachineSlots(ItemStack itemStack, IInventory inventory) {
        return MOInventoryHelper.mergeItemStack((List<Slot>) inventorySlots, itemStack, 0, inventory.getSizeInventory(), false, true);
    }

    public T getMachine() {
        return machine;
    }

    public float getProgress() {
        return (float) progressScaled / (float) Short.MAX_VALUE;
    }
}
