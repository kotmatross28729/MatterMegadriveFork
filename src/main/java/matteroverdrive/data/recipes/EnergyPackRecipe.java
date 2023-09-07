package matteroverdrive.data.recipes;

import cofh.api.energy.IEnergyContainerItem;
import matteroverdrive.init.MatterOverdriveItems;
import matteroverdrive.items.Battery;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.ShapelessRecipes;

import java.util.Arrays;
import java.util.List;

public class EnergyPackRecipe extends ShapelessRecipes {
    public EnergyPackRecipe(ItemStack... recipeitems) {
        super(new ItemStack(MatterOverdriveItems.energyPack), Arrays.asList(recipeitems));
        for (ItemStack stack : (List<ItemStack>) recipeItems) {
            if (stack != null && stack.getItem() instanceof Battery) {
                ((Battery) stack.getItem()).setEnergyStored(stack, ((Battery) stack.getItem()).getMaxEnergyStored(stack));
                getRecipeOutput().stackSize = ((Battery) stack.getItem()).getEnergyStored(stack) / MatterOverdriveItems.energyPack.getEnergyAmount(getRecipeOutput());
            }
        }
    }

    @Override
    public ItemStack getCraftingResult(InventoryCrafting inventoryCrafting) {
        ItemStack stack = getRecipeOutput().copy();
        for (int i = 0; i < inventoryCrafting.getSizeInventory(); i++) {
            if (inventoryCrafting.getStackInSlot(i) != null && inventoryCrafting.getStackInSlot(i).getItem() instanceof IEnergyContainerItem) {
                int energyStored = ((IEnergyContainerItem) inventoryCrafting.getStackInSlot(i).getItem()).getEnergyStored(inventoryCrafting.getStackInSlot(i));
                int packEnergy = MatterOverdriveItems.energyPack.getEnergyAmount(inventoryCrafting.getStackInSlot(i));
                if (energyStored > 0) {
                    stack.stackSize = energyStored / packEnergy;
                    return stack;
                }
            }
        }
        return null;
    }
}
