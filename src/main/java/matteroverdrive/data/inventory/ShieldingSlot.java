package matteroverdrive.data.inventory;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import matteroverdrive.client.render.HoloIcon;
import matteroverdrive.init.MatterOverdriveItems;
import matteroverdrive.proxy.ClientProxy;
import net.minecraft.item.ItemStack;

public class ShieldingSlot extends Slot {
    public ShieldingSlot(boolean isMainSlot) {
        super(isMainSlot);
    }

    @Override
    public boolean isValidForSlot(ItemStack itemStack) {
        if (this.getItem() == null || this.getItem().stackSize < 4) {
            if (itemStack != null && itemStack.getItem() != null) {
                return itemStack.getItem() == MatterOverdriveItems.tritanium_plate;
            }
        }
        return false;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public HoloIcon getHoloIcon() {
        return ClientProxy.holoIcons.getIcon("shielding");
    }

    @Override
    public int getMaxStackSize() {
        return 5;
    }

    @Override
    public boolean keepOnDismantle() {
        return true;
    }

    @Override
    public String getUnlocalizedTooltip() {
        return "gui.tooltip.slot.shielding";
    }
}
