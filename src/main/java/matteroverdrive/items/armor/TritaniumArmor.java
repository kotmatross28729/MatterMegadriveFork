package matteroverdrive.items.armor;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import matteroverdrive.Reference;
import matteroverdrive.client.model.ModelTritaniumArmor;
import matteroverdrive.proxy.ClientProxy;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.entity.RenderPlayer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;

public class TritaniumArmor extends ItemArmor {
    public TritaniumArmor(ArmorMaterial armorMaterial, int renderIndex, int renderType) {
        super(armorMaterial, renderIndex, renderType);
    }

    @Override
    public String getArmorTexture(ItemStack stack, Entity entity, int slot, String type) {
        return String.format(Reference.PATH_ARMOR + "Tritanium_Armor2_layer_%d.png", slot == 3 ? 2 : 1);
        //return String.format(Reference.PATH_ARMOR + "tritanium_layer_%d%s.png",(slot == 2 ? 2 : 1),type == null ? "" : String.format("_%s", type));
    }

    @SideOnly(Side.CLIENT)
    @Override
    public ModelBiped getArmorModel(EntityLivingBase entityLiving, ItemStack itemStack, int armorSlot) {
        ModelTritaniumArmor armorModel = armorSlot == 3 ? ClientProxy.renderHandler.modelTritaniumArmorFeet : ClientProxy.renderHandler.modelTritaniumArmor;
        armorModel.bipedHead.showModel = armorSlot == 0;
        armorModel.bipedHeadwear.showModel = armorSlot == 0;
        armorModel.bipedBody.showModel = armorSlot == 1;
        armorModel.bipedRightArm.showModel = armorSlot == 1;
        armorModel.bipedLeftArm.showModel = armorSlot == 1;
        armorModel.bipedRightLeg.showModel = armorSlot == 2 || armorSlot == 3;
        armorModel.bipedLeftLeg.showModel = armorSlot == 2 || armorSlot == 3;
        armorModel.FootLeft.showModel = armorModel.FootRight.showModel = armorSlot == 3;

        Render render = RenderManager.instance.getEntityRenderObject(entityLiving);

        if (render instanceof RenderPlayer) {
            RenderPlayer renderPlayer = (RenderPlayer) render;
            armorModel.isSneak = entityLiving.isSneaking();
            armorModel.heldItemRight = renderPlayer.modelArmor.heldItemRight;
            armorModel.heldItemLeft = renderPlayer.modelArmor.heldItemLeft;
            armorModel.aimedBow = renderPlayer.modelArmor.aimedBow;
            ItemStack heldItem = entityLiving.getHeldItem();
        }

        return armorModel;
    }
}
