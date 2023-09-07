package matteroverdrive.network.packet.server;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import io.netty.buffer.ByteBuf;
import matteroverdrive.MatterOverdrive;
import matteroverdrive.api.dialog.IDialogMessage;
import matteroverdrive.api.dialog.IDialogNpc;
import matteroverdrive.network.packet.PacketAbstract;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;

public class PacketConversationInteract extends PacketAbstract {
    int npcID;
    int dialogMessageID;
    int optionId;

    public PacketConversationInteract() {
    }

    public PacketConversationInteract(IDialogNpc npc, IDialogMessage dialogMessage, int optionId) {
        this.npcID = npc.getEntity().getEntityId();
        this.dialogMessageID = MatterOverdrive.dialogRegistry.getMessageId(dialogMessage);
        this.optionId = optionId;
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        npcID = buf.readInt();
        dialogMessageID = buf.readInt();
        optionId = buf.readInt();
    }

    @Override
    public void toBytes(ByteBuf buf) {
        buf.writeInt(npcID);
        buf.writeInt(dialogMessageID);
        buf.writeInt(optionId);
    }

    public static class ServerHandler extends AbstractServerPacketHandler<PacketConversationInteract> {
        @Override
        public IMessage handleServerMessage(EntityPlayer player, PacketConversationInteract message, MessageContext ctx) {
            Entity npcEntity = player.worldObj.getEntityByID(message.npcID);
            if (npcEntity instanceof IDialogNpc) {
                IDialogMessage m;
                if (message.dialogMessageID >= 0) {
                    m = MatterOverdrive.dialogRegistry.getMessage(message.dialogMessageID);
                } else {
                    m = ((IDialogNpc) npcEntity).getStartDialogMessage(player);
                }
                if (m != null) {
                    m.onOptionsInteract((IDialogNpc) npcEntity, player, message.optionId);
                }
            }
            return null;
        }
    }
}
