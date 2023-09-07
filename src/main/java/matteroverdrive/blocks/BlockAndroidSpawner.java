package matteroverdrive.blocks;

import matteroverdrive.blocks.includes.MOBlockMachine;
import matteroverdrive.tile.TileEntityAndroidSpawner;
import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class BlockAndroidSpawner extends MOBlockMachine {
    public BlockAndroidSpawner(Material material, String name) {
        super(material, name);
        blockHardness = -1;
        setHasGui(true);
    }

    @Override
    public TileEntity createNewTileEntity(World p_149915_1_, int p_149915_2_) {
        return new TileEntityAndroidSpawner();
    }
}
