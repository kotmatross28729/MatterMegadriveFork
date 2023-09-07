package matteroverdrive.blocks.ores;

import matteroverdrive.blocks.includes.MOBlock;
import matteroverdrive.init.MatterOverdriveItems;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.item.Item;
import net.minecraft.util.MathHelper;
import net.minecraft.world.IBlockAccess;

import java.util.Random;

public class DilithiumOre extends MOBlock {

    public DilithiumOre(String name) {
        super(Material.rock, name);
        this.setHardness(4.0f);
        this.setHarvestLevel("pickaxe", 3);
        this.setResistance(5.0f);
        this.setStepSound(Block.soundTypeStone);
    }

    public Item getItemDropped(int p_149650_1_, Random p_149650_2_, int p_149650_3_) {
        return MatterOverdriveItems.dilithium_crystal;
    }

    public int quantityDroppedWithBonus(int fortune, Random random) {
        if (fortune > 0 && Item.getItemFromBlock(this) != this.getItemDropped(0, random, fortune)) {
            int j = random.nextInt(fortune) - 1;

            if (j < 0) {
                j = 0;
            }

            return this.quantityDropped(random) * (j + 1);
        } else {
            return this.quantityDropped(random);
        }
    }

    private Random rand = new Random();

    @Override
    public int getExpDrop(IBlockAccess world, int p_149690_5_, int p_149690_7_) {
        if (this.getItemDropped(p_149690_5_, rand, p_149690_7_) != Item.getItemFromBlock(this)) {
            return MathHelper.getRandomIntegerInRange(rand, 2, 5);
        }
        return 0;
    }
}
