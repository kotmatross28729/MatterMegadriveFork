package matteroverdrive.items.weapon;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import matteroverdrive.Reference;
import matteroverdrive.api.inventory.IEnergyPack;
import matteroverdrive.items.includes.MOBaseItem;
import matteroverdrive.util.MOEnergyHelper;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.IIcon;

import java.util.List;

public class EnergyPack extends MOBaseItem implements IEnergyPack {
    IIcon overlay;

    public EnergyPack(String name) {
        super(name);
    }

    public boolean hasDetails(ItemStack stack) {
        return true;
    }

    public void addDetails(ItemStack itemstack, EntityPlayer player, List infos) {
        super.addDetails(itemstack, player, infos);
        infos.add(EnumChatFormatting.YELLOW + MOEnergyHelper.formatEnergy(null, getEnergyAmount(itemstack)));
    }

    @SideOnly(Side.CLIENT)
    public void registerIcons(IIconRegister iconRegister) {
        this.itemIcon = iconRegister.registerIcon(Reference.MOD_ID + ":" + "container_2");
        overlay = iconRegister.registerIcon(Reference.MOD_ID + ":" + "container_2_overlay");
    }

    @Override
    public int getEnergyAmount(ItemStack pack) {
        return 32000;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public boolean requiresMultipleRenderPasses() {
        return true;
    }

    @Override
    public int getRenderPasses(int metadata) {
        return 2;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public IIcon getIconFromDamageForRenderPass(int damage, int pass) {
        if (pass == 1) {
            return overlay;
        } else {
            return itemIcon;
        }
    }

    @Override
    @SideOnly(Side.CLIENT)
    public int getColorFromItemStack(ItemStack itemStack, int pass) {
        if (pass == 1) {
            return Reference.COLOR_HOLO_RED.getColor();
        }
        return super.getColorFromItemStack(itemStack, pass);
    }
}
