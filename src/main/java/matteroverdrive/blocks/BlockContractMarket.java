package matteroverdrive.blocks;

import matteroverdrive.tile.TileEntityMachineContractMarket;
import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class BlockContractMarket extends BlockMonitor {
    public BlockContractMarket(Material material, String name) {
        super(material, name);
        setHasGui(true);
    }

    @Override
    public TileEntity createNewTileEntity(World p_149915_1_, int p_149915_2_) {
        return new TileEntityMachineContractMarket();
    }
}
