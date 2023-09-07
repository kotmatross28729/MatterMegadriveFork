package matteroverdrive.matter_network.components;

import cpw.mods.fml.common.gameevent.TickEvent;
import matteroverdrive.MatterOverdrive;
import matteroverdrive.api.network.IMatterNetworkClient;
import matteroverdrive.api.network.IMatterNetworkDispatcher;
import matteroverdrive.api.network.MatterNetworkTask;
import matteroverdrive.machines.MOTileEntityMachine;
import matteroverdrive.matter_network.MatterNetworkTaskQueue;
import matteroverdrive.util.MOLog;
import net.minecraft.world.World;
import org.apache.logging.log4j.Level;

public abstract class MatterNetworkComponentClientDispatcher<K extends MatterNetworkTask, T extends MOTileEntityMachine & IMatterNetworkClient & IMatterNetworkDispatcher> extends MatterNetworkComponentClient<T> implements IMatterNetworkDispatcher<K> {
    private TickEvent.Phase dispatchPhase;

    public MatterNetworkComponentClientDispatcher(T rootClient, TickEvent.Phase dispatchPhase) {
        super(rootClient);
        this.dispatchPhase = dispatchPhase;
    }

    @Override
    public MatterNetworkTaskQueue<K> getTaskQueue(int queueID) {
        return rootClient.getTaskQueue(queueID);
    }

    @Override
    public int onNetworkTick(World world, TickEvent.Phase phase) {
        super.onNetworkTick(world, phase);
        if (phase.equals(dispatchPhase)) {
            for (int i = 0; i < getTaskQueueCount(); i++) {
                if (getTaskQueue(i).peek() != null) {
                    try {
                        return manageTopQueue(world, i, getTaskQueue(i).peek());
                    } catch (Exception e) {
                        MOLog.log(Level.ERROR, e, "Where was a problem while trying to get task from Queue from %s", getClass());
                        try {
                            getTaskQueue(i).dequeue();
                        } catch (Exception e1) {
                            MOLog.log(Level.ERROR, e1, "Could not deque bad task from dispatcher!");
                            getTaskQueue(i).clear();
                        }
                    }
                }
            }
        }
        return 0;
    }

    public abstract int manageTopQueue(World world, int queueID, K element);

    public int getTaskQueueCount() {
        return rootClient.getTaskQueueCount();
    }
}
