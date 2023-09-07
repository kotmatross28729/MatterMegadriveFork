package matteroverdrive.matter_network.packets;

import matteroverdrive.data.BlockPos;
import matteroverdrive.matter_network.MatterNetworkPacket;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public class MatterNetworkBroadcastPacket extends MatterNetworkPacket {
    int broadcastType;

    public MatterNetworkBroadcastPacket() {
        super();
    }

    public MatterNetworkBroadcastPacket(BlockPos position, int broadcastType, ForgeDirection port) {
        super(position, port);
        this.broadcastType = broadcastType;
    }

    @Override
    public boolean isValid(World world) {
        return true;
    }

    @Override
    public String getName() {
        return "Broadcast Packet";
    }

    public int getBroadcastType() {
        return broadcastType;
    }

    public void setBroadcastType(int broadcastType) {
        this.broadcastType = broadcastType;
    }
}
