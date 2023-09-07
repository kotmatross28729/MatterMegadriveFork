package matteroverdrive.world;

import matteroverdrive.MatterOverdrive;
import matteroverdrive.data.world.GenPositionWorldData;
import matteroverdrive.data.world.WorldPosition2D;
import matteroverdrive.util.MOLog;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraft.world.chunk.IChunkProvider;
import org.apache.logging.log4j.Level;

import java.util.Random;

public abstract class MOWorldGenBuilding extends MOImageGen implements IMOWorldGenBuilding {

    int yOffset = -1;
    int maxDistanceToAir = 2;
    String name;
    protected Block[] validSpawnBlocks;

    public MOWorldGenBuilding(String name, ResourceLocation texture, int layerWidth, int layerHeight) {
        super(texture, layerWidth, layerHeight);
        this.name = name;
        validSpawnBlocks = new Block[]{Blocks.stone, Blocks.grass, Blocks.dirt};
    }

    @Override
    public void generate(Random random, int x, int y, int z, World world, IChunkProvider chunkGenerator, IChunkProvider chunkProvider, int layer, int placeNotify) {
        generateFromImage(world, random, x, y + getYOffset(), z, layer, placeNotify);
    }

    public boolean locationIsValidSpawn(World world, int i, int j, int k) {
        int distanceToAir = 0;
        Block check = world.getBlock(i, j, k);

        while (check != Blocks.air) {
            if (distanceToAir > getMaxDistanceToAir()) {
                return false;
            }

            distanceToAir++;
            check = world.getBlock(i, j + distanceToAir, k);
        }

        j += distanceToAir - 1;

        Block block = world.getBlock(i, j, k);
        Block blockAbove = world.getBlock(i, j + 1, k);
        Block blockBelow = world.getBlock(i, j - 1, k);

        for (Block x : getValidSpawnBlocks()) {
            if (blockAbove != Blocks.air) {
                return false;
            }
            if (block == x) {
                return true;
            } else if (block == Blocks.snow && blockBelow == x) {
                return true;
            }
        }

        return false;
    }

    @Override
    public String getName() {
        return name;
    }

    public void setMaxDistanceToAir(int maxDistanceToAir) {
        this.maxDistanceToAir = maxDistanceToAir;
    }

    protected int getMaxDistanceToAir() {
        return maxDistanceToAir;
    }

    protected Block[] getValidSpawnBlocks() {
        return validSpawnBlocks;
    }

    public int getYOffset() {
        return yOffset;
    }

    public void setyOffset(int yOffset) {
        this.yOffset = yOffset;
    }

    protected abstract void onGeneration(Random random, World world, int x, int y, int z);

    protected abstract boolean shouldGenerate(Random random, World world, int x, int y, int z);

    public abstract void onGenerationWorkerCreated(WorldGenBuildingWorker worldGenBuildingWorker);

    protected boolean isLocationValid(World world, int x, int y, int z) {
        return locationIsValidSpawn(world, x, y, z) && locationIsValidSpawn(world, x + layerWidth, y, z) && locationIsValidSpawn(world, x + layerWidth, y, z + layerHeight) && locationIsValidSpawn(world, x, y, z + layerHeight);
    }

    public boolean isFarEnoughFromOthers(World world, int x, int z, int minDistance) {
        GenPositionWorldData worldData = MOWorldGen.getWorldPositionData(world);
        if (worldData != null) {
            return worldData.isFarEnough(getName(), x, z, minDistance);
        }
        return true;
    }

    public WorldGenBuildingWorker createWorker(Random random, int x, int y, int z, World world, IChunkProvider chunkGenerator, IChunkProvider chunkProvider) {
        WorldGenBuildingWorker worldGenBuildingWorker = new WorldGenBuildingWorker(this, random, x, y, z, world, chunkGenerator, chunkProvider);
        onGenerationWorkerCreated(worldGenBuildingWorker);
        GenPositionWorldData data = MOWorldGen.getWorldPositionData(world);
        data.addPosition(getName(), new WorldPosition2D(x + layerWidth / 2, z + layerHeight / 2));
        return worldGenBuildingWorker;
    }

    public static class WorldGenBuildingWorker {
        int currentLayer;
        Random random;
        int x, y, z;
        World world;
        IChunkProvider chunkGenerator;
        IChunkProvider chunkProvider;
        MOWorldGenBuilding worldGenBuilding;
        int placeNotify;

        public WorldGenBuildingWorker() {
        }

        public WorldGenBuildingWorker(MOWorldGenBuilding worldGenBuilding, Random random, int x, int y, int z, World world, IChunkProvider chunkGenerator, IChunkProvider chunkProvider) {
            init(worldGenBuilding, random, x, y, z, world, chunkGenerator, chunkProvider);
        }

        public WorldGenBuildingWorker init(MOWorldGenBuilding worldGenBuilding, Random random, int x, int y, int z, World world, IChunkProvider chunkGenerator, IChunkProvider chunkProvider) {
            this.worldGenBuilding = worldGenBuilding;
            this.worldGenBuilding.manageTextureLoading();
            this.x = x;
            this.y = y;
            this.z = z;
            this.random = random;
            this.world = world;
            this.chunkGenerator = chunkGenerator;
            this.chunkProvider = chunkProvider;
            this.placeNotify = this.worldGenBuilding.placeNotify;
            return this;
        }

        public boolean generate() {
            try {
                if (currentLayer >= worldGenBuilding.getLayerCount()) {
                    worldGenBuilding.onGeneration(random, world, x, y, z);
                    return true;
                } else {
                    worldGenBuilding.generate(random, x, y, z, world, chunkGenerator, chunkProvider, currentLayer, placeNotify);
                    currentLayer++;
                    return false;
                }

            } catch (Exception e) {
                MOLog.log(Level.ERROR, e, "There was a problem while generating layer %s of %s", currentLayer, worldGenBuilding.getName());
            }
            return false;
        }

        public WorldGenBuildingWorker setPlaceNotify(int placeNotify) {
            this.placeNotify = placeNotify;
            return this;
        }
    }
}
