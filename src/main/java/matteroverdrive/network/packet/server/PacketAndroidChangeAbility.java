package matteroverdrive.network.packet.server;

import cpw.mods.fml.common.network.ByteBufUtils;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import io.netty.buffer.ByteBuf;
import matteroverdrive.MatterOverdrive;
import matteroverdrive.api.android.IBionicStat;
import matteroverdrive.entity.player.AndroidPlayer;
import matteroverdrive.network.packet.PacketAbstract;
import net.minecraft.entity.player.EntityPlayer;

import java.util.EnumSet;

public class PacketAndroidChangeAbility extends PacketAbstract {
    String ability;

    public PacketAndroidChangeAbility() {

    }

    public PacketAndroidChangeAbility(String ability) {
        this.ability = ability;
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        ability = ByteBufUtils.readUTF8String(buf);
    }

    @Override
    public void toBytes(ByteBuf buf) {
        ByteBufUtils.writeUTF8String(buf, ability);
    }

    public static class ServerHandler extends AbstractServerPacketHandler<PacketAndroidChangeAbility> {

        @Override
        public IMessage handleServerMessage(EntityPlayer player, PacketAndroidChangeAbility message, MessageContext ctx) {
            IBionicStat stat = MatterOverdrive.statRegistry.getStat(message.ability);
            if (stat != null) {
                AndroidPlayer androidPlayer = AndroidPlayer.get(player);
                if (androidPlayer.isUnlocked(stat, 0) && stat.showOnWheel(androidPlayer, androidPlayer.getUnlockedLevel(stat))) {
                    androidPlayer.setActiveStat(stat);
                    androidPlayer.sync(EnumSet.of(AndroidPlayer.DataType.STATS));
                }
            }
            return null;
        }
    }
}
