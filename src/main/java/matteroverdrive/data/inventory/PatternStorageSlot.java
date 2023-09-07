package matteroverdrive.data.inventory;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import matteroverdrive.api.matter.IMatterPatternStorage;
import matteroverdrive.client.render.HoloIcon;
import matteroverdrive.proxy.ClientProxy;
import net.minecraft.item.ItemStack;

public class PatternStorageSlot extends Slot {
    public PatternStorageSlot(boolean isMainSlot) {
        super(isMainSlot);
    }

    @Override
    public boolean isValidForSlot(ItemStack item) {
        return item.getItem() instanceof IMatterPatternStorage;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public HoloIcon getHoloIcon() {
        return ClientProxy.holoIcons.getIcon("pattern_storage");
    }

    @Override
    public int getMaxStackSize() {
        return 1;
    }

    @Override
    public String getUnlocalizedTooltip() {
        return "gui.tooltip.slot.pattern_storage";
    }
}
