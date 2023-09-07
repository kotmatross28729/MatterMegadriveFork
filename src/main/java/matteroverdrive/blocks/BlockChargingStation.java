package matteroverdrive.blocks;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import matteroverdrive.blocks.includes.MOBlockMachine;
import matteroverdrive.client.render.block.RendererBlockChargingStation;
import matteroverdrive.data.BlockPos;
import matteroverdrive.handler.ConfigurationHandler;
import matteroverdrive.init.MatterOverdriveIcons;
import matteroverdrive.tile.TileEntityMachineChargingStation;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;

public class BlockChargingStation extends MOBlockMachine {

    public BlockChargingStation(Material material, String name) {
        super(material, name);
        setHardness(20.0F);
        this.setResistance(9.0f);
        this.setHarvestLevel("pickaxe", 2);
        lightValue = 10;
        setHasGui(true);
    }


    //	Multiblock
    @Override
    public boolean canPlaceBlockAt(World world, int x, int y, int z) {
        return world.getBlock(x, y, z).isReplaceable(world, x, y, z) &&
                world.getBlock(x, y + 1, z).isReplaceable(world, x, y + 1, z) &&
                world.getBlock(x, y + 2, z).isReplaceable(world, x, y + 2, z);
    }

    @Override
    public int onBlockPlaced(World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ, int meta) {
        BlockPos ownerPos = new matteroverdrive.data.BlockPos(x, y, z);
        BlockBoundingBox.createBoundingBox(world, new matteroverdrive.data.BlockPos(x, y + 1, z), ownerPos, this);
        BlockBoundingBox.createBoundingBox(world, new BlockPos(x, y + 2, z), ownerPos, this);

        return meta;
    }

    @Override
    public void breakBlock(World world, int x, int y, int z, Block block, int meta) {
        TileEntity te = world.getTileEntity(x, y, z);
        if (te instanceof TileEntityMachineChargingStation) {
            TileEntityMachineChargingStation chargingStation = (TileEntityMachineChargingStation) te;
            chargingStation.getBoundingBlocks().forEach(coord -> {
                world.setBlockToAir(coord.x, coord.y, coord.z);
            });
        }

        super.breakBlock(world, x, y, z, block, meta);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public IIcon getIcon(int side, int metadata) {
        return MatterOverdriveIcons.Base;
    }

    @Override
    public TileEntity createNewTileEntity(World world, int meta) {
        return new TileEntityMachineChargingStation();
    }

    @Override
    public int getRenderType() {
        return RendererBlockChargingStation.renderID;
    }

    @Override
    public boolean isOpaqueCube() {
        return false;
    }

    @Override
    public void onConfigChanged(ConfigurationHandler config) {
        super.onConfigChanged(config);
        TileEntityMachineChargingStation.BASE_MAX_RANGE = config.getInt("charge station range", ConfigurationHandler.CATEGORY_MACHINES, 8, "The range of the Charge Station");
    }

}
