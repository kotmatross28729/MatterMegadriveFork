package matteroverdrive.data.inventory;

import matteroverdrive.handler.recipes.InscriberRecipes;
import net.minecraft.item.ItemStack;

public class InscriberSlot extends Slot {
    boolean isSecSolt;

    public InscriberSlot(boolean isMainSlot, boolean isSecSlot) {
        super(isMainSlot);
        this.isSecSolt = isSecSlot;
    }

    public boolean isValidForSlot(ItemStack item) {
        return InscriberRecipes.containedInRecipe(item, !isSecSolt);
    }

    public int getMaxStackSize() {
        if (isSecSolt)
            return 64;
        else
            return 1;
    }
}
