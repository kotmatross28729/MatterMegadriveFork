package matteroverdrive.tile;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import matteroverdrive.MatterOverdrive;
import matteroverdrive.api.IMOTileEntity;
import matteroverdrive.machines.MachineNBTCategory;
import matteroverdrive.network.packet.server.PacketSendMachineNBT;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

import java.util.EnumSet;

public abstract class MOTileEntity extends TileEntity implements IMOTileEntity {
    private boolean isAwake = false;

    public MOTileEntity() {
        super();
    }

    public MOTileEntity(World world, int meta) {
        super();
    }

    protected void updateBlock() {
        if (worldObj != null) {
            worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
        }
    }

    @Override
    public void updateEntity() {
        if (!isAwake) {
            onAwake(worldObj.isRemote ? Side.CLIENT : Side.SERVER);
            isAwake = true;
        }
    }

    @Override
    public void readFromNBT(NBTTagCompound nbt) {
        super.readFromNBT(nbt);
        readCustomNBT(nbt, MachineNBTCategory.ALL_OPTS);
    }

    @Override
    public void writeToNBT(NBTTagCompound nbt) {
        super.writeToNBT(nbt);
        writeCustomNBT(nbt, MachineNBTCategory.ALL_OPTS, true);
    }

    public abstract void writeCustomNBT(NBTTagCompound nbt, EnumSet<MachineNBTCategory> categories, boolean toDisk);

    public abstract void readCustomNBT(NBTTagCompound nbt, EnumSet<MachineNBTCategory> categories);

    @SideOnly(Side.CLIENT)
    public void sendNBTToServer(EnumSet<MachineNBTCategory> categories, boolean forceUpdate) {
        if (worldObj.isRemote) {
            MatterOverdrive.packetPipeline.sendToServer(new PacketSendMachineNBT(categories, this, forceUpdate, true));
        }
    }

    protected abstract void onAwake(Side side);
}
