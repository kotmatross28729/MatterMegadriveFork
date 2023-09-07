package matteroverdrive.items.tools;

import matteroverdrive.Reference;
import matteroverdrive.init.MatterOverdriveItems;
import net.minecraft.item.ItemAxe;

public class TritaniumAxe extends ItemAxe {
    public TritaniumAxe(String name) {
        super(MatterOverdriveItems.toolMaterialTritanium);
        this.setUnlocalizedName(name);
        this.setTextureName(Reference.MOD_ID + ":" + name);
    }
}
