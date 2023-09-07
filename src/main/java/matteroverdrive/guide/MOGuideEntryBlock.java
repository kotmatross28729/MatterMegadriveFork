package matteroverdrive.guide;

import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;

public class MOGuideEntryBlock extends MOGuideEntry {
    public MOGuideEntryBlock(Block block) {
        super(block.getUnlocalizedName(), (ItemStack[]) null);
        setStackIcons(block);
    }

    public MOGuideEntryBlock(Block blockIcon, String name) {
        super(name, new ItemStack(blockIcon));
    }

    @Override
    public String getDisplayName() {
        return getStackIcons()[0].getDisplayName();
    }
}
