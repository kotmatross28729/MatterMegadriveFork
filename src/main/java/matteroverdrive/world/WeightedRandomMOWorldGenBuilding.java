package matteroverdrive.world;

import net.minecraft.util.WeightedRandom;
import net.minecraft.world.World;

import java.util.Random;

public class WeightedRandomMOWorldGenBuilding extends WeightedRandom.Item {
    public final MOWorldGenBuilding worldGenBuilding;

    public WeightedRandomMOWorldGenBuilding(MOWorldGenBuilding worldGenBuilding, int weight) {
        super(weight);
        this.worldGenBuilding = worldGenBuilding;
    }

    public int getWeight(Random random, World world, int x, int y, int z) {
        return worldGenBuilding.shouldGenerate(random, world, x, y, z) && worldGenBuilding.isLocationValid(world, x, y, z) ? itemWeight : Math.max(1, (int) (itemWeight * 0.1));
    }
}
