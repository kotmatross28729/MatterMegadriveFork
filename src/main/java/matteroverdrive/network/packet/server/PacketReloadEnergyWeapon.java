package matteroverdrive.network.packet.server;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import io.netty.buffer.ByteBuf;
import matteroverdrive.items.weapon.EnergyWeapon;
import matteroverdrive.network.packet.PacketAbstract;
import net.minecraft.entity.player.EntityPlayer;

public class PacketReloadEnergyWeapon extends PacketAbstract {
    public PacketReloadEnergyWeapon() {
    }

    @Override
    public void fromBytes(ByteBuf buf) {

    }

    @Override
    public void toBytes(ByteBuf buf) {

    }

    public static class ServerHandler extends AbstractServerPacketHandler<PacketReloadEnergyWeapon> {
        @Override
        public IMessage handleServerMessage(EntityPlayer player, PacketReloadEnergyWeapon message, MessageContext ctx) {
            if (player.getHeldItem() != null && player.getHeldItem().getItem() instanceof EnergyWeapon) {
                if (((EnergyWeapon) player.getHeldItem().getItem()).needsRecharge(player.getHeldItem())) {
                    ((EnergyWeapon) player.getHeldItem().getItem()).chargeFromEnergyPack(player.getHeldItem(), player);
                }
            }
            return null;
        }
    }
}
