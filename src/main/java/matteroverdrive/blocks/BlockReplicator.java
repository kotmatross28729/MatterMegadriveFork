package matteroverdrive.blocks;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import matteroverdrive.blocks.includes.MOMatterEnergyStorageBlock;
import matteroverdrive.handler.ConfigurationHandler;
import matteroverdrive.init.MatterOverdriveIcons;
import matteroverdrive.tile.TileEntityMachineReplicator;
import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public class BlockReplicator extends MOMatterEnergyStorageBlock {
    public float replication_volume;
    public boolean hasVentParticles;

    public BlockReplicator(Material material, String name) {
        super(material, name, true, true);
        setHardness(20.0F);
        this.setResistance(9.0f);
        this.setHarvestLevel("pickaxe", 2);
        setHasGui(true);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public IIcon getIcon(IBlockAccess world, int x, int y, int z, int side) {
        return MatterOverdriveIcons.Base;
    }

    @Override
    public boolean isOpaqueCube() {
        return false;
    }

    @Override
    public boolean canPlaceTorchOnTop(World world, int x, int y, int z) {
        return true;
    }

    public boolean isSideSolid(IBlockAccess world, int x, int y, int z, ForgeDirection side) {
        return true;
    }

    @Override
    public TileEntity createNewTileEntity(World world, int meta) {
        return new TileEntityMachineReplicator();
    }


    /**
     * The type of render function that is called for this block
     */
    @Override
    public int getRenderType() {
        return -1;
    }

    @Override
    public void onConfigChanged(ConfigurationHandler config) {
        super.onConfigChanged(config);
        replication_volume = (float) config.getMachineDouble(getUnlocalizedName(), "volume.replicate", 1, "The volume of the replication animation");
        hasVentParticles = config.getMachineBool(getUnlocalizedName(), "particles.vent", true, "Should vent particles be displayed");
        TileEntityMachineReplicator.MATTER_STORAGE = config.getMachineInt(getUnlocalizedName(), "storage.matter", 1024, "How much matter can the replicator hold");
        TileEntityMachineReplicator.ENERGY_STORAGE = config.getMachineInt(getUnlocalizedName(), "storage.energy", 512000, "How much energy can the replicator hold");
        TileEntityMachineReplicator.REPLICATE_ENERGY_PER_MATTER = config.getMachineInt(getUnlocalizedName(), "cost.replication.energy", 16000, "The total replication cost of each matter value. The energy cost is calculated like so: (matterAmount*EnergyCost)");
        TileEntityMachineReplicator.REPLICATE_SPEED_PER_MATTER = config.getMachineInt(getUnlocalizedName(), "speed.replication", 120, "The replication speed in ticks per matter value");
    }
}
