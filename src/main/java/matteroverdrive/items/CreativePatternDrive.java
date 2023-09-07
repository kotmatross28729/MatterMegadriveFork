package matteroverdrive.items;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import matteroverdrive.Reference;
import matteroverdrive.data.ItemPattern;
import matteroverdrive.util.MatterDatabaseHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class CreativePatternDrive extends PatternDrive {

    public CreativePatternDrive(String name, int capacity) {
        super(name, capacity);
    }

    private void loadAllPatterns(ItemStack patternStorage) {

    }

    public ItemStack onItemRightClick(ItemStack itemStack, World world, EntityPlayer entityPlayer) {
        if (!world.isRemote && entityPlayer.isSneaking()) {
            loadAllPatterns(itemStack);
        }
        return itemStack;
    }

    @SideOnly(Side.CLIENT)
    protected String getIconString() {
        return Reference.MOD_ID + ":" + "pattern_drive";
    }

    @Override
    public boolean addItem(ItemStack storage, ItemStack itemStack, int initialAmount, boolean simulate) {
        return false;
    }
}
