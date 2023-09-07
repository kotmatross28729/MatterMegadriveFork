package matteroverdrive.container;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import matteroverdrive.tile.TileEntityMachineSolarPanel;
import matteroverdrive.util.MOContainerHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.ICrafting;

public class ContainerSolarPanel extends ContainerMachine<TileEntityMachineSolarPanel> {
    int lastChargeAmount;

    public ContainerSolarPanel(InventoryPlayer inventory, TileEntityMachineSolarPanel machine) {
        super(inventory, machine);
    }

    @Override
    public void init(InventoryPlayer inventory) {
        addAllSlotsFromInventory(machine.getInventoryContainer());
        MOContainerHelper.AddPlayerSlots(inventory, this, 45, 89, true, true);
    }

    public void addCraftingToCrafters(ICrafting icrafting) {
        super.addCraftingToCrafters(icrafting);
        icrafting.sendProgressBarUpdate(this, 1, this.machine.getChargeAmount());
    }

    public void detectAndSendChanges() {
        super.detectAndSendChanges();
        for (Object crafter : this.crafters) {
            ICrafting icrafting = (ICrafting) crafter;

            if (this.lastChargeAmount != this.machine.getChargeAmount()) {
                icrafting.sendProgressBarUpdate(this, 1, this.machine.getChargeAmount());
            }
        }

        this.lastChargeAmount = this.machine.getChargeAmount();
    }

    @SideOnly(Side.CLIENT)
    public void updateProgressBar(int slot, int newValue) {
        super.updateProgressBar(slot, newValue);
        if (slot == 1)
            this.machine.setChargeAmount((byte) newValue);
    }

    @Override
    public boolean canInteractWith(EntityPlayer player) {
        return true;
    }
}
