package matteroverdrive.init;

import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import matteroverdrive.fluids.FluidMatterPlasma;
import matteroverdrive.fluids.FluidMoltenTritanium;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidContainerRegistry;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;

public class MatterOverdriveFluids {
    public static FluidMatterPlasma matterPlasma;
    public static FluidMoltenTritanium moltenTritanium;

    public static void init(FMLPreInitializationEvent event) {
        matterPlasma = new FluidMatterPlasma("matter_plasma");
        matterPlasma.setViscosity(8000);
        matterPlasma.setLuminosity(15);
        FluidRegistry.registerFluid(matterPlasma);

        moltenTritanium = new FluidMoltenTritanium("molten_tritanium");
        moltenTritanium.setViscosity(6000);
        moltenTritanium.setLuminosity(15);
        moltenTritanium.setTemperature(2000);
        FluidRegistry.registerFluid(moltenTritanium);
    }

    public static void register(FMLInitializationEvent event) {
        FluidContainerRegistry.registerFluidContainer(new FluidStack(matterPlasma, 32), new ItemStack(MatterOverdriveItems.matterContainerFull), new ItemStack(MatterOverdriveItems.matterContainer));
    }
}
