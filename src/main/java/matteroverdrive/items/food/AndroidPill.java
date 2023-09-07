package matteroverdrive.items.food;

import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import matteroverdrive.MatterOverdrive;
import matteroverdrive.Reference;
import matteroverdrive.entity.player.AndroidPlayer;
import matteroverdrive.util.MOStringHelper;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.IIcon;
import net.minecraft.util.WeightedRandomChestContent;
import net.minecraft.world.World;
import net.minecraftforge.common.ChestGenHooks;
import org.lwjgl.input.Keyboard;

import java.util.List;

public class AndroidPill extends ItemFood {
    IIcon overlay;

    public AndroidPill(String name) {
        super(0, 0, false);
        setUnlocalizedName(name);
        setTextureName(Reference.MOD_ID + ":" + name);
        setAlwaysEdible();
        hasSubtypes = true;
    }

    @Override
    public void addInformation(ItemStack itemstack, EntityPlayer player, List infos, boolean p_77624_4_) {
        if (Keyboard.isKeyDown(Keyboard.KEY_LSHIFT) || Keyboard.isKeyDown(Keyboard.KEY_RSHIFT)) {
            infos.add(EnumChatFormatting.GRAY + MOStringHelper.translateToLocal(getUnlocalizedName(itemstack) + ".details"));
        } else {
            infos.add(MOStringHelper.MORE_INFO);
        }

        if (itemstack.getItemDamage() == 2) {
            AndroidPlayer androidPlayer = AndroidPlayer.get(player);
            if (androidPlayer != null && androidPlayer.isAndroid()) {

                infos.add(EnumChatFormatting.GREEN + "XP:" + androidPlayer.getResetXPRequired());
            } else {
                infos.add(EnumChatFormatting.RED + "Not an Android.");
            }
        }
    }

    public void addToDungeons() {
        ChestGenHooks.getInfo(ChestGenHooks.STRONGHOLD_CORRIDOR).addItem(new WeightedRandomChestContent(new ItemStack(this, 1), 1, 1, 1));
    }

    @Override
    public String getUnlocalizedName(ItemStack itemStack) {
        if (itemStack.getItemDamage() == 1) {
            return getUnlocalizedName() + "_blue";
        } else if (itemStack.getItemDamage() == 2) {
            return getUnlocalizedName() + "_yellow";
        }
        return getUnlocalizedName() + "_red";
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerIcons(IIconRegister iconRegister) {
        this.itemIcon = iconRegister.registerIcon(Reference.MOD_ID + ":" + "pill_bottom");
        this.overlay = iconRegister.registerIcon(Reference.MOD_ID + ":" + "pill_top");
    }

    @Override
    @SideOnly(Side.CLIENT)
    public boolean requiresMultipleRenderPasses() {
        return true;
    }

    @SideOnly(Side.CLIENT)
    public void getSubItems(Item item, CreativeTabs creativeTabs, List list) {
        list.add(new ItemStack(item, 1, 0));
        list.add(new ItemStack(item, 1, 1));
        list.add(new ItemStack(item, 1, 2));
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
            if (itemStack.getItemDamage() == 0) {
                return 0xd00000;
            } else if (itemStack.getItemDamage() == 1) {
                return 0x019fea;
            } else if (itemStack.getItemDamage() == 2) {
                return 0xffe400;
            }
        }
        return super.getColorFromItemStack(itemStack, pass);
    }

    @Override
    public ItemStack onItemRightClick(ItemStack itemStack, World world, EntityPlayer player) {
        AndroidPlayer androidPlayer = AndroidPlayer.get(player);
        if (itemStack.getItemDamage() >= 1) {
            if (!androidPlayer.isTurning() && androidPlayer.isAndroid()) {
                player.setItemInUse(itemStack, this.getMaxItemUseDuration(itemStack));
            }
        } else {
            if (!androidPlayer.isAndroid() && !androidPlayer.isTurning()) {
                player.setItemInUse(itemStack, this.getMaxItemUseDuration(itemStack));
            }
        }
        return itemStack;
    }

    public void register() {
        setCreativeTab(MatterOverdrive.tabMatterOverdrive_food);
        GameRegistry.registerItem(this, this.getUnlocalizedName().substring(5));
    }

    @Override
    protected void onFoodEaten(ItemStack itemStack, World world, EntityPlayer player) {
        if (world.isRemote)
            return;

        AndroidPlayer androidPlayer = AndroidPlayer.get(player);
        if (itemStack.getItemDamage() == 0) {
            androidPlayer.startConversion();
        } else if (itemStack.getItemDamage() == 1) {
            androidPlayer.setAndroid(false);
        } else if (itemStack.getItemDamage() == 2) {
            if (!androidPlayer.isTurning() && androidPlayer.isAndroid()) {
                int xpLevels = androidPlayer.resetUnlocked();
                player.addExperienceLevel(xpLevels);
            }
        }
    }
}
