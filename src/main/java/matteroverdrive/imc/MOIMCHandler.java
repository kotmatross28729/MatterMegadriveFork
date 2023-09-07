package matteroverdrive.imc;

import cpw.mods.fml.common.event.FMLInterModComms;
import matteroverdrive.MatterOverdrive;
import matteroverdrive.api.IMC;
import matteroverdrive.data.recipes.InscriberRecipe;
import matteroverdrive.handler.recipes.InscriberRecipes;
import matteroverdrive.util.MOLog;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import org.apache.logging.log4j.Level;

public class MOIMCHandler {

    public static void imcCallback(FMLInterModComms.IMCEvent event) {
        event.getMessages().forEach(MOIMCHandler::handleMessage);
    }

    public static void handleMessage(FMLInterModComms.IMCMessage msg) {
        switch (msg.key) {
            case IMC.MATTER_REGISTRY_BLACKLIST:
                handleItemBlacklistRegistration(msg);
                break;
            case IMC.MATTER_REGISTRY_BLACKLIST_MOD:
                MatterOverdrive.matterRegistry.addModToBlacklist(msg.getStringValue());
                break;
            case IMC.INSCRIBER_RECIPE:
                handleInscriberRecipeRegistration(msg);
                break;
            case IMC.MATTER_REGISTER:
                handleMatterRegistration(msg);
                break;
        }
    }

    private static void handleMatterRegistration(FMLInterModComms.IMCMessage msg) {
        if (!msg.isNBTMessage()) {
            MOLog.warn("Invalid message type for Matter Registration. Message needs to be of type NBT");
            return;
        }
        try {
            NBTTagCompound data = msg.getNBTValue();
            if (containsAllTags(data, "Matter")) {
                int matter = data.getInteger("Matter");
                if (data.hasKey("Item")) {
                    ItemStack itemStack = ItemStack.loadItemStackFromNBT(data.getCompoundTag("Item"));
                    MatterOverdrive.matterRegistry.register(itemStack, matter);
                } else if (data.hasKey("Ore")) {
                    String oreName = data.getString("Ore");
                    MatterOverdrive.matterRegistry.register(oreName, matter);
                }
            }
        } catch (Exception e) {
            MOLog.log(Level.ERROR, e, "There was a problem while trying to register an Item in the Matter Registry from: %s", msg.getSender());
        }
    }

    private static void handleItemBlacklistRegistration(FMLInterModComms.IMCMessage msg) {
        if (!msg.isItemStackMessage()) {
            MOLog.warn("Invalid message type for Matter Blacklist Registration. Message needs to be of type Item Stack");
            return;
        }
        ItemStack itemStack = msg.getItemStackValue();
        if (itemStack != null)
            MatterOverdrive.matterRegistry.addToBlacklist(itemStack);
    }

    private static void handleInscriberRecipeRegistration(FMLInterModComms.IMCMessage msg) {
        if (!msg.isNBTMessage()) {
            MOLog.error("Invalid message format for Inscriber Recipe registration. Message needs to be of type NBT");
            return;
        }
        try {
            NBTTagCompound data = msg.getNBTValue();
            if (containsAllTags(data, "Main", "Sec", "Output", "Energy", "Time")) {
                ItemStack mainStack = ItemStack.loadItemStackFromNBT(data.getCompoundTag("Main"));
                ItemStack secStack = ItemStack.loadItemStackFromNBT(data.getCompoundTag("Sec"));
                ItemStack output = ItemStack.loadItemStackFromNBT(data.getCompoundTag("Output"));
                int energy = data.getInteger("Energy");
                int time = data.getInteger("Time");
                if (mainStack != null && secStack != null && output != null) {
                    InscriberRecipe recipe = new InscriberRecipe(mainStack, secStack, output, energy, time);
                    InscriberRecipes.registerRecipe(recipe);
                }
            }
        } catch (Exception e) {
            MOLog.log(Level.ERROR, e, "There was a problem while trying to register an Inscriber Recipe from: %s", msg.getSender());
        }
    }

    private static boolean containsAllTags(NBTTagCompound tagCompound, String... tags) {
        for (String tag : tags) {
            if (!tagCompound.hasKey(tag)) {
                return false;
            }
        }
        return true;
    }

}
