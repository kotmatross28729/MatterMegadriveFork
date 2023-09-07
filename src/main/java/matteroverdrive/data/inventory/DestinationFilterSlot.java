package matteroverdrive.data.inventory;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import matteroverdrive.client.render.HoloIcon;
import matteroverdrive.proxy.ClientProxy;
import net.minecraft.item.ItemStack;

public class DestinationFilterSlot extends Slot {
    public DestinationFilterSlot(boolean isMainSlot) {
        super(isMainSlot);
    }

    @Override
    public boolean isValidForSlot(ItemStack item) {
        return true;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public HoloIcon getHoloIcon() {
        return ClientProxy.holoIcons.getIcon("connections");
    }

    @Override
    public int getMaxStackSize() {
        return 1;
    }

    @Override
    public boolean keepOnDismantle() {
        return true;
    }

    @Override
    public String getUnlocalizedTooltip() {
        return "gui.tooltip.slot.filter";
    }
}
