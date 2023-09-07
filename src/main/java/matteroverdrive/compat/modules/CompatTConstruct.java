package matteroverdrive.compat.modules;

import cpw.mods.fml.common.event.FMLInterModComms;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import matteroverdrive.MatterOverdrive;
import matteroverdrive.Reference;
import matteroverdrive.compat.Compat;
import matteroverdrive.handler.ConfigurationHandler;
import matteroverdrive.init.MatterOverdriveBlocks;
import matteroverdrive.init.MatterOverdriveFluids;
import matteroverdrive.init.MatterOverdriveItems;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fluids.FluidStack;

@Compat("TConstruct")
public class CompatTConstruct {
    private static int TRITANIUM_METAL_FLUID_ID = 201;

    @Compat.PostInit
    public static void postInit(FMLPostInitializationEvent event) {
        TRITANIUM_METAL_FLUID_ID = MatterOverdrive.configHandler.getInt("TConstruct molten tritanium ID", ConfigurationHandler.CATEGORY_COMPATIBILITY, 201, "The ID of Molten Tritanium");
        NBTTagCompound tagCompound = new NBTTagCompound();
        tagCompound.setInteger("Id", TRITANIUM_METAL_FLUID_ID);
        tagCompound.setString("Name", "Tritanium");
        tagCompound.setInteger("HarvestLevel", 3);
        tagCompound.setInteger("Durability", 2048);
        tagCompound.setInteger("MiningSpeed", 100);
        tagCompound.setInteger("Attack", 3);
        tagCompound.setFloat("HandleModifier", 1f);
        tagCompound.setInteger("Reinforced", 3);
        tagCompound.setInteger("Color", Reference.COLOR_GUI_NORMAL.getColor());

        FMLInterModComms.sendMessage("TConstruct", "addMaterial", tagCompound);

        tagCompound = new NBTTagCompound();
        tagCompound.setInteger("MaterialId", TRITANIUM_METAL_FLUID_ID);
        tagCompound.setInteger("Value", 2);
        NBTTagCompound itemTag = new NBTTagCompound();
        (new ItemStack(MatterOverdriveItems.tritanium_ingot)).writeToNBT(itemTag);
        tagCompound.setTag("Item", itemTag);
        FMLInterModComms.sendMessage("TConstruct", "addMaterialItem", tagCompound);

        tagCompound = new NBTTagCompound();
        tagCompound.setString("FluidName", MatterOverdriveFluids.moltenTritanium.getName());
        //(new FluidStack(MatterOverdriveFluids.moltenTritanium,1)).writeToNBT(tagCompound);
        tagCompound.setInteger("MaterialId", TRITANIUM_METAL_FLUID_ID);
        FMLInterModComms.sendMessage("TConstruct", "addPartCastingMaterial", tagCompound);

        tagCompound = new NBTTagCompound();
        tagCompound.setInteger("MaterialId", TRITANIUM_METAL_FLUID_ID);
        tagCompound.setInteger("Value", 1);


        tagCompound = new NBTTagCompound();
        itemTag = new NBTTagCompound();
        (new ItemStack(MatterOverdriveBlocks.tritaniumOre)).writeToNBT(itemTag);
        tagCompound.setTag("Item", itemTag);
        tagCompound.setTag("Block", itemTag);
        tagCompound.setInteger("Temperature", 800);
        (new FluidStack(MatterOverdriveFluids.moltenTritanium, 288)).writeToNBT(tagCompound);
        FMLInterModComms.sendMessage("TConstruct", "addSmelteryMelting", tagCompound);

        tagCompound = new NBTTagCompound();
        itemTag = new NBTTagCompound();
        (new ItemStack(MatterOverdriveItems.tritanium_ingot)).writeToNBT(itemTag);
        tagCompound.setTag("Item", itemTag);
        itemTag = new NBTTagCompound();
        (new ItemStack(MatterOverdriveBlocks.tritanium_block)).writeToNBT(itemTag);
        tagCompound.setTag("Block", itemTag);
        tagCompound.setInteger("Temperature", 700);
        (new FluidStack(MatterOverdriveFluids.moltenTritanium, 144)).writeToNBT(tagCompound);
        FMLInterModComms.sendMessage("TConstruct", "addSmelteryMelting", tagCompound);

        FMLInterModComms.sendMessage("TConstruct", "addFluxBattery", new ItemStack(MatterOverdriveItems.battery));
        FMLInterModComms.sendMessage("TConstruct", "addFluxBattery", new ItemStack(MatterOverdriveItems.hc_battery));
    }
}
