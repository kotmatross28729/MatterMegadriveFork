package matteroverdrive.handler;

import matteroverdrive.MatterOverdrive;
import matteroverdrive.api.android.IAndroidStatRegistry;
import matteroverdrive.api.android.IBionicStat;
import matteroverdrive.api.events.MOEventRegisterAndroidStat;
import matteroverdrive.client.render.HoloIcons;
import matteroverdrive.util.MOLog;
import net.minecraftforge.common.MinecraftForge;

import java.util.Collection;
import java.util.HashMap;

public class AndroidStatRegistry implements IAndroidStatRegistry {
    private HashMap<String, IBionicStat> stats = new HashMap<>();

    @Override
    public boolean registerStat(IBionicStat stat) {
        if (stats.containsKey(stat.getUnlocalizedName())) {
            MOLog.warn("Stat with the name '%s' is already present!", stat.getUnlocalizedName());
        } else {
            if (!MinecraftForge.EVENT_BUS.post(new MOEventRegisterAndroidStat(stat))) {
                stats.put(stat.getUnlocalizedName(), stat);
                return true;
            }
        }
        return false;
    }

    @Override
    public IBionicStat getStat(String name) {
        return stats.get(name);
    }

    @Override
    public boolean hasStat(String name) {
        return stats.containsKey(name);
    }

    @Override
    public IBionicStat unregisterStat(String statName) {
        return stats.remove(statName);
    }

    public void registerIcons(HoloIcons holoIcons) {
        for (IBionicStat stat : stats.values()) {
            stat.registerIcons(holoIcons);
        }
    }

    public Collection<IBionicStat> getStats() {
        return stats.values();
    }
}
