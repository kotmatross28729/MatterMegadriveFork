package matteroverdrive.api;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

/**
 * Implemented by all Matter Megadrive Tile Entities
 */
public interface IMOTileEntity {
    void onAdded(World world, int x, int y, int z);

    void onPlaced(World world, EntityLivingBase entityLiving);

    void onDestroyed();

    void onNeighborBlockChange();

    void writeToDropItem(ItemStack itemStack);

    void readFromPlaceItem(ItemStack itemStack);

}
