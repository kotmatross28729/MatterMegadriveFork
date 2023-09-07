package matteroverdrive.items;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import matteroverdrive.items.includes.MOBaseItem;
import matteroverdrive.machines.MOTileEntityMachine;
import matteroverdrive.util.MOStringHelper;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.IIcon;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

import java.util.List;
import java.util.UUID;

public class SecurityProtocol extends MOBaseItem {

    IIcon[] icons;
    String[] types = new String[]{"empty", "claim", "access", "remove"};

    public SecurityProtocol(String name) {
        super(name);
        setMaxStackSize(16);
    }

    @Override
    public void addDetails(ItemStack itemstack, EntityPlayer player, List infos) {
        super.addDetails(itemstack, player, infos);

        if (itemstack.hasTagCompound()) {
            try {
                EntityPlayer entityPlayer = player.worldObj.func_152378_a(UUID.fromString(itemstack.getTagCompound().getString("Owner")));
                if (entityPlayer != null) {
                    String owner = entityPlayer.getGameProfile().getName();
                    infos.add(EnumChatFormatting.YELLOW + "Owner: " + owner);
                }
            } catch (Exception e) {
                infos.add(EnumChatFormatting.RED + MOStringHelper.translateToLocal(getUnlocalizedName() + ".invalid"));
            }
        }
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerIcons(IIconRegister p_94581_1_) {
        icons = new IIcon[types.length];

        for (int i = 0; i < types.length; i++) {
            icons[i] = p_94581_1_.registerIcon(this.getIconString() + "_" + types[i]);
        }
    }

    @Override
    @SideOnly(Side.CLIENT)
    public IIcon getIconFromDamage(int damage) {
        return icons[damage];
    }

    @Override
    public String getUnlocalizedName(ItemStack stack) {
        return super.getUnlocalizedName(stack) + "." + types[MathHelper.clamp_int(stack.getItemDamage(), 0, types.length)];
    }

    @Override
    public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player) {
        if (!stack.hasTagCompound()) {
            if (player.isSneaking()) {
                TagCompountCheck(stack);
                stack.getTagCompound().setString("Owner", player.getGameProfile().getId().toString());
                stack.setItemDamage(1);
            }
        } else if (stack.getTagCompound().getString("Owner").equals(player.getGameProfile().getId().toString()) || player.capabilities.isCreativeMode) {
            if (player.isSneaking()) {
                int damage = stack.getItemDamage() + 1;
                if (damage >= types.length)
                    damage = 1;

                stack.setItemDamage(damage);
            }
        }

        return stack;
    }

    @Override
    public boolean onItemUseFirst(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ) {
        if (!world.isRemote) {
            TileEntity tileEntity = world.getTileEntity(x, y, z);
            if (tileEntity instanceof MOTileEntityMachine) {
                if (stack.getItemDamage() == 1) {
                    if (((MOTileEntityMachine) tileEntity).claim(stack)) {
                        stack.stackSize--;
                        return true;
                    }
                } else if (stack.getItemDamage() == 3) {
                    if (((MOTileEntityMachine) tileEntity).unclaim(stack)) {
                        stack.stackSize--;
                        return true;
                    }
                }
            }
        }
        return false;
    }

    @Override
    public boolean hasDetails(ItemStack stack) {
        return stack.hasTagCompound() && stack.getTagCompound().hasKey("Owner");
    }
}
