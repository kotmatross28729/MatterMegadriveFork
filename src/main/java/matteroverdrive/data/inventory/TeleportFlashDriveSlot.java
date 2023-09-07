package matteroverdrive.data.inventory;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import matteroverdrive.client.render.HoloIcon;
import matteroverdrive.init.MatterOverdriveItems;
import matteroverdrive.proxy.ClientProxy;
import net.minecraft.item.ItemStack;

public class TeleportFlashDriveSlot extends Slot {
    public TeleportFlashDriveSlot(boolean isMainSlot) {
        super(isMainSlot);
    }

    public boolean isValidForSlot(ItemStack item) {
        return item != null && item.getItem() == MatterOverdriveItems.transportFlashDrive;
    }

    @SideOnly(Side.CLIENT)
    public HoloIcon getHoloIcon() {
        return ClientProxy.holoIcons.getIcon("flash_drive");
    }

    @Override
    public String getUnlocalizedTooltip() {
        return "item.transport_flash_drive.name";
    }
}
