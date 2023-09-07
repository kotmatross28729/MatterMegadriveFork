package mekanism.api.reactor;

import mekanism.api.IHeatTransfer;
import mekanism.api.gas.GasTank;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidTank;

public interface IFusionReactor extends IHeatTransfer {
    void addTemperatureFromEnergyInput(double energyAdded);

    void simulate();

    FluidTank getWaterTank();

    FluidTank getSteamTank();

    GasTank getDeuteriumTank();

    GasTank getTritiumTank();

    GasTank getFuelTank();

    double getBufferedEnergy();

    void setBufferedEnergy(double energy);

    double getPlasmaTemp();

    void setPlasmaTemp(double temp);

    double getCaseTemp();

    void setCaseTemp(double temp);

    double getBufferSize();

    void formMultiblock();

    boolean isFormed();

    void setInjectionRate(int rate);

    int getInjectionRate();

    boolean isBurning();

    void setBurning(boolean burn);

    int getMinInjectionRate(boolean active);

    double getMaxPlasmaTemperature(boolean active);

    double getMaxCasingTemperature(boolean active);

    double getIgnitionTemperature(boolean active);

    double getPassiveGeneration(boolean active, boolean current);

    int getSteamPerTick(boolean current);

    void updateTemperatures();

    ItemStack[] getInventory();
}
