package matteroverdrive.init;

import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import matteroverdrive.enchantment.EnchantmentOverclock;
import matteroverdrive.handler.ConfigurationHandler;
import matteroverdrive.util.IConfigSubscriber;

public class MatterOverdriveEnchantments implements IConfigSubscriber {
    public static EnchantmentOverclock overclock;

    public static void init(FMLPreInitializationEvent event, ConfigurationHandler configurationHandler) {
        int id = configurationHandler.getInt("Overclock", ConfigurationHandler.CATEGORY_ENCHANTMENTS, 69);
        while (id < 256) {
            try {
                overclock = new EnchantmentOverclock(id);
                break;
            } catch (IllegalArgumentException e) {
                id++;
            }
        }

        configurationHandler.setInt("Overclock", ConfigurationHandler.CATEGORY_ENCHANTMENTS, id);
    }

    @Override
    public void onConfigChanged(ConfigurationHandler config) {

    }
}
