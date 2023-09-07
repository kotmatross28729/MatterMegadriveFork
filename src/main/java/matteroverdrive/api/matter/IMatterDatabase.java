package matteroverdrive.api.matter;

import matteroverdrive.data.ItemPattern;
import net.minecraft.item.ItemStack;

import javax.annotation.Nullable;
import java.util.List;

/**
 * This is used by Machines that store Item Patters. Used by the Replicator.
 */
public interface IMatterDatabase {
    /**
     * Does the Database contain the specified Item Stack pattern
     *
     * @param item The item stack being searched for.
     * @return is the Item Stack's pattern present in the database.
     */
    boolean hasItem(ItemStack item);

    /**
     * @return an list of all the item patterns in the database.
     */
    List<ItemPattern> getPatterns();

    /**
     * @param withInfo should more info be included in the item stack's NBT tag, like the scan progress.
     * @return all item patterns in the database as {@link net.minecraft.item.ItemStack}
     */
    List<ItemStack> getItems(boolean withInfo);

    /**
     * Add an Items Stack to the database as a pattern.
     *
     * @param itemStack     The item stack being added to the database.
     * @param initialAmount the amount of initial progress the patterns has. It ranges from 0 - 100%
     * @param simulate      is this call a simulation.
     *                      Does no actual storing of item.
     *                      Used to check if the database can handle or store a given Item Stack without actually storing it.
     * @param info          the string builder used to store information on the storing process of the item stack's pattern.
     *                      It gives useful information about why the pattern didn't get stored in the database.
     * @return did the item pattern get stored in the database.
     */
    boolean addItem(ItemStack itemStack, int initialAmount, boolean simulate, @Nullable StringBuilder info);

    /**
     * Used to get an item pattern of the specified Item stack.
     *
     * @param item The item stack to search for.
     * @return The pattern of the given Item Stack, {@code null} if the stack wasn't found.
     */
    ItemPattern getPattern(ItemStack item);

    /**
     * Used to get the pattern instance in the storage.
     *
     * @param item The item stack to search for.
     * @return The pattern instance of the given pattern, {@code null} if the pattern wasn't found.
     */
    ItemPattern getPattern(ItemPattern item);

    /**
     * @return a list of Pattern Storages in the database if any.
     */
    ItemStack[] getPatternStorageList();
}
