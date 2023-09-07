package matteroverdrive.matter_network;

import matteroverdrive.data.BlockPos;
import net.minecraftforge.common.util.ForgeDirection;

public class MatterNetworkPathNode {
    int x;
    int y;
    int z;
    ForgeDirection port;

    public MatterNetworkPathNode(BlockPos position) {
        this(position, ForgeDirection.UNKNOWN);
    }

    public MatterNetworkPathNode(BlockPos position, ForgeDirection port) {
        x = position.x;
        y = position.y;
        z = position.z;
        this.port = port;
    }

    @Override
    public boolean equals(Object obj) {

        if (!(obj instanceof MatterNetworkPathNode)) {
            return false;
        }
        MatterNetworkPathNode bp = (MatterNetworkPathNode) obj;
        return bp.x == x & bp.y == y & bp.z == z;
    }

    @Override
    public int hashCode() {
        return (x & 0xFFF) | (y & 0xFF << 8) | (z & 0xFFF << 12);
    }

    // so compiler will optimize
    public boolean equals(BlockPos bp) {

        return bp != null && bp.x == x & bp.y == y & bp.z == z;
    }
}
