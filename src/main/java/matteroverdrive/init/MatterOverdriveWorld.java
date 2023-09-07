package matteroverdrive.init;

import cpw.mods.fml.common.gameevent.TickEvent;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.Side;
import matteroverdrive.handler.ConfigurationHandler;
import matteroverdrive.world.MOWorldGen;

public class MatterOverdriveWorld {
    public MOWorldGen worldGen;

    public MatterOverdriveWorld(ConfigurationHandler configurationHandler) {
        worldGen = new MOWorldGen(configurationHandler);
        configurationHandler.subscribe(worldGen);
    }

    public void onWorldTick(TickEvent.WorldTickEvent event) {
        if (event.side.equals(Side.SERVER)) {
            worldGen.manageBuildingGeneration(event);
        }
    }

    public void register() {
        GameRegistry.registerWorldGenerator(worldGen, 0);
    }
}
