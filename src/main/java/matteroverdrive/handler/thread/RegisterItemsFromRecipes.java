package matteroverdrive.handler.thread;

import cpw.mods.fml.common.registry.GameRegistry;
import matteroverdrive.MatterOverdrive;
import matteroverdrive.handler.MatterEntry;
import matteroverdrive.util.MOLog;
import matteroverdrive.util.MatterHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraft.item.crafting.IRecipe;
import org.apache.logging.log4j.Level;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.TimeUnit;

public class RegisterItemsFromRecipes implements Runnable {

    String savePath;

    public RegisterItemsFromRecipes(String savePath) {
        this.savePath = savePath;
    }

    @Override
    public void run() {

        long startTime = System.nanoTime();
        int startEntriesCount = MatterOverdrive.matterRegistry.getEntries().size();

        if (MatterOverdrive.matterRegistry.CALCULATE_RECIPES) {
            int passesCount = 8;
            MOLog.info("Starting Matter Recipe Calculation !");

            for (int pass = 0; pass < passesCount; pass++) {
                long passStartTime = System.nanoTime();
                int passStartRecipeCount = MatterOverdrive.matterRegistry.getEntries().size();

                List<IRecipe> recipes = new CopyOnWriteArrayList(CraftingManager.getInstance().getRecipeList());

                MOLog.info("Matter Recipe Calculation Started for %s recipes at pass %s, with %s matter entries", recipes.size(), pass + 1, passStartRecipeCount);
                for (IRecipe recipe : recipes) {
                    if (recipe == null || recipe.getRecipeOutput() == null)
                        continue;

                    if (Thread.interrupted())
                        return;

                    try {
                        ItemStack itemStack = recipe.getRecipeOutput();
                        if (itemStack != null && !MatterOverdrive.matterRegistry.blacklisted(itemStack) && !MatterOverdrive.matterRegistry.blacklistedFromMod(itemStack)) {
                            debug("Calculating Recipe for: %s", recipe.getRecipeOutput());
                            MatterEntry entry = MatterOverdrive.matterRegistry.getEntry(itemStack);
                            int matter = 0;

                            if (entry == null) {
                                matter += MatterOverdrive.matterRegistry.getMatterFromRecipe(itemStack, false, 0, true);

                                if (matter > 0) {
                                    MatterEntry e = MatterOverdrive.matterRegistry.register(itemStack, matter);
                                    e.setCalculated(true);
                                } else {
                                    debug("Could not calculate recipe for: %s. Matter from recipe is 0.", recipe.getRecipeOutput());
                                }
                            } else {
                                debug("Entry for: %s is already present", recipe.getRecipeOutput());
                            }
                        } else {
                            debug("% was blacklisted. Skipping matter calculation", recipe.getRecipeOutput());
                        }
                    } catch (Exception e) {
                        if (recipe.getRecipeOutput() != null) {
                            debug("Recipe missing output", e);
                        } else {
                            debug("There was a problem calculating matter from recipe", e);
                        }
                    }
                }

                MOLog.info("Matter Recipe Calculation for pass %s complete. Took %s milliseconds. Registered %s recipes", pass + 1, TimeUnit.NANOSECONDS.toMillis(System.nanoTime() - passStartTime), MatterOverdrive.matterRegistry.getEntries().size() - passStartRecipeCount);
                if (MatterOverdrive.matterRegistry.getEntries().size() - passStartRecipeCount <= 0) {
                    break;
                }
            }

            MOLog.info("Matter Recipe Calculation, Complete ! Took %s Milliseconds. Registered total of %s items", TimeUnit.NANOSECONDS.toMillis(System.nanoTime() - startTime), MatterOverdrive.matterRegistry.getEntries().size() - startEntriesCount);
        }

        if (MatterOverdrive.matterRegistry.CALCULATE_FURNACE) {
            startTime = System.nanoTime();
            startEntriesCount = MatterOverdrive.matterRegistry.getEntries().size();

            MOLog.info("Matter Furnace Calculation Started");
            registerFromFurnace();
            MOLog.info("Matter Furnace Calculation Complete. Took %s Milliseconds. Registered %s entries", TimeUnit.NANOSECONDS.toMillis(System.nanoTime() - startTime), MatterOverdrive.matterRegistry.getEntries().size() - startEntriesCount);
        }

        if (MatterOverdrive.matterRegistry.CALCULATE_FURNACE || MatterOverdrive.matterRegistry.CALCULATE_RECIPES) {
            startTime = System.nanoTime();

            MOLog.info("Saving Registry to Disk");
            try {
                MatterOverdrive.matterRegistry.saveToFile(savePath);
                MOLog.info("Registry saved at: %s. Took %s Milliseconds.", savePath, TimeUnit.NANOSECONDS.toMillis(System.nanoTime() - startTime));
            } catch (IOException e) {
                MOLog.log(Level.ERROR, e, "Could not save registry to: %s", savePath);
            }
        }
        MatterOverdrive.matterRegistry.hasComplitedRegistration = true;
        MatterOverdrive.matterRegistrationHandler.onRegistrationComplete();
    }

    private void registerFromFurnace() {
        Map<ItemStack, ItemStack> smeltingMap = new ConcurrentHashMap<>((Map<ItemStack, ItemStack>) FurnaceRecipes.smelting().getSmeltingList());
        for (Map.Entry<ItemStack, ItemStack> entry : smeltingMap.entrySet()) {
            if (entry.getKey() != null && entry.getValue() != null) {
                int keyMatter = (MatterHelper.getMatterAmountFromItem(entry.getKey()) * entry.getKey().stackSize) / entry.getValue().stackSize;
                int valueMatter = MatterHelper.getMatterAmountFromItem(entry.getValue());
                if (keyMatter > 0 && valueMatter <= 0) {
                    MatterOverdrive.matterRegistry.register(entry.getValue(), keyMatter);
                }
            }
        }
    }

    private boolean tryRegisterFuel(ItemStack stack, float matterPerFuel) {
        int stackMatter = MatterHelper.getMatterAmountFromItem(stack);
        int fuelMatter = Math.round(GameRegistry.getFuelValue(stack) * matterPerFuel);
        if (stackMatter <= 0 && fuelMatter > 0) {
            MatterOverdrive.matterRegistry.register(stack, stackMatter);
            return true;
        }
        return false;
    }

    private void debug(String debug, Exception ex, Object... params) {
        if (MatterOverdrive.matterRegistry.CALCULATION_DEBUG) {
            params = getParams(params);
            MOLog.log(Level.DEBUG, ex, debug, params);
        }
    }

    private void debug(String debug, Object... params) {
        if (MatterOverdrive.matterRegistry.CALCULATION_DEBUG) {
            params = getParams(params);
            MOLog.debug(debug, params);
        }
    }

    private Object[] getParams(Object... params) {
        for (int i = 0; i < params.length; i++) {
            if (params[i] instanceof ItemStack) {
                try {
                    params[i] = ((ItemStack) params[i]).getUnlocalizedName();
                } catch (Exception e) {
                    MOLog.log(Level.ERROR, e, "There was a problem getting the name of item %s", ((ItemStack) params[i]).getItem());
                }
            }
        }
        return params;
    }
}
