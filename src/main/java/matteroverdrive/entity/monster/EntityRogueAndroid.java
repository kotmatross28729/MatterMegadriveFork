package matteroverdrive.entity.monster;

import matteroverdrive.MatterOverdrive;
import matteroverdrive.handler.ConfigurationHandler;
import matteroverdrive.util.IConfigSubscriber;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraftforge.common.config.Property;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class EntityRogueAndroid implements IConfigSubscriber {

    private static HashSet<String> biomesBlacklist = new HashSet<>();
    private static HashSet<String> biomesWhitelist = new HashSet<>();
    public static HashSet<Integer> dimensionBlacklist = new HashSet<>();
    public static HashSet<Integer> dimensionWhitelist = new HashSet<>();
    private static List<BiomeGenBase.SpawnListEntry> spawnListEntries = new ArrayList<>();

    // Rogue Android options
    public static int MAX_ANDROIDS_PER_CHUNK = 4;
    public static boolean ANDROID_NAMES = true;
    public static boolean RENDER_ANDROID_LABEL = true;
    public static boolean UNLIMITED_WEAPON_ENERGY = true;


    public static void addAsBiomeGen(Class<? extends EntityLiving> entityClass) {
        spawnListEntries.add(new BiomeGenBase.SpawnListEntry(entityClass, 15, 1, 2));
        addInBiome(BiomeGenBase.getBiomeGenArray());
    }

    private static void addInBiome(BiomeGenBase[] biomes) {
        loadBiomeBlacklist(MatterOverdrive.configHandler);
        loadBiomesWhitelist(MatterOverdrive.configHandler);

        for (BiomeGenBase biome : biomes) {
            if (biome != null) {
                List spawnList = biome.getSpawnableList(EnumCreatureType.monster);
                for (BiomeGenBase.SpawnListEntry entry : spawnListEntries) {
                    if (isBiomeValid(biome) && !spawnList.contains(entry) && entry.itemWeight > 0) {
                        spawnList.add(entry);
                    }
                }
            }
        }
    }

    private static boolean isBiomeValid(BiomeGenBase biome) {
        if (biome != null) {
            if (biomesWhitelist.size() > 0) {
                return biomesWhitelist.contains(biome.biomeName.toLowerCase());
            } else {
                return !biomesBlacklist.contains(biome.biomeName.toLowerCase());
            }
        }
        return false;
    }

    @Override
    public void onConfigChanged(ConfigurationHandler config) {
        for (BiomeGenBase.SpawnListEntry entry : spawnListEntries) {
            entry.itemWeight = config.config.getInt("spawn_chance", ConfigurationHandler.CATEGORY_ENTITIES + ".rogue_android", 15, 0, 100, "The spawn change of the Rogue Android");
        }

        loadDimensionBlacklist(config);
        loadDimensionWhitelist(config);
        loadBiomeBlacklist(config);
        loadBiomesWhitelist(config);

        MAX_ANDROIDS_PER_CHUNK = config.getInt("max_android_per_chunk", ConfigurationHandler.CATEGORY_ENTITIES + ".rogue_android", 4, "The max amount of Rogue Android that can spawn in a given chunk");
        ANDROID_NAMES = config.getBool("android_names", ConfigurationHandler.CATEGORY_ENTITIES + ".rogue_android", true, "Whether Rogue Androids should have names");
        RENDER_ANDROID_LABEL = config.getBool("render_android_label", ConfigurationHandler.CATEGORY_ENTITIES + ".rogue_android", true, "Whether to render the name label above a Rogue Android (without a team)");
        UNLIMITED_WEAPON_ENERGY = config.getBool("unlimited_weapon_energy", ConfigurationHandler.CATEGORY_ENTITIES + ".rogue_android", true, "Do Ranged Rogue Androids have unlimited weapon energy in their weapons");
    }

    private static void loadBiomeBlacklist(ConfigurationHandler config) {
        biomesBlacklist.clear();
        String[] blacklist = config.config.getStringList("biome.blacklist", ConfigurationHandler.CATEGORY_ENTITIES + ".rogue_android", new String[]{"Hell", "Sky", "MushroomIsland", "MushroomIslandShore"}, "Rogue Android biome blacklist");
        for (String blacklistItem : blacklist) {
            biomesBlacklist.add(blacklistItem.toLowerCase());
        }
    }

    private static void loadBiomesWhitelist(ConfigurationHandler configurationHandler) {
        biomesWhitelist.clear();
        String[] whitelist = configurationHandler.config.getStringList("biome.whitelist", ConfigurationHandler.CATEGORY_ENTITIES + "." + "rogue_android", new String[0], "Rogue Android biome whitelist");
        for (String whitelistItem : whitelist) {
            biomesBlacklist.add(whitelistItem.toLowerCase());
        }
    }

    private static void loadDimensionBlacklist(ConfigurationHandler configurationHandler) {
        dimensionBlacklist.clear();
        Property blacklistProp = configurationHandler.config.get(ConfigurationHandler.CATEGORY_ENTITIES + "." + "rogue_android", "dimension.blacklist", new int[]{1});
        blacklistProp.comment = "Rogue Android Dimension ID blacklist";
        int[] blacklist = blacklistProp.getIntList();
        for (int blacklistItem : blacklist) {
            dimensionBlacklist.add(blacklistItem);
        }
    }

    private static void loadDimensionWhitelist(ConfigurationHandler configurationHandler) {
        dimensionWhitelist.clear();
        Property whitelistProp = configurationHandler.config.get(ConfigurationHandler.CATEGORY_ENTITIES + ".rogue_android", "dimension.whitelist", new int[0]);
        whitelistProp.comment = "Rogue Android Dimension ID whitelist";
        int[] whitelist = whitelistProp.getIntList();

        for (int item : whitelist) {
            dimensionWhitelist.add(item);
        }
    }
}
