package matteroverdrive.api.matter;

import matteroverdrive.data.ItemPattern;
import net.minecraft.item.ItemStack;

/**
 * Used by items that store Item Patterns such as the {@link matteroverdrive.items.PatternDrive}
 */
public interface IMatterPatternStorage {
    /**
     * @param storage the storage stack
     * @return a list of all the patterns
     */
    ItemPattern[] getPatterns(ItemStack storage);

    /**
     * Adds an item as a pattern in the given Item Stack storage.
     *
     * @param storage       The storage stack.
     * @param itemStack     The item stack being stored as a patten.
     * @param initialAmount The initial progress amount of the pattern.
     *                      Ranges from 0 - 100%.
     * @param simulate      if the call is a simulation, no pattern is stored in the storage item.
     *                      Used to check if a pattern can be stored without actually storing the pattern.
     * @return was the pattern stored.
     */
    boolean addItem(ItemStack storage, ItemStack itemStack, int initialAmount, boolean simulate);

    /**
     * Gets a pattern for a given Item Stack.
     *
     * @param storage The Item Stack storage.
     * @param item    The item stack being searched for.
     * @return The pattern for a given Item Stack in storage.
     * Returns null if pattern is not present in the storage.
     */
    ItemPattern getPattern(ItemStack storage, ItemStack item);

    /**
     * Gets the capacity of the storage item.
     * How much pattern can it store.
     *
     * @param item The storage stack.
     * @return the pattern capacity of the storage.
     */
    int getCapacity(ItemStack item);
}
