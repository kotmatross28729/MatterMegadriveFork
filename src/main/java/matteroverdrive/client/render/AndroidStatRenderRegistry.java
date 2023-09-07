package matteroverdrive.client.render;

import matteroverdrive.api.android.IAndroidStatRenderRegistry;
import matteroverdrive.api.android.IBionicStat;
import matteroverdrive.api.events.MOEventRegisterAndroidStatRenderer;
import matteroverdrive.api.renderer.IBioticStatRenderer;
import net.minecraftforge.common.MinecraftForge;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class AndroidStatRenderRegistry implements IAndroidStatRenderRegistry {
    Map<Class<? extends IBionicStat>, Collection<IBioticStatRenderer>> map;

    public AndroidStatRenderRegistry() {
        map = new HashMap<>();
    }

    @Override
    public Collection<IBioticStatRenderer> getRendererCollection(Class<? extends IBionicStat> stat) {
        return map.get(stat);
    }

    @Override
    public Collection<IBioticStatRenderer> removeAllRenderersFor(Class<? extends IBionicStat> stat) {
        return map.remove(stat);
    }

    @Override
    public boolean registerRenderer(Class<? extends IBionicStat> stat, IBioticStatRenderer renderer) {
        if (!MinecraftForge.EVENT_BUS.post(new MOEventRegisterAndroidStatRenderer(stat, renderer))) {
            Collection<IBioticStatRenderer> collection = map.get(stat);
            if (collection == null) {
                collection = new ArrayList<>();
                map.put(stat, collection);
            }
            return collection.add(renderer);
        }
        return false;
    }
}
