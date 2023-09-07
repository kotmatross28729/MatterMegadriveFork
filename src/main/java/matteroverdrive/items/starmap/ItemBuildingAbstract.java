package matteroverdrive.items.starmap;

import matteroverdrive.MatterOverdrive;
import matteroverdrive.api.starmap.IBuilding;
import matteroverdrive.init.MatterOverdriveItems;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

import java.util.List;

public abstract class ItemBuildingAbstract extends ItemBuildableAbstract implements IBuilding {

    public ItemBuildingAbstract(String name) {
        super(name);
        setMaxStackSize(1);
        setCreativeTab(MatterOverdrive.tabMatterOverdrive_buildings);
    }

    public void addDetails(ItemStack itemstack, EntityPlayer player, List infos) {
        super.addDetails(itemstack, player, infos);
    }

    public boolean hasDetails(ItemStack stack) {
        return true;
    }
}
