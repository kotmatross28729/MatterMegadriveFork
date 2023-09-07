package mekanism.api;

import net.minecraftforge.common.util.ForgeDirection;

public interface IHeatTransfer {
    /**
     * The value of the zero point of our temperature scale in kelvin
     */
    double AMBIENT_TEMP = 300;

    /**
     * The heat transfer coefficient for air
     */
    double AIR_INVERSE_COEFFICIENT = 10000;

    double getTemp();

    double getInverseConductionCoefficient();

    double getInsulationCoefficient(ForgeDirection side);

    void transferHeatTo(double heat);

    double[] simulateHeat();

    double applyTemperatureChange();

    boolean canConnectHeat(ForgeDirection side);

    IHeatTransfer getAdjacent(ForgeDirection side);
}
