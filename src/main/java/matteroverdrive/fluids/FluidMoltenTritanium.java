package matteroverdrive.fluids;

import matteroverdrive.init.MatterOverdriveIcons;
import net.minecraft.util.IIcon;
import net.minecraftforge.fluids.Fluid;

public class FluidMoltenTritanium extends Fluid {
    public FluidMoltenTritanium(String fluidName) {
        super(fluidName);
    }

    @Override
    public IIcon getStillIcon() {
        return MatterOverdriveIcons.molten_tritanium_still;
    }

    @Override
    public IIcon getFlowingIcon() {
        return MatterOverdriveIcons.molten_tritanium_flowing;
    }
}
