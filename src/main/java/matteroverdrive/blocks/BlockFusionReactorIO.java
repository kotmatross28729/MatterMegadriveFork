package matteroverdrive.blocks;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import matteroverdrive.blocks.includes.MOBlockMachine;
import matteroverdrive.init.MatterOverdriveIcons;
import matteroverdrive.tile.TileEntityFusionReactorPart;
import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;

public class BlockFusionReactorIO extends MOBlockMachine {
    public BlockFusionReactorIO(Material material, String name) {
        super(material, name);
        setHardness(30.0F);
        this.setResistance(10.0f);
        this.setHarvestLevel("pickaxe", 2);
    }

    @Override
    public TileEntity createNewTileEntity(World world, int mea) {
        return new TileEntityFusionReactorPart();
    }

    @SideOnly(Side.CLIENT)
    public IIcon getIcon(int side, int meta) {
        return MatterOverdriveIcons.Network_port_square;
    }
}
