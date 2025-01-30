package matteroverdrive.core;

import matteroverdrive.handler.ConfigurationHandler;
import net.minecraftforge.common.config.Configuration;

import java.io.File;

public class CFG {
	public static boolean enablehands = false;
	public static boolean enableScreenShake = false;
	public static boolean enableMatterAmountTooltip = true;
	public static boolean enableHoloShader = true;
	public static void loadConfig(File configFile) {
		Configuration config = new Configuration(configFile);
		
		enablehands = config.getBoolean("enable hands", ConfigurationHandler.CATEGORY_CLIENT, false, "Render hands when the player is holding a weapon");
		enableScreenShake = config.getBoolean("enable screen shake", ConfigurationHandler.CATEGORY_CLIENT, false, "Turns on the camera shaking when shooting. Disabled by default due to bugs");
		enableMatterAmountTooltip = config.getBoolean("enable matter amount tooltip", ConfigurationHandler.CATEGORY_CLIENT, true, "Shows the amount of matter in an item when pressing SHIFT");
		enableHoloShader = config.getBoolean("use holo shader", ConfigurationHandler.CATEGORY_CLIENT, true, "Use the custom holo shader for holographic items");

		if(config.hasChanged()) {
			config.save();
		}
	}
	
	
}
