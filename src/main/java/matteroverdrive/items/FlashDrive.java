package matteroverdrive.items;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import matteroverdrive.Reference;
import matteroverdrive.client.data.Color;
import matteroverdrive.items.includes.MOBaseItem;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;

public class FlashDrive extends MOBaseItem {
    private IIcon overlay;
    protected Color color;

    public FlashDrive(String name, Color color) {
        super(name);
        this.color = color;
        setMaxStackSize(1);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerIcons(IIconRegister p_94581_1_) {
        this.itemIcon = p_94581_1_.registerIcon(Reference.MOD_ID + ":" + "flash_drive");
        overlay = p_94581_1_.registerIcon(Reference.MOD_ID + ":" + "flash_drive_overlay");
    }

    public boolean hasDetails(ItemStack stack) {
        return true;
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
            return color.getColor();
        }
        return super.getColorFromItemStack(itemStack, pass);
    }
}
