package matteroverdrive.container;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import matteroverdrive.machines.fusionReactorController.TileEntityMachineFusionReactorController;
import matteroverdrive.util.MOContainerHelper;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.ICrafting;

public class ContainerFusionReactor extends ContainerMachine<TileEntityMachineFusionReactorController> {
    protected int energyPerTick;

    public ContainerFusionReactor(InventoryPlayer inventory, TileEntityMachineFusionReactorController machine) {
        super(inventory, machine);
    }

    @Override
    public void init(InventoryPlayer inventory) {
        addAllSlotsFromInventory(machine.getInventoryContainer());
        MOContainerHelper.AddPlayerSlots(inventory, this, 45, 89, false, true);
    }

    @Override
    public void addCraftingToCrafters(ICrafting icrafting) {
        super.addCraftingToCrafters(icrafting);
        icrafting.sendProgressBarUpdate(this, 0, this.machine.getEnergyPerTick());
    }

    @Override
    public void detectAndSendChanges() {
        super.detectAndSendChanges();
        for (Object crafter : this.crafters) {
            ICrafting icrafting = (ICrafting) crafter;

            if (this.energyPerTick != this.machine.getEnergyPerTick()) {
                icrafting.sendProgressBarUpdate(this, 0, this.machine.getEnergyPerTick());
            }
        }

        this.energyPerTick = this.machine.getEnergyPerTick();
    }

    @SideOnly(Side.CLIENT)
    public void updateProgressBar(int slot, int newValue) {
        if (slot == 0)
            energyPerTick = newValue;
    }

    public int getEnergyPerTick() {
        return energyPerTick;
    }
}
