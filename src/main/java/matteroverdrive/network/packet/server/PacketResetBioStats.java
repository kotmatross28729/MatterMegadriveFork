package matteroverdrive.network.packet.server;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import io.netty.buffer.ByteBuf;
import matteroverdrive.entity.player.AndroidPlayer;
import matteroverdrive.network.packet.PacketAbstract;
import net.minecraft.entity.player.EntityPlayer;

public class PacketResetBioStats extends PacketAbstract {
    @Override
    public void fromBytes(ByteBuf buf) {

    }

    @Override
    public void toBytes(ByteBuf buf) {

    }

    public static class ServerHandler extends AbstractServerPacketHandler<PacketResetBioStats> {

        @Override
        public IMessage handleServerMessage(EntityPlayer player, PacketResetBioStats message, MessageContext ctx) {
            AndroidPlayer androidPlayer = AndroidPlayer.get(player);
            if (androidPlayer != null && androidPlayer.isAndroid()) {
                player.addExperienceLevel(androidPlayer.resetUnlocked());
            }
            return null;
        }
    }
}
