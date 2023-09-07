package matteroverdrive.network.packet.bi;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import io.netty.buffer.ByteBuf;
import matteroverdrive.MatterOverdrive;
import matteroverdrive.network.packet.PacketAbstract;
import matteroverdrive.network.packet.server.AbstractServerPacketHandler;
import net.minecraft.entity.player.EntityPlayer;

public class PacketWeaponTick extends PacketAbstract {

    long timestamp;
    PacketFirePlasmaShot plasmaShot;

    public PacketWeaponTick() {

    }

    public PacketWeaponTick(long timestamp) {
        this.timestamp = timestamp;
    }

    public PacketWeaponTick(long timestamp, PacketFirePlasmaShot plasmaShot) {
        this(timestamp);
        this.plasmaShot = plasmaShot;
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        timestamp = buf.readLong();
        if (buf.readBoolean()) {
            this.plasmaShot = new PacketFirePlasmaShot();
            this.plasmaShot.fromBytes(buf);
        }
    }

    @Override
    public void toBytes(ByteBuf buf) {
        buf.writeLong(timestamp);
        buf.writeBoolean(plasmaShot != null);
        if (plasmaShot != null) {
            plasmaShot.toBytes(buf);
        }
    }

    public static class ServerHandler extends AbstractServerPacketHandler<PacketWeaponTick> {
        @Override
        public IMessage handleServerMessage(EntityPlayer player, PacketWeaponTick message, MessageContext ctx) {
            if (message.plasmaShot != null) {
                MatterOverdrive.proxy.getWeaponHandler().handlePlasmaShotFire(player, message.plasmaShot, message.timestamp);
            }
            MatterOverdrive.proxy.getWeaponHandler().addTimestamp(player, message.timestamp);
            return null;
        }
    }
}
