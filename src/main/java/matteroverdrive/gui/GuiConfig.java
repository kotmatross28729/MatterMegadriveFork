package matteroverdrive.gui;

import cpw.mods.fml.client.config.IConfigElement;
import matteroverdrive.MatterOverdrive;
import matteroverdrive.Reference;
import net.minecraft.client.gui.GuiScreen;
import net.minecraftforge.common.config.ConfigCategory;
import net.minecraftforge.common.config.ConfigElement;

import java.util.ArrayList;
import java.util.List;

public class GuiConfig extends cpw.mods.fml.client.config.GuiConfig {

    public GuiConfig(GuiScreen parent) {
        super(parent, getAllGuiCategories(), Reference.MOD_ID, false, false, "Matter Megadrive Configurations");
    }

    public GuiConfig(GuiScreen parent, String category) {
        super(parent, getConfigElements(parent, category), Reference.MOD_ID, false, false, GuiConfig.getAbridgedConfigPath(MatterOverdrive.configHandler.toString()), Reference.MOD_NAME + " Configurations");
    }

    private static List<IConfigElement> getConfigElements(GuiScreen parent, String category) {

        List<IConfigElement> list = new ArrayList<IConfigElement>();
        list.add(new ConfigElement<ConfigCategory>(MatterOverdrive.configHandler.getCategory(category)));
        return list;
    }

    private static List<IConfigElement> getAllGuiCategories() {
        List<IConfigElement> list = new ArrayList<IConfigElement>();
        MatterOverdrive.configHandler.addCategoryToGui(list);
        return list;
    }
}
