package matteroverdrive.matter_network.components;

import cpw.mods.fml.common.gameevent.TickEvent;
import matteroverdrive.MatterOverdrive;
import matteroverdrive.matter_network.MatterNetworkPacket;
import matteroverdrive.network.packet.client.PacketSendQueueFlash;
import matteroverdrive.tile.TileEntityMachinePacketQueue;
import matteroverdrive.util.MatterNetworkHelper;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public class MatterNetworkComponentQueue extends MatterNetworkComponentClient<TileEntityMachinePacketQueue> {

    public static int[] directions = {0, 1, 2, 3, 4, 5};

    public MatterNetworkComponentQueue(TileEntityMachinePacketQueue queue) {
        super(queue);
    }

    @Override
    public boolean canPreform(MatterNetworkPacket packet) {
        return rootClient.getRedstoneActive();
    }

    @Override
    public void queuePacket(MatterNetworkPacket packet, ForgeDirection from) {
        if (canPreform(packet) && packet.isValid(getWorldObj())) {
            if (getPacketQueue(0).queue(packet)) {
                packet.addToPath(rootClient, from);
                packet.tickAlive(getWorldObj(), true);
                MatterOverdrive.packetPipeline.sendToAllAround(new PacketSendQueueFlash(rootClient), rootClient, 32);
            }
        }
    }

    @Override
    protected void executePacket(MatterNetworkPacket packet) {

    }

    protected int handlePacketBroadcast(World world, MatterNetworkPacket packet) {
        int broadcastCount = 0;
        for (int i = 0; i < directions.length; i++) {
            if (MatterNetworkHelper.broadcastPacketInDirection(world, packet, rootClient, ForgeDirection.getOrientation(directions[i]))) {
                broadcastCount++;
            }
        }
        return broadcastCount;
    }

    @Override
    public int onNetworkTick(World world, TickEvent.Phase phase) {
        int broadcastCount = 0;
        if (phase == TickEvent.Phase.END) {
            for (int i = 0; i < getPacketQueueCount(); i++) {
                getPacketQueue(i).tickAllAlive(world, true);

                MatterNetworkPacket packet = getPacketQueue(i).dequeue();
                if (packet != null) {
                    if (packet.isValid(world)) {

                        broadcastCount += handlePacketBroadcast(world, packet);
                    }
                }
            }
        }
        return broadcastCount;
    }
}
