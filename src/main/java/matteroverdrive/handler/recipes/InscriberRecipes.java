package matteroverdrive.handler.recipes;

import matteroverdrive.data.recipes.InscriberRecipe;
import net.minecraft.item.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class InscriberRecipes {
    private static List<InscriberRecipe> recipes = new ArrayList<>();

    public static void registerRecipe(InscriberRecipe recipe) {
        recipes.add(recipe);
    }

    public static InscriberRecipe getRecipe(ItemStack main, ItemStack sec) {
        for (InscriberRecipe recipe : recipes) {
            if (recipe.matches(main, sec)) {
                return recipe;
            }
        }
        return null;
    }

    public static boolean containedInRecipe(ItemStack itemStack, boolean main) {
        if (itemStack != null) {
            ItemStack other;
            for (InscriberRecipe recipe : recipes) {
                if (main) {
                    other = recipe.getMain();
                } else {
                    other = recipe.getSec();
                }
                if (other != null) {
                    if (itemStack.getItem() == other.getItem() && itemStack.getItemDamage() == other.getItemDamage()) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public static List<InscriberRecipe> getRecipes() {
        return recipes;
    }
}
