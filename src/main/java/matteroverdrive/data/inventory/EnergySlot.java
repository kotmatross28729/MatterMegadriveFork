package matteroverdrive.data.inventory;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import matteroverdrive.client.render.HoloIcon;
import matteroverdrive.proxy.ClientProxy;
import matteroverdrive.util.MOEnergyHelper;
import net.minecraft.item.ItemStack;

public class EnergySlot extends Slot {
    public EnergySlot(boolean isMainSlot) {
        super(isMainSlot);
    }

    @Override
    public boolean isValidForSlot(ItemStack itemStack) {
        return MOEnergyHelper.isEnergyContainerItem(itemStack);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public HoloIcon getHoloIcon() {
        return ClientProxy.holoIcons.getIcon("energy");
    }

    @Override
    public boolean keepOnDismantle() {
        return true;
    }

    @Override
    public String getUnlocalizedTooltip() {
        return "gui.tooltip.slot.energy";
    }
}
