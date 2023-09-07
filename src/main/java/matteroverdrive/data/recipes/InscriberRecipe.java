package matteroverdrive.data.recipes;

import net.minecraft.item.ItemStack;

public class InscriberRecipe {
    private final ItemStack main, sec;
    private final ItemStack recipeOutput;
    private final int energy;
    private final int time;

    public InscriberRecipe(ItemStack main, ItemStack sec, ItemStack recipeOutput, int energy, int time) {
        this.main = main;
        this.sec = sec;
        this.recipeOutput = recipeOutput;
        this.energy = energy;
        this.time = time;
    }

    public boolean matches(ItemStack main, ItemStack sec) {
        return this.main.isItemEqual(main) && main.stackSize > 0 && this.sec.isItemEqual(sec) && sec.stackSize > 0;
    }

    public ItemStack getCraftingResult(ItemStack main, ItemStack sec) {
        return recipeOutput.copy();
    }

    public ItemStack getRecipeOutput() {
        return recipeOutput;
    }

    public ItemStack getMain() {
        return main;
    }

    public ItemStack getSec() {
        return sec;
    }

    public int getEnergy() {
        return energy;
    }

    public int getTime() {
        return this.time;
    }
}
