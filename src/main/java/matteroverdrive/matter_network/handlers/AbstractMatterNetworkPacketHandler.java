package matteroverdrive.matter_network.handlers;

import matteroverdrive.api.network.IMatterNetworkConnection;
import matteroverdrive.matter_network.MatterNetworkPacket;
import net.minecraft.world.World;

public abstract class AbstractMatterNetworkPacketHandler {
    public abstract void processPacket(MatterNetworkPacket packet, Context context);

    public static class Context {
        public final World world;
        public final IMatterNetworkConnection connection;

        public Context(World world, IMatterNetworkConnection connection) {
            this.world = world;
            this.connection = connection;
        }
    }
}
