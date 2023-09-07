package matteroverdrive.tile;

import cpw.mods.fml.common.gameevent.TickEvent;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import matteroverdrive.api.inventory.UpgradeTypes;
import matteroverdrive.api.network.IMatterNetworkClient;
import matteroverdrive.data.BlockPos;
import matteroverdrive.machines.MOTileEntityMachine;
import matteroverdrive.matter_network.MatterNetworkPacket;
import matteroverdrive.matter_network.MatterNetworkPacketQueue;
import matteroverdrive.matter_network.components.MatterNetworkComponentQueue;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public abstract class TileEntityMachinePacketQueue extends MOTileEntityMachine implements IMatterNetworkClient {
    public static int BROADCAST_DELAY = 2;
    public static int TASK_QUEUE_SIZE = 16;
    protected MatterNetworkComponentQueue networkComponent;
    @SideOnly(Side.CLIENT)
    public int flashTime;

    public TileEntityMachinePacketQueue(int upgradeCount) {
        super(upgradeCount);
    }

    protected void registerComponents() {
        super.registerComponents();
        networkComponent = new MatterNetworkComponentQueue(this);
        addComponent(networkComponent);
    }

    @Override
    public void updateEntity() {
        super.updateEntity();
        if (worldObj.isRemote) {
            if (flashTime > 0) {
                flashTime--;
            }
        }
    }

    @Override
    public boolean canConnectFromSide(ForgeDirection side) {
        return true;
    }

    @Override
    public boolean isAffectedByUpgrade(UpgradeTypes type) {
        return type.equals(UpgradeTypes.Speed);
    }


    //region Matter Network
    @Override
    public int onNetworkTick(World world, TickEvent.Phase phase) {
        return networkComponent.onNetworkTick(world, phase);
    }

    @Override
    public boolean canPreform(MatterNetworkPacket packet) {
        return networkComponent.canPreform(packet);
    }

    @Override
    public void queuePacket(MatterNetworkPacket packet, ForgeDirection from) {
        networkComponent.queuePacket(packet, from);
    }

    @Override
    public BlockPos getPosition() {
        return new BlockPos(xCoord, yCoord, zCoord);
    }

    @Override
    public MatterNetworkPacketQueue getPacketQueue(int queueID) {
        return networkComponent.getPacketQueue(queueID);
    }
    //endregion

    //region Events
    @Override
    public void onAdded(World world, int x, int y, int z) {

    }

    @Override
    public void onPlaced(World world, EntityLivingBase entityLiving) {

    }

    @Override
    public void onDestroyed() {

    }

    @Override
    protected void onAwake(Side side) {

    }
    //endregion

    //region Getters and Setters
    @Override
    public String getSound() {
        return null;
    }

    @Override
    public boolean hasSound() {
        return false;
    }

    @Override
    public boolean getServerActive() {
        return false;
    }

    @Override
    public float soundVolume() {
        return 0;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public double getMaxRenderDistanceSquared() {
        return 1024.0D;
    }

    @Override
    public int getPacketQueueCount() {
        return networkComponent.getPacketQueueCount();
    }
    //endregion
}
