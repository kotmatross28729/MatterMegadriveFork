package matteroverdrive.blocks;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import matteroverdrive.blocks.includes.MOBlockMachine;
import matteroverdrive.handler.ConfigurationHandler;
import matteroverdrive.init.MatterOverdriveIcons;
import matteroverdrive.tile.TileEntityMachinePatternStorage;
import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;

import static matteroverdrive.util.MOBlockHelper.getOppositeSide;

public class BlockPatternStorage extends MOBlockMachine {
    public boolean hasVentParticles;

    public BlockPatternStorage(Material material, String name) {
        super(material, name);
        setHardness(20.0F);
        this.setResistance(9.0f);
        this.setHarvestLevel("pickaxe", 2);
        setHasGui(true);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public IIcon getIcon(int side, int metadata) {
        if (side == getOppositeSide(metadata)) {
            return MatterOverdriveIcons.Vent;
        }

        return MatterOverdriveIcons.Base;
    }

    @Override
    public TileEntity createNewTileEntity(World world, int meta) {
        return new TileEntityMachinePatternStorage();
    }

    @Override
    public int getRenderType() {
        return -1;
    }

    @Override
    public boolean isOpaqueCube() {
        return false;
    }

    public void onConfigChanged(ConfigurationHandler config) {
        super.onConfigChanged(config);
        hasVentParticles = config.getMachineBool(getUnlocalizedName(), "particles.vent", true, "Should vent particles be displayed");
        TileEntityMachinePatternStorage.ENERGY_CAPACITY = config.getMachineInt(getUnlocalizedName(), "storage.energy", 64000, String.format("How much energy can the %s hold", getLocalizedName()));
        TileEntityMachinePatternStorage.ENERGY_TRANSFER = config.getMachineInt(getUnlocalizedName(), "transfer.energy", 128, String.format("The Transfer speed of the %s", getLocalizedName()));
    }
}
