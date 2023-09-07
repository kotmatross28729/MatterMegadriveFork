package matteroverdrive.items.tools;

import matteroverdrive.Reference;
import matteroverdrive.init.MatterOverdriveItems;
import net.minecraft.item.ItemPickaxe;

public class TritaniumPickaxe extends ItemPickaxe {
    public TritaniumPickaxe(String name) {
        super(MatterOverdriveItems.toolMaterialTritanium);
        setUnlocalizedName(name);
        setTextureName(Reference.MOD_ID + ":" + name);
    }
}
