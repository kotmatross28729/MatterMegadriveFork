package matteroverdrive.items.starmap;

import matteroverdrive.MatterOverdrive;
import matteroverdrive.api.starmap.IShip;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

import java.util.List;

public abstract class ItemShipAbstract extends ItemBuildableAbstract implements IShip {
    public ItemShipAbstract(String name) {
        super(name);
        setMaxStackSize(1);
        setCreativeTab(MatterOverdrive.tabMatterOverdrive_ships);
    }

    public void addDetails(ItemStack itemstack, EntityPlayer player, List infos) {
        super.addDetails(itemstack, player, infos);
    }

    public boolean hasDetails(ItemStack stack) {
        return true;
    }
}
