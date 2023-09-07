package matteroverdrive.network.packet.bi;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import io.netty.buffer.ByteBuf;
import matteroverdrive.network.packet.AbstractBiPacketHandler;
import matteroverdrive.network.packet.PacketAbstract;
import matteroverdrive.starmap.GalaxyClient;
import matteroverdrive.starmap.GalaxyServer;
import matteroverdrive.starmap.data.Quadrant;
import matteroverdrive.starmap.data.Star;
import net.minecraft.entity.player.EntityPlayer;

public class PacketStarLoading extends PacketAbstract {
    int quadrantID;
    int starID;
    Star star;

    public PacketStarLoading() {
    }

    public PacketStarLoading(int quadrantID, int starID) {
        this.quadrantID = quadrantID;
        this.starID = starID;
    }

    public PacketStarLoading(int quadrantID, Star star) {
        this.quadrantID = quadrantID;
        this.star = star;
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        quadrantID = buf.readInt();
        starID = buf.readInt();
        star = new Star();
        if (buf.readBoolean()) {
            star = new Star();
            star.readFromBuffer(buf);
        }
    }

    @Override
    public void toBytes(ByteBuf buf) {
        buf.writeInt(quadrantID);
        buf.writeInt(starID);
        buf.writeBoolean(star != null);
        if (star != null) {
            star.writeToBuffer(buf);
        }
    }

    public static class BiHandler extends AbstractBiPacketHandler<PacketStarLoading> {

        @Override
        public IMessage handleClientMessage(EntityPlayer player, PacketStarLoading message, MessageContext ctx) {
            Quadrant quadrant = GalaxyClient.getInstance().getTheGalaxy().getQuadrantMap().get(message.quadrantID);
            if (quadrant != null && message.star != null) {
                quadrant.addStar(message.star);
                message.star.setQuadrant(quadrant);
            }
            return null;
        }

        @Override
        public IMessage handleServerMessage(EntityPlayer player, PacketStarLoading message, MessageContext ctx) {
            Quadrant quadrant = GalaxyServer.getInstance().getTheGalaxy().getQuadrantMap().get(message.quadrantID);
            if (quadrant != null) {
                Star star = quadrant.getStarMap().get(message.starID);
                if (star != null) {
                    return new PacketStarLoading(message.quadrantID, star);
                }
            }
            return null;
        }
    }
}
