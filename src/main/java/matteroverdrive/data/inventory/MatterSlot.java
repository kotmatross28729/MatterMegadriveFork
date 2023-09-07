package matteroverdrive.data.inventory;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import matteroverdrive.client.render.HoloIcon;
import matteroverdrive.proxy.ClientProxy;
import matteroverdrive.util.MatterHelper;
import net.minecraft.item.ItemStack;

public class MatterSlot extends Slot {
    public MatterSlot(boolean isMainSlot) {
        super(isMainSlot);
    }

    @Override
    public boolean isValidForSlot(ItemStack itemStack) {
        return MatterHelper.containsMatter(itemStack);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public HoloIcon getHoloIcon() {
        return ClientProxy.holoIcons.getIcon("decompose");
    }

    @Override
    public String getUnlocalizedTooltip() {
        return "gui.tooltip.slot.matter";
    }
}
