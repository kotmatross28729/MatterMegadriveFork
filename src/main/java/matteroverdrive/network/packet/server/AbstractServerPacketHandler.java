package matteroverdrive.network.packet.server;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import matteroverdrive.network.packet.AbstractPacketHandler;
import net.minecraft.entity.player.EntityPlayer;

public abstract class AbstractServerPacketHandler<T extends IMessage> extends AbstractPacketHandler<T> {
    public AbstractServerPacketHandler() {
    }

    public IMessage handleClientMessage(EntityPlayer player, T message, MessageContext ctx) {
        return null;
    }
}
