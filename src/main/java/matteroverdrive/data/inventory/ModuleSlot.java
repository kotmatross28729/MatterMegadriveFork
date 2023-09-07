package matteroverdrive.data.inventory;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import matteroverdrive.Reference;
import matteroverdrive.api.weapon.IWeapon;
import matteroverdrive.api.weapon.IWeaponModule;
import matteroverdrive.client.render.HoloIcon;
import matteroverdrive.proxy.ClientProxy;
import matteroverdrive.util.MOEnergyHelper;
import matteroverdrive.util.WeaponHelper;
import net.minecraft.item.ItemStack;

public class ModuleSlot extends Slot {
    int type;
    WeaponSlot weaponSlot;

    public ModuleSlot(boolean isMainSlot, int type, WeaponSlot weaponSlot) {
        super(isMainSlot);
        this.type = type;
        this.weaponSlot = weaponSlot;
    }

    @Override
    public boolean isValidForSlot(ItemStack item) {
        switch (type) {
            case Reference.MODULE_BATTERY:
                return MOEnergyHelper.isEnergyContainerItem(item) && !WeaponHelper.isWeapon(item);
            default:
                if (WeaponHelper.isWeaponModule(item)) {
                    if (((IWeaponModule) item.getItem()).getSlot(item) == type) {
                        if (weaponSlot != null && weaponSlot.getItem() != null && weaponSlot.getItem().getItem() instanceof IWeapon) {
                            return ((IWeapon) weaponSlot.getItem().getItem()).supportsModule(weaponSlot.getItem(), item);
                        }
                        return true;
                    }
                }
                return false;
        }
    }

    @Override
    @SideOnly(Side.CLIENT)
    public HoloIcon getHoloIcon() {
        switch (type) {
            case Reference.MODULE_BATTERY:
                return ClientProxy.holoIcons.getIcon("battery");
            case Reference.MODULE_COLOR:
                return ClientProxy.holoIcons.getIcon("color");
            case Reference.MODULE_BARREL:
                return ClientProxy.holoIcons.getIcon("barrel");
            case Reference.MODULE_SIGHTS:
                return ClientProxy.holoIcons.getIcon("sights");
            default:
                return ClientProxy.holoIcons.getIcon("module");
        }
    }

    @Override
    public int getMaxStackSize() {
        return 1;
    }
}
