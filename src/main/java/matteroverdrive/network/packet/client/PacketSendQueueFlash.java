package matteroverdrive.network.packet.client;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import io.netty.buffer.ByteBuf;
import matteroverdrive.network.packet.TileEntityUpdatePacket;
import matteroverdrive.tile.TileEntityMachinePacketQueue;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;

public class PacketSendQueueFlash extends TileEntityUpdatePacket {
    public PacketSendQueueFlash() {
        super();
    }

    public PacketSendQueueFlash(TileEntityMachinePacketQueue tileEntity) {
        super(tileEntity);
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        super.fromBytes(buf);
    }

    @Override
    public void toBytes(ByteBuf buf) {
        super.toBytes(buf);
    }

    public static class ClientHandler extends AbstractClientPacketHandler<PacketSendQueueFlash> {
        @Override
        public IMessage handleClientMessage(EntityPlayer player, PacketSendQueueFlash message, MessageContext ctx) {
            TileEntity tileEntity = message.getTileEntity(player.worldObj);
            if (tileEntity instanceof TileEntityMachinePacketQueue) {
                ((TileEntityMachinePacketQueue) tileEntity).flashTime = 5;
            }
            return null;
        }
    }
}
