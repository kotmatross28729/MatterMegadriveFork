package matteroverdrive.items;

import matteroverdrive.client.data.Color;
import matteroverdrive.data.BlockPos;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.world.World;
import net.minecraftforge.common.util.Constants;

import java.util.List;

public class TransportFlashDrive extends FlashDrive {
    public TransportFlashDrive(String name, Color color) {
        super(name, color);
    }

    public void addDetails(ItemStack itemstack, EntityPlayer player, List infos) {
        super.addDetails(itemstack, player, infos);
        if (hasTarget(itemstack)) {
            Block block = player.worldObj.getBlock(getTargetX(itemstack), getTargetY(itemstack), getTargetZ(itemstack));
            infos.add(EnumChatFormatting.YELLOW + String.format("[%s,%s,%s] %s", getTargetX(itemstack), getTargetY(itemstack), getTargetZ(itemstack), block != Blocks.air ? block.getLocalizedName() : "Unknown"));
        }
    }

    @Override
    public boolean onItemUse(ItemStack itemStack, EntityPlayer entityPlayer, World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ) {
        if (world.getBlock(x, y, z) != null && world.getBlock(x, y, z) != Blocks.air) {
            setTarget(itemStack, x, y, z);
            return true;
        }
        removeTarget(itemStack);
        return false;
    }

    public void setTarget(ItemStack itemStack, int x, int y, int z) {
        if (!itemStack.hasTagCompound())
            itemStack.setTagCompound(new NBTTagCompound());

        itemStack.getTagCompound().setInteger("TargetX", x);
        itemStack.getTagCompound().setInteger("TargetY", y);
        itemStack.getTagCompound().setInteger("TargetZ", z);
    }

    public int getTargetDistance(ItemStack itemStack) {
        if (itemStack.hasTagCompound()) {
            return (int) Math.sqrt(getTargetX(itemStack) * getTargetX(itemStack) + getTargetY(itemStack) * getTargetY(itemStack) + getTargetZ(itemStack) * getTargetZ(itemStack));
        }
        return 0;
    }

    public void removeTarget(ItemStack itemStack) {
        if (itemStack.hasTagCompound()) {
            itemStack.setTagCompound(null);
        }
    }

    public int getTargetX(ItemStack itemStack) {
        if (itemStack.hasTagCompound()) {
            return itemStack.getTagCompound().getInteger("TargetX");
        }
        return 0;
    }

    public int getTargetY(ItemStack itemStack) {
        if (itemStack.hasTagCompound()) {
            return itemStack.getTagCompound().getInteger("TargetY");
        }
        return 0;
    }

    public int getTargetZ(ItemStack itemStack) {
        if (itemStack.hasTagCompound()) {
            return itemStack.getTagCompound().getInteger("TargetZ");
        }
        return 0;
    }

    public BlockPos getTarget(ItemStack itemStack) {
        if (hasTarget(itemStack))
            return new BlockPos(getTargetX(itemStack), getTargetY(itemStack), getTargetZ(itemStack));
        return null;
    }

    public boolean hasTarget(ItemStack itemStack) {
        return itemStack.hasTagCompound() && itemStack.getTagCompound().hasKey("TargetX", Constants.NBT.TAG_INT) && itemStack.getTagCompound().hasKey("TargetY", Constants.NBT.TAG_INT) && itemStack.getTagCompound().hasKey("TargetZ", Constants.NBT.TAG_INT);
    }
}
