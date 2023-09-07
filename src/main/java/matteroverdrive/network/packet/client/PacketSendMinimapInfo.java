package matteroverdrive.network.packet.client;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import io.netty.buffer.ByteBuf;
import matteroverdrive.data.MinimapEntityInfo;
import matteroverdrive.entity.player.AndroidPlayer;
import matteroverdrive.network.packet.PacketAbstract;
import net.minecraft.entity.player.EntityPlayer;

import java.util.ArrayList;
import java.util.List;

public class PacketSendMinimapInfo extends PacketAbstract {
    List<MinimapEntityInfo> entityInfos;

    public PacketSendMinimapInfo() {

    }

    public PacketSendMinimapInfo(List<MinimapEntityInfo> entityInfos) {
        this.entityInfos = entityInfos;
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        entityInfos = new ArrayList<>();
        int size = buf.readInt();
        for (int i = 0; i < size; i++) {
            entityInfos.add(new MinimapEntityInfo().readFromBuffer(buf));
        }
    }

    @Override
    public void toBytes(ByteBuf buf) {
        buf.writeInt(entityInfos.size());
        for (MinimapEntityInfo entityInfo : entityInfos) {
            entityInfo.writeToBuffer(buf);
        }
    }

    public static class ClientHandler extends AbstractClientPacketHandler<PacketSendMinimapInfo> {

        @Override
        public IMessage handleClientMessage(EntityPlayer player, PacketSendMinimapInfo message, MessageContext ctx) {
            AndroidPlayer androidPlayer = AndroidPlayer.get(player);
            if (androidPlayer != null && androidPlayer.isAndroid()) {
                AndroidPlayer.setMinimapEntityInfo(message.entityInfos);
            }
            return null;
        }
    }
}
