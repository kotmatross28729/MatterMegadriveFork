package matteroverdrive.api.matter;

import net.minecraftforge.common.util.ForgeDirection;

/**
 * @deprecated This is now replaced by Forge Fluid Tanks. As all machines that store matter are Fluid Tanks.
 */
public interface IMatterConnection {
    boolean canConnectFrom(ForgeDirection dir);
}
