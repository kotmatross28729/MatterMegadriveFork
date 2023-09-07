package matteroverdrive.matter_network.components;

import matteroverdrive.Reference;
import matteroverdrive.api.network.IMatterNetworkClient;
import matteroverdrive.api.network.MatterNetworkTask;
import matteroverdrive.api.network.MatterNetworkTaskState;
import matteroverdrive.data.ItemPattern;
import matteroverdrive.matter_network.MatterNetworkPacket;
import matteroverdrive.matter_network.packets.MatterNetworkRequestPacket;
import matteroverdrive.matter_network.packets.MatterNetworkTaskPacket;
import matteroverdrive.matter_network.tasks.MatterNetworkTaskStorePattern;
import matteroverdrive.tile.TileEntityMachinePatternStorage;
import matteroverdrive.util.MatterDatabaseHelper;
import matteroverdrive.util.MatterNetworkHelper;
import matteroverdrive.util.TimeTracker;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public class MatterNetworkComponentPatternStorage extends MatterNetworkComponentClient<TileEntityMachinePatternStorage> {
    private TimeTracker taskProcessingTracker;

    public MatterNetworkComponentPatternStorage(TileEntityMachinePatternStorage patternStorage) {
        super(patternStorage);
        taskProcessingTracker = new TimeTracker();
        handlers.add(BASIC_CONNECTIONS_HANDLER);
    }

    @Override
    protected void executePacket(MatterNetworkPacket packet) {
        super.executePacket(packet);

        if (packet instanceof MatterNetworkTaskPacket) {
            MatterNetworkTask task = ((MatterNetworkTaskPacket) packet).getTask(getWorldObj());
            if (task != null) {
                executeTasks((MatterNetworkTaskPacket) packet, task);
            }
        } else if (packet instanceof MatterNetworkRequestPacket) {
            executeRequests((MatterNetworkRequestPacket) packet);
        }
    }


    protected void executeTasks(MatterNetworkTaskPacket packet, MatterNetworkTask task) {
        if (taskProcessingTracker.hasDelayPassed(rootClient.getWorldObj(), TileEntityMachinePatternStorage.TASK_PROCESS_DELAY) && task.getState().belowOrEqual(MatterNetworkTaskState.QUEUED)) {
            if (task instanceof MatterNetworkTaskStorePattern) {
                if (rootClient.addItem(((MatterNetworkTaskStorePattern) task).getItemStack(), ((MatterNetworkTaskStorePattern) task).getProgress(), false, null)) {
                    //if the task is finished and the item is in the database
                    task.setState(MatterNetworkTaskState.FINISHED);
                } else {
                    //if the item could not be added to the database for some reason, and has passed the canProcess check
                    //then reset the task and set it to waiting
                    //task.setState(MatterNetworkTaskState.WAITING);
                }
            }
        }
    }

    protected void executeRequests(MatterNetworkRequestPacket packet) {
        if (packet.getRequestType() == Reference.PACKET_REQUEST_PATTERN_SEARCH) {
            if (packet.getRequest() instanceof ItemPattern) {
                ItemPattern packPattern = (ItemPattern) packet.getRequest();
                ItemPattern storagePattern = rootClient.getPattern(packPattern);
                if (storagePattern != null && packet.getSender(rootClient.getWorldObj()) instanceof IMatterNetworkClient) {
                    NBTTagCompound patternNBT = new NBTTagCompound();
                    storagePattern.writeToNBT(patternNBT);
                    MatterNetworkHelper.respondToRequest(getWorldObj(), rootClient, packet, Reference.PACKET_RESPONCE_VALID, patternNBT);
                }
            }
        } else if (packet.getRequestType() == Reference.PACKET_REQUEST_VALID_PATTERN_DESTINATION) {
            if (packet.getRequest() instanceof ItemPattern) {
                ItemPattern packetPattern = (ItemPattern) packet.getRequest();
                ItemPattern storagePattern = rootClient.getPattern(packetPattern);
                NBTTagCompound packetPatternNBT = new NBTTagCompound();
                packetPattern.writeToNBT(packetPatternNBT);
                if (storagePattern != null) {
                    //return not valid when there is already a pattern in storage with full progress
                    MatterNetworkHelper.respondToRequest(getWorldObj(), rootClient, packet, storagePattern.getProgress() >= MatterDatabaseHelper.MAX_ITEM_PROGRESS ? Reference.PACKET_RESPONCE_INVALID : Reference.PACKET_RESPONCE_VALID, packetPatternNBT);
                } else {
                    ItemStack itemStack = packetPattern.toItemStack(false);
                    boolean canAddItem = rootClient.addItem(itemStack, 20, true, null);
                    if (canAddItem) {

                        MatterNetworkHelper.respondToRequest(getWorldObj(), rootClient, packet, Reference.PACKET_RESPONCE_VALID, packetPatternNBT);
                    }
                }
            }
        }
    }

    @Override
    public boolean canPreform(MatterNetworkPacket packet) {
        return super.canPreform(packet) && rootClient.getRedstoneActive();
    }
}
