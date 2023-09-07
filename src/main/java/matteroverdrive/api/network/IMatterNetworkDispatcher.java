package matteroverdrive.api.network;

import matteroverdrive.matter_network.MatterNetworkTaskQueue;

/**
 * This is used by Machines that can issue tasks (orders) to other Machines on the Matter Network.
 */
public interface IMatterNetworkDispatcher<T extends MatterNetworkTask> extends IMatterNetworkConnection, IMatterNetworkHandler {
    /**
     * Gets the Task queue of the Machine at the given ID.
     *
     * @param queueID the ID of the Queue.
     * @return the task queue at the given ID.
     */
    MatterNetworkTaskQueue<T> getTaskQueue(int queueID);

    /**
     * @return the number of task queues in the machine.
     */
    int getTaskQueueCount();
}
