package matteroverdrive.handler;

import cpw.mods.fml.common.event.FMLServerStartedEvent;
import matteroverdrive.MatterOverdrive;
import matteroverdrive.handler.thread.RegisterItemsFromRecipes;
import matteroverdrive.network.packet.client.PacketUpdateMatterRegistry;
import matteroverdrive.util.MOLog;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import org.apache.logging.log4j.Level;

import java.util.List;
import java.util.concurrent.Future;

public class MatterRegistrationHandler {
    public final String registryPath;
    private Future matterCalculationThread;

    public MatterRegistrationHandler(String registryPath) {
        this.registryPath = registryPath;
    }

    public void serverStart(FMLServerStartedEvent event) {
        try {
            if (MatterOverdrive.matterRegistry.needsCalculation(registryPath) && MatterOverdrive.matterRegistry.AUTOMATIC_CALCULATION) {
                try {
                    runCalculationThread();
                } catch (Exception e) {
                    MOLog.log(Level.ERROR, e, "There was a problem calculating Matter from Recipes or Furnaces");
                }
            } else {
                try {
                    MatterOverdrive.matterRegistry.loadFromFile(registryPath);
                } catch (Exception e) {
                    MOLog.log(Level.ERROR, e, "There was a problem loading the Matter Registry file.");
                    if (MatterOverdrive.matterRegistry.AUTOMATIC_CALCULATION) {
                        MOLog.log(Level.INFO, e, "Starting automatic matter calculation thread.");
                        runCalculationThread();
                    } else {
                        MOLog.log(Level.INFO, e, "Automatic matter calculation disabled. To enable go to Matter Megadrive configs");
                    }
                }
            }
        } catch (Exception e) {
            MOLog.log(Level.ERROR, e, "There was a problem while trying to load Matter Registry or trying to Calculate it");
        }
    }

    public void runCalculationThread() {
        if (matterCalculationThread != null) {
            MOLog.log(Level.INFO, "Old calculation thread is running. Stopping old calculation thread");
            matterCalculationThread.cancel(true);
            matterCalculationThread = null;
        }
        matterCalculationThread = MatterOverdrive.threadPool.submit(new RegisterItemsFromRecipes(registryPath));
    }

    public void onRegistrationComplete() {
        PacketUpdateMatterRegistry updateMatterRegistry = new PacketUpdateMatterRegistry(MatterOverdrive.matterRegistry.getEntries());
        for (EntityPlayerMP playerMP : (List<EntityPlayerMP>) MinecraftServer.getServer().getEntityWorld().playerEntities) {
            MatterOverdrive.packetPipeline.sendTo(updateMatterRegistry, playerMP);
        }
    }
}
