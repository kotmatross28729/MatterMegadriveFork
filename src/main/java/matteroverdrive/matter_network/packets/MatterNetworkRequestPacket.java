package matteroverdrive.matter_network.packets;

import matteroverdrive.api.network.IMatterNetworkConnection;
import matteroverdrive.matter_network.MatterNetworkPacket;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public class MatterNetworkRequestPacket extends MatterNetworkPacket {
    int requestType;
    Object request;

    public MatterNetworkRequestPacket() {
        super();
    }

    public MatterNetworkRequestPacket(IMatterNetworkConnection sender, int requestType, ForgeDirection port, Object request) {
        this(sender, requestType, port, null, request);
    }

    public MatterNetworkRequestPacket(IMatterNetworkConnection sender, int requestType, ForgeDirection port, NBTTagCompound filter, Object request) {
        super(sender.getPosition(), port, filter);
        this.requestType = requestType;
        this.request = request;
    }

    @Override
    public boolean isValid(World world) {
        return true;
    }

    @Override
    public String getName() {
        return "Request Packet";
    }

    public int getRequestType() {
        return requestType;
    }

    public Object getRequest() {
        return request;
    }
}
