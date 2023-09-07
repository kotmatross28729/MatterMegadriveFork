package matteroverdrive.api.inventory;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MovingObjectPosition;

public interface IBlockScanner {
    MovingObjectPosition getScanningPos(ItemStack itemStack, EntityPlayer player);

    boolean destroysBlocks(ItemStack itemStack);
}
