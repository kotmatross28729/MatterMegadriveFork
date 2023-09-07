package matteroverdrive.api.matter;

import net.minecraft.item.ItemStack;

/**
 * Used by items that can be recycled at a Recycler.
 */
public interface IRecyclable {
    /**
     * @param from the original stack being recycled.
     * @return the recycled output stack.
     */
    ItemStack getOutput(ItemStack from);

    /**
     * This is manly used to calculated power and speed requirements.
     *
     * @param stack the stack being recycled.
     * @return the amount of matter the recycled output has.
     */
    int getRecycleMatter(ItemStack stack);

    /**
     * @param stack the recycled item stack.
     * @return can be recycled.
     */
    boolean canRecycle(ItemStack stack);
}
