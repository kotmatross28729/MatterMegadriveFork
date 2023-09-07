package matteroverdrive.network.packet.client;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import io.netty.buffer.ByteBuf;
import matteroverdrive.MatterOverdrive;
import matteroverdrive.handler.MatterEntry;
import matteroverdrive.network.packet.PacketAbstract;
import net.minecraft.entity.player.EntityPlayer;

import java.util.HashMap;
import java.util.Map;

public class PacketUpdateMatterRegistry extends PacketAbstract {
    private static Map<String, MatterEntry> entries;

    public PacketUpdateMatterRegistry() {
        super();
    }

    public PacketUpdateMatterRegistry(Map<String, MatterEntry> entries) {
        super();
        PacketUpdateMatterRegistry.entries = entries;
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        String key;
        int keySize;
        char[] keyChars;
        int size = buf.readInt();
        entries = new HashMap<>();

        for (int i = 0; i < size; i++) {
            keySize = buf.readInt();
            keyChars = new char[keySize];
            for (int c = 0; c < keySize; c++) {
                keyChars[c] = buf.readChar();
            }
            key = String.copyValueOf(keyChars);
            entries.put(key, new MatterEntry(key, buf.readInt(), buf.readByte()));
        }
    }

    @Override
    public void toBytes(ByteBuf buf) {
        int size = entries.size();
        buf.writeInt(size);

        for (Map.Entry<String, MatterEntry> entry : entries.entrySet()) {
            int keySize = entry.getKey().length();
            buf.writeInt(keySize);
            char[] keyChars = entry.getKey().toCharArray();
            for (int c = 0; c < keySize; c++) {
                buf.writeChar(keyChars[c]);
            }
            buf.writeInt(entry.getValue().getMatter());
            buf.writeByte(entry.getValue().getType());
        }
    }

    public static class ClientHandler extends AbstractClientPacketHandler<PacketUpdateMatterRegistry> {

        @Override
        public IMessage handleClientMessage(EntityPlayer player, PacketUpdateMatterRegistry message, MessageContext ctx) {
            MatterOverdrive.matterRegistry.setEntries(PacketUpdateMatterRegistry.entries);
            MatterOverdrive.matterRegistry.hasComplitedRegistration = true;
            return null;
        }
    }
}
