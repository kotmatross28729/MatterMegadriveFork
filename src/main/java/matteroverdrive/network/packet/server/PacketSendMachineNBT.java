package matteroverdrive.network.packet.server;

import cpw.mods.fml.common.network.ByteBufUtils;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import io.netty.buffer.ByteBuf;
import matteroverdrive.machines.MOTileEntityMachine;
import matteroverdrive.machines.MachineNBTCategory;
import matteroverdrive.network.packet.AbstractBiPacketHandler;
import matteroverdrive.network.packet.TileEntityUpdatePacket;
import matteroverdrive.tile.MOTileEntity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;

import java.util.EnumSet;

public class PacketSendMachineNBT extends TileEntityUpdatePacket {
    NBTTagCompound data;
    int cattegories;
    boolean forceUpdate;

    public PacketSendMachineNBT() {
    }

    public PacketSendMachineNBT(EnumSet<MachineNBTCategory> categories, MOTileEntity tileEntity, boolean forceUpdate, boolean toDisk) {
        super(tileEntity);
        data = new NBTTagCompound();
        this.forceUpdate = forceUpdate;
        tileEntity.writeCustomNBT(data, categories, toDisk);
        this.cattegories = MachineNBTCategory.encode(categories);
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        super.fromBytes(buf);
        data = ByteBufUtils.readTag(buf);
        cattegories = buf.readInt();
        forceUpdate = buf.readBoolean();
    }

    @Override
    public void toBytes(ByteBuf buf) {
        super.toBytes(buf);
        ByteBufUtils.writeTag(buf, data);
        buf.writeInt(cattegories);
        buf.writeBoolean(forceUpdate);
    }

    public static class BiHandler extends AbstractBiPacketHandler<PacketSendMachineNBT> {
        @Override
        public IMessage handleClientMessage(EntityPlayer player, PacketSendMachineNBT message, MessageContext ctx) {
            TileEntity tileEntity = message.getTileEntity(player.worldObj);
            if (tileEntity instanceof MOTileEntity) {
                ((MOTileEntity) tileEntity).readCustomNBT(message.data, MachineNBTCategory.decode(message.cattegories));
            }
            return null;
        }

        @Override
        public IMessage handleServerMessage(EntityPlayer player, PacketSendMachineNBT message, MessageContext ctx) {
            TileEntity tileEntity = message.getTileEntity(player.worldObj);
            if (tileEntity instanceof MOTileEntity) {
                ((MOTileEntity) tileEntity).readCustomNBT(message.data, MachineNBTCategory.decode(message.cattegories));
                if (message.forceUpdate) {
                    if (tileEntity instanceof MOTileEntityMachine) {
                        ((MOTileEntityMachine) tileEntity).forceSync();
                    } else {
                        player.worldObj.markBlockForUpdate(tileEntity.xCoord, tileEntity.yCoord, tileEntity.zCoord);
                    }
                }
            }
            return null;
        }
    }
}
