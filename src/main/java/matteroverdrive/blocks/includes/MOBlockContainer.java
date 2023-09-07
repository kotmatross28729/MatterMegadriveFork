package matteroverdrive.blocks.includes;

import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;

public abstract class MOBlockContainer extends MOBlock implements ITileEntityProvider {

    public MOBlockContainer(Material material, String name) {
        super(material, name);
        this.isBlockContainer = true;
    }

    @Override
    public void register() {
        super.register();
        GameRegistry.registerTileEntity(createNewTileEntity(null, 0).getClass(), this.getUnlocalizedName().substring(5));
    }
}
