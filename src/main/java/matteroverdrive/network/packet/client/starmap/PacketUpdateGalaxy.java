package matteroverdrive.network.packet.client.starmap;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import io.netty.buffer.ByteBuf;
import matteroverdrive.MatterOverdrive;
import matteroverdrive.network.packet.PacketAbstract;
import matteroverdrive.network.packet.client.AbstractClientPacketHandler;
import matteroverdrive.starmap.GalaxyClient;
import matteroverdrive.starmap.data.Galaxy;
import matteroverdrive.util.MOLog;
import net.minecraft.entity.player.EntityPlayer;

public class PacketUpdateGalaxy extends PacketAbstract {

    Galaxy galaxy;

    public PacketUpdateGalaxy() {

    }

    public PacketUpdateGalaxy(Galaxy galaxy) {
        this.galaxy = galaxy;
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        galaxy = new Galaxy();
        galaxy.readFromBuffer(buf);
    }

    @Override
    public void toBytes(ByteBuf buf) {
        try {
            galaxy.writeToBuffer(buf);
        } catch (Exception e) {
            MOLog.fatal("There was a problem writing the galaxy to buffer when sending to player", e);
        }
    }

    public static class ClientHandler extends AbstractClientPacketHandler<PacketUpdateGalaxy> {

        @Override
        public IMessage handleClientMessage(EntityPlayer player, PacketUpdateGalaxy message, MessageContext ctx) {
            message.galaxy.setWorld(player.worldObj);
            GalaxyClient.getInstance().setTheGalaxy(message.galaxy);
            GalaxyClient.getInstance().loadClaimedPlanets();
            return null;
        }
    }
}
