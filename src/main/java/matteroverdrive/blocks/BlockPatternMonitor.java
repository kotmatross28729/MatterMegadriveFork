package matteroverdrive.blocks;

import matteroverdrive.tile.TileEntityMachinePatternMonitor;
import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class BlockPatternMonitor extends BlockMonitor {
    public BlockPatternMonitor(Material material, String name) {
        super(material, name);
        setHasGui(true);
    }

    @Override
    public TileEntity createNewTileEntity(World world, int meta) {
        return new TileEntityMachinePatternMonitor();
    }
}
