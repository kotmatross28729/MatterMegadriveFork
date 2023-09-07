package matteroverdrive.items;

import matteroverdrive.api.network.IMatterNetworkConnection;
import matteroverdrive.api.network.IMatterNetworkFilter;
import matteroverdrive.client.data.Color;
import matteroverdrive.data.BlockPos;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.common.util.Constants;

import java.util.List;

public class NetworkFlashDrive extends FlashDrive implements IMatterNetworkFilter {

    public NetworkFlashDrive(String name, Color color) {
        super(name, color);
        setMaxStackSize(1);
    }

    public void addDetails(ItemStack itemstack, EntityPlayer player, List infos) {
        super.addDetails(itemstack, player, infos);
        if (itemstack.hasTagCompound()) {
            NBTTagList list = itemstack.getTagCompound().getTagList(IMatterNetworkFilter.CONNECTIONS_TAG, Constants.NBT.TAG_COMPOUND);
            for (int i = 0; i < list.tagCount(); i++) {
                BlockPos pos = new BlockPos(list.getCompoundTagAt(i));
                Block block = pos.getBlock(player.worldObj);
                if (block != null) {
                    infos.add(String.format("[%s,%s,%s] %s", pos.x, pos.y, pos.z, block != Blocks.air ? block.getLocalizedName() : "Unknown"));
                }
            }
        }
    }

    @Override
    public boolean onItemUse(ItemStack itemStack, EntityPlayer entityPlayer, World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ) {
        TileEntity tileEntity = world.getTileEntity(x, y, z);
        if (tileEntity instanceof IMatterNetworkConnection) {
            BlockPos connectionPosition = ((IMatterNetworkConnection) tileEntity).getPosition();
            if (!itemStack.hasTagCompound()) {
                itemStack.setTagCompound(new NBTTagCompound());
            }

            boolean hasPos = false;
            NBTTagList list = itemStack.getTagCompound().getTagList(IMatterNetworkFilter.CONNECTIONS_TAG, Constants.NBT.TAG_COMPOUND);
            for (int i = 0; i < list.tagCount(); i++) {
                BlockPos pos = new BlockPos(list.getCompoundTagAt(i));
                if (pos.equals(connectionPosition)) {
                    hasPos = true;
                    list.removeTag(i);
                    break;
                }
            }

            if (!hasPos) {
                NBTTagCompound posNBT = new NBTTagCompound();
                connectionPosition.writeToNBT(posNBT);
                list.appendTag(posNBT);
            }

            itemStack.getTagCompound().setTag(IMatterNetworkFilter.CONNECTIONS_TAG, list);

            return true;
        }
        return false;
    }

    @Override
    public NBTTagCompound getFilter(ItemStack stack) {
        return stack.getTagCompound();
    }
}
