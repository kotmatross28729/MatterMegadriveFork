package mekanism.api.lasers;

import net.minecraftforge.common.util.ForgeDirection;

public interface ILaserReceptor {
    void receiveLaserEnergy(double energy, ForgeDirection side);

    boolean canLasersDig();
}
