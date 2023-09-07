package matteroverdrive.blocks;

import matteroverdrive.tile.pipes.TileEntityMatterPipe;
import matteroverdrive.util.MOBlockHelper;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class BlockMatterPipe extends BlockPipe {
    public BlockMatterPipe(Material material, String name) {
        super(material, name);
        setHardness(10.0F);
        this.setResistance(5.0f);
        setRotationType(MOBlockHelper.RotationType.PREVENT);
    }

    @Override
    public TileEntity createNewTileEntity(World world, int meta) {
        return new TileEntityMatterPipe();
    }

    @Override
    public void onNeighborBlockChange(World p_149695_1_, int p_149695_2_, int p_149695_3_, int p_149695_4_, Block p_149695_5_) {
        super.onNeighborBlockChange(p_149695_1_, p_149695_2_, p_149695_3_, p_149695_4_, p_149695_5_);
    }
}
