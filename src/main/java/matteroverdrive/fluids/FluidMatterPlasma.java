package matteroverdrive.fluids;

import matteroverdrive.init.MatterOverdriveIcons;
import net.minecraft.util.IIcon;
import net.minecraftforge.fluids.Fluid;

public class FluidMatterPlasma extends Fluid {
    public FluidMatterPlasma(String fluidName) {
        super(fluidName);
    }

    @Override
    public IIcon getStillIcon() {
        return MatterOverdriveIcons.matter_plasma_still;
    }

    @Override
    public IIcon getFlowingIcon() {
        return MatterOverdriveIcons.matter_plasma_flowing;
    }
}
