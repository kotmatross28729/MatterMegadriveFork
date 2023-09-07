package matteroverdrive.data.inventory;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import matteroverdrive.client.render.HoloIcon;
import matteroverdrive.proxy.ClientProxy;
import matteroverdrive.util.MatterHelper;
import net.minecraft.item.ItemStack;

public class DatabaseSlot extends Slot {
    public DatabaseSlot(boolean isMainSlot) {
        super(isMainSlot);
    }

    @Override
    public boolean isValidForSlot(ItemStack itemStack) {
        return MatterHelper.isMatterScanner(itemStack);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public HoloIcon getHoloIcon() {
        return ClientProxy.holoIcons.getIcon("scanner");
    }

    @Override
    public int getMaxStackSize() {
        return 1;
    }

    @Override
    public String getUnlocalizedTooltip() {
        return "gui.tooltip.slot.database";
    }
}
