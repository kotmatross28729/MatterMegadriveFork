package matteroverdrive.blocks.ores;

import matteroverdrive.blocks.includes.MOBlock;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;

public class TritaniumOre extends MOBlock {

    public TritaniumOre(String name) {
        super(Material.rock, name);
        this.setHardness(8f);
        this.setHarvestLevel("pickaxe", 2);
        this.setResistance(5.0f);
        this.setStepSound(Block.soundTypeStone);
    }
}
