package matteroverdrive.blocks;

import matteroverdrive.tile.pipes.TileEntityHeavyMatterPipe;
import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class BlockHeavyMatterPipe extends BlockMatterPipe {

    public BlockHeavyMatterPipe(Material material, String name) {
        super(material, name);
    }

    @Override
    public TileEntity createNewTileEntity(World world, int meta) {
        return new TileEntityHeavyMatterPipe();
    }

}
