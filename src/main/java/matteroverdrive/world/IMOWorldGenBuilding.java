package matteroverdrive.world;

import net.minecraft.world.World;
import net.minecraft.world.chunk.IChunkProvider;

import java.util.Random;

public interface IMOWorldGenBuilding {
    String getName();

    void generate(Random random, int x, int y, int z, World world, IChunkProvider chunkGenerator, IChunkProvider chunkProvider, int layer, int placeNotify);
}
