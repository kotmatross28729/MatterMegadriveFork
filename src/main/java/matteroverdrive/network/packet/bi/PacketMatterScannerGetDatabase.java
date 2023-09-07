package matteroverdrive.network.packet.bi;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import io.netty.buffer.ByteBuf;
import matteroverdrive.api.matter.IMatterDatabase;
import matteroverdrive.data.BlockPos;
import matteroverdrive.data.ItemPattern;
import matteroverdrive.gui.GuiMatterScanner;
import matteroverdrive.network.packet.AbstractBiPacketHandler;
import matteroverdrive.network.packet.TileEntityUpdatePacket;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;

import java.util.List;

public class PacketMatterScannerGetDatabase extends TileEntityUpdatePacket {
    List<ItemPattern> list;

    public PacketMatterScannerGetDatabase() {
        super();
    }

    public PacketMatterScannerGetDatabase(int x, int y, int z) {
        super(x, y, z);
    }

    public PacketMatterScannerGetDatabase(BlockPos position) {
        this(position.x, position.y, position.z);
    }

    public PacketMatterScannerGetDatabase(List<ItemPattern> list) {
        this.list = list;
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        super.fromBytes(buf);
        int size = buf.readInt();
        for (int i = 0; i < size; i++) {
            list.add(new ItemPattern(buf));
        }
    }

    @Override
    public void toBytes(ByteBuf buf) {
        // Only works when not empty?
        // TODO: Investigate NullPointerException & EncoderException here.
        super.toBytes(buf);
        buf.writeInt(list.size());
        for (ItemPattern pattern : list) {
            pattern.writeToBuffer(buf);
        }
    }

    public static class Handler extends AbstractBiPacketHandler<PacketMatterScannerGetDatabase> {

        public Handler() {
        }

        @Override
        public IMessage handleServerMessage(EntityPlayer player, PacketMatterScannerGetDatabase message, MessageContext ctx) {
            TileEntity tileEntity = message.getTileEntity(player.worldObj);
            if (tileEntity instanceof IMatterDatabase) {
                IMatterDatabase database = (IMatterDatabase) tileEntity;
                return new PacketMatterScannerGetDatabase(database.getPatterns());
            }

            return null;
        }

        @Override
        public IMessage handleClientMessage(EntityPlayer player, PacketMatterScannerGetDatabase message, MessageContext ctx) {
            if (Minecraft.getMinecraft().currentScreen instanceof GuiMatterScanner) {
                GuiMatterScanner guiMatterScanner = (GuiMatterScanner) Minecraft.getMinecraft().currentScreen;
                guiMatterScanner.UpdatePatternList(message.list);
            }

            return null;
        }
    }
}
