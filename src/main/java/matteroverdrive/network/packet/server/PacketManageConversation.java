package matteroverdrive.network.packet.server;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import io.netty.buffer.ByteBuf;
import matteroverdrive.api.dialog.IDialogNpc;
import matteroverdrive.gui.GuiDialog;
import matteroverdrive.network.packet.AbstractBiPacketHandler;
import matteroverdrive.network.packet.PacketAbstract;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;

public class PacketManageConversation extends PacketAbstract {
    public boolean start;
    public int npcID;

    public PacketManageConversation() {

    }

    public PacketManageConversation(IDialogNpc npc, boolean start) {
        npcID = npc.getEntity().getEntityId();
        this.start = start;
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        npcID = buf.readInt();
        start = buf.readBoolean();
    }

    @Override
    public void toBytes(ByteBuf buf) {
        buf.writeInt(npcID);
        buf.writeBoolean(start);
    }

    public static class BiHandler extends AbstractBiPacketHandler<PacketManageConversation> {
        @Override
        @SideOnly(Side.CLIENT)
        public IMessage handleClientMessage(EntityPlayer player, PacketManageConversation message, MessageContext ctx) {
            Entity npcEntity = player.worldObj.getEntityByID(message.npcID);
            if (npcEntity instanceof IDialogNpc) {
                if (message.start) {
                    ((IDialogNpc) npcEntity).onPlayerInteract(player, null);
                    ((IDialogNpc) npcEntity).setDialogPlayer(player);
                    Minecraft.getMinecraft().displayGuiScreen(new GuiDialog((IDialogNpc) npcEntity, player));
                } else {
                    ((IDialogNpc) npcEntity).setDialogPlayer(null);
                }
            }
            return null;
        }

        @Override
        public IMessage handleServerMessage(EntityPlayer player, PacketManageConversation message, MessageContext ctx) {
            Entity npcEntity = player.worldObj.getEntityByID(message.npcID);
            if (npcEntity instanceof IDialogNpc) {
                if (message.start) {
                    if (((IDialogNpc) npcEntity).getDialogPlayer() == null && ((IDialogNpc) npcEntity).canTalkTo(player)) {
                        ((IDialogNpc) npcEntity).setDialogPlayer(player);
                        return message;
                    }
                } else {
                    ((IDialogNpc) npcEntity).setDialogPlayer(null);
                    return message;
                }
            }
            return null;
        }
    }
}
