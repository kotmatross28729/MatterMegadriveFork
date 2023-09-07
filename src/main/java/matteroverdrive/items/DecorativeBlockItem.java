package matteroverdrive.items;

import matteroverdrive.blocks.BlockDecorative;
import matteroverdrive.util.MOStringHelper;
import net.minecraft.block.Block;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemDye;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MathHelper;

public class DecorativeBlockItem extends ItemBlock {
    public DecorativeBlockItem(Block block) {
        super(block);
        if (block instanceof BlockDecorative) {
            setHasSubtypes(((BlockDecorative) block).canBeRotated());
        }
    }

    @Override
    public String getItemStackDisplayName(ItemStack itemStack) {
        if (((BlockDecorative) field_150939_a).canBeRotated() && itemStack.getItemDamage() > 0) {
            return super.getItemStackDisplayName(itemStack) + " [Rotated]";
        } else if (((BlockDecorative) field_150939_a).isColored()) {
            return MOStringHelper.translateToLocal("color." + ItemDye.field_150923_a[MathHelper.clamp_int(itemStack.getItemDamage(), 0, ItemDye.field_150923_a.length - 1)]) + " " + super.getItemStackDisplayName(itemStack);
        }
        return super.getItemStackDisplayName(itemStack);
    }
}
