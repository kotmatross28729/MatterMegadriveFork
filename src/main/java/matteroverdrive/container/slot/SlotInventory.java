package matteroverdrive.container.slot;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import matteroverdrive.client.render.HoloIcon;
import matteroverdrive.data.inventory.Slot;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;

public class SlotInventory extends MOSlot {

    Slot slot;

    public SlotInventory(IInventory inventory, Slot slot, int x, int y) {
        super(inventory, slot.getId(), x, y);
        this.slot = slot;
    }

    @Override
    public boolean isItemValid(ItemStack itemStack) {
        return slot.isValidForSlot(itemStack);
    }

    public int getSlotStackLimit() {
        return slot.getMaxStackSize();
    }

    public Slot getSlot() {
        return slot;
    }

    public String getUnlocalizedTooltip() {
        return slot.getUnlocalizedTooltip();
    }

    @Override
    @SideOnly(Side.CLIENT)
    public HoloIcon getHoloIcon() {
        return slot.getHoloIcon();
    }
}
