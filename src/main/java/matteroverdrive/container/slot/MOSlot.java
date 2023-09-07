package matteroverdrive.container.slot;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import matteroverdrive.client.render.HoloIcon;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class MOSlot extends Slot {
    boolean isVisible = true;
    protected String unlocalizedTooltip;

    @SideOnly(Side.CLIENT)
    public boolean func_111238_b() {
        return isVisible;
    }

    public MOSlot(IInventory inventory, int slot, int x, int y) {
        super(inventory, slot, x, y);
    }

    /**
     * Check if the stack is a valid item for this slot. Always true beside for the armor slots.
     */
    @Override
    public boolean isItemValid(ItemStack itemStack) {
        return isValid(itemStack);
    }

    public boolean isValid(ItemStack itemStack) {
        return true;
    }

    public void setVisible(boolean visible) {
        this.isVisible = visible;
    }

    @SideOnly(Side.CLIENT)
    public HoloIcon getHoloIcon() {
        return null;
    }

    public String getUnlocalizedTooltip() {
        return unlocalizedTooltip;
    }
}
