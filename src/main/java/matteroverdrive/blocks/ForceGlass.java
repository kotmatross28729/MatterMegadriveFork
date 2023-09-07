package matteroverdrive.blocks;

import cofh.api.block.IDismantleable;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import crazypants.enderio.conduit.IConduitBundle;
import matteroverdrive.Reference;
import matteroverdrive.util.MOInventoryHelper;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import java.util.ArrayList;

public class ForceGlass extends BlockCT implements IDismantleable {
    public ForceGlass(Material material, String name) {
        super(material, name);
        setHardness(40);
        setRotationType(-1);
    }

    @Override
    public boolean canConnect(IBlockAccess world, Block block, int x, int y, int z) {
        boolean eio = false;
        eio = checkEIO(world, block, x, y, z);
        return block instanceof ForceGlass || eio;
    }

    //	Check if the block is an EIO conduit facade painted with Tritanium Glass
    private boolean checkEIO(IBlockAccess world, Block block, int x, int y, int z) {
        if (Reference.eioLoaded()) {
            TileEntity te = world.getTileEntity(x, y, z);
            return te instanceof IConduitBundle && ((IConduitBundle) te).getFacadeId() instanceof ForceGlass;
        }
        return false;
    }

    @Override
    public boolean isSideCT(IBlockAccess world, int x, int y, int z, int side) {
        return true;
    }

    @Override
    public boolean renderAsNormalBlock() {
        return false;
    }

    @Override
    public boolean isOpaqueCube() {
        return false;
    }

    @SideOnly(Side.CLIENT)
    public boolean shouldSideBeRendered(IBlockAccess world, int x, int y, int z, int side) {
        Block block = world.getBlock(x, y, z);
        return !(block instanceof ForceGlass || checkEIO(world, block, x, y, z));
    }

    @Override
    public ArrayList<ItemStack> dismantleBlock(EntityPlayer player, World world, int x, int y, int z, boolean returnDrops) {
        Block block = world.getBlock(x, y, z);
        int l = world.getBlockMetadata(x, y, z);
        boolean flag = block.removedByPlayer(world, player, x, y, z, true);
        ItemStack blockItem = new ItemStack(getItemDropped(l, world.rand, 1));

        if (!returnDrops) {
            dropBlockAsItem(world, x, y, z, blockItem);
        } else {
            MOInventoryHelper.insertItemStackIntoInventory(player.inventory, blockItem, 0);
        }

        ArrayList<ItemStack> list = new ArrayList<>();
        list.add(blockItem);
        return list;
    }

    @Override
    public boolean canDismantle(EntityPlayer entityPlayer, World world, int x, int y, int z) {
        return true;
    }
}
