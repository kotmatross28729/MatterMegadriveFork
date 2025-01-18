package matteroverdrive.client.render.item;

import com.emoniph.witchery.common.ExtendedPlayer;
import com.emoniph.witchery.util.TransformCreature;
import matteroverdrive.Reference;
import matteroverdrive.core.CFG;
import matteroverdrive.proxy.ClientProxy;
import matteroverdrive.util.RenderUtils;
import matteroverdrive.util.WeaponHelper;
import matteroverdrive.util.animation.MOEasing;
import matteroverdrive.util.math.MOMathHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityClientPlayerMP;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

import java.util.Random;

import static com.emoniph.witchery.client.ClientEvents.wolfSkin;
import static org.lwjgl.opengl.GL11.glColor3f;
import static org.lwjgl.opengl.GL11.glPopMatrix;
import static org.lwjgl.opengl.GL11.glPushMatrix;
import static org.lwjgl.opengl.GL11.glRotated;
import static org.lwjgl.opengl.GL11.glScaled;
import static org.lwjgl.opengl.GL11.glTranslated;
import static org.lwjgl.opengl.GL11.glTranslatef;

public class ItemRenderPlasmaShotgun extends WeaponItemRenderer {

    public static final String TEXTURE = Reference.PATH_ITEM + "plasma_shotgun.png";
    public static final String MODEL = Reference.PATH_MODEL + "item/plasma_shotgun.obj";
    public static final float SCALE = 0.85f;
    public static final float THIRD_PERSON_SCALE = 0.6f;
    public static final float ITEM_SCALE = 0.3f;
    public static final float SCALE_DROP = 0.4f;

    private Random random;

    public ItemRenderPlasmaShotgun() {
        super(new ResourceLocation(MODEL), new ResourceLocation(TEXTURE));
        random = new Random();
    }

    @Override
    public boolean handleRenderType(ItemStack item, ItemRenderType type) {
        return true;
    }

    @Override
    public boolean shouldUseRenderHelper(ItemRenderType type, ItemStack item, ItemRendererHelper helper) {
        return type != ItemRenderType.EQUIPPED_FIRST_PERSON;
    }

    @Override
    public void renderItem(ItemRenderType type, ItemStack item, Object... data) {
        if (type == ItemRenderType.EQUIPPED_FIRST_PERSON) {
            renderFirstPerson(item);
        } else if (type == ItemRenderType.INVENTORY) {
            renderItem(item);
        } else if (type == ItemRenderType.ENTITY) {
            renderDrop(item);
        } else {
            renderThirdPerson(type, item);
        }
    }

    void renderItem(ItemStack item) {
        glPushMatrix();
        glScaled(ITEM_SCALE, ITEM_SCALE, ITEM_SCALE);
        glTranslated(0, -0.2, -2.5);
        glRotated(180, 0, 1, 0);
        renderGun(ItemRenderType.INVENTORY, item);
        glPopMatrix();
    }

    void renderThirdPerson(ItemRenderType renderType, ItemStack item) {
            glPushMatrix();
            glScaled(THIRD_PERSON_SCALE, THIRD_PERSON_SCALE, THIRD_PERSON_SCALE);
            glTranslated(1.5, 1.5, 1.3);
            glRotated(45, 0, 1, 0);
            glRotated(-70, 1, 0, 0);
            renderGun(renderType, item);
            glPopMatrix();
    }

    void renderDrop(ItemStack item) {
        glPushMatrix();
        glScaled(SCALE_DROP, SCALE_DROP, SCALE_DROP);
        glTranslated(0, 0, 2);
        renderGun(ItemRenderType.ENTITY, item);
        glPopMatrix();
    }

    void renderFirstPerson(ItemStack item) {
        float zoomValue = MOEasing.Sine.easeInOut(ClientProxy.instance().getClientWeaponHandler().ZOOM_TIME, 0, 1, 1f);
        float recoilValue = MOEasing.Quart.easeInOut(getRecoilTime(), 0, 1, 1f);
        GL11.glPushMatrix();

        if (CFG.enablehands) {
            if (iswitcheryloaded()) {
                EntityClientPlayerMP entityclientplayermp = mc.thePlayer;
                ExtendedPlayer playerEx = ExtendedPlayer.get(entityclientplayermp);
                TransformCreature creatureType = playerEx.getCreatureType();
                if (creatureType != TransformCreature.NONE) {
                    this.mc.getTextureManager().bindTexture(entityclientplayermp.getLocationSkin());
                    if (creatureType != TransformCreature.PLAYER) {
                        if (creatureType == TransformCreature.WOLFMAN) {
                            this.mc.getTextureManager().bindTexture(wolfSkin);
                        } else if (creatureType == TransformCreature.WOLF) {
                            this.mc.getTextureManager().bindTexture(wolfSkin);
                        }
                    }
                } else {
                    this.mc.getTextureManager().bindTexture(entityclientplayermp.getLocationSkin());
                }
            } else {
                ResourceLocation skin = Minecraft.getMinecraft().thePlayer.getLocationSkin();
                Minecraft.getMinecraft().getTextureManager().bindTexture(skin);
            }
        }

        Minecraft.getMinecraft().renderViewEntity.rotationPitch -= recoilValue * 0.7f;
        Minecraft.getMinecraft().renderViewEntity.rotationYaw += recoilValue * 0.1f * random.nextGaussian();

        glTranslated(2.7, MOMathHelper.Lerp(-0.3f, -0.8f, zoomValue), MOMathHelper.Lerp(-1, -1.1f, zoomValue));
        glTranslatef(0, recoilValue * 0.02f * getRecoilAmount(), 0);
        glRotated(MOMathHelper.Lerp(45, 0, zoomValue), 1, 1, 0);
        glRotated(MOMathHelper.Lerp(0, MOMathHelper.Lerp(3, 0, zoomValue), recoilValue), 0, 0, 1);

        if (CFG.enablehands) {
            glColor3f(1, 1, 1);
            renderHandShotgun();
        }
        GL11.glPopMatrix();

        glPushMatrix();
        glScaled(SCALE, SCALE, SCALE);

        if (Minecraft.getMinecraft().thePlayer.isUsingItem()) {
            glTranslated(2.5, -0.5, 0.8);
            glRotated(MOMathHelper.Lerp(48, 50, recoilValue), 0, 0, 1);
            glRotated(-77, 0, 1, 0);
            glScaled(1.2, 1.2, 0.8);
        } else {
            glTranslated(2, MOMathHelper.Lerp(-0.5f, -1f, recoilValue), 0.3);
            glRotated(MOMathHelper.Lerp(28, 50, recoilValue), 0, 0, 1);
            glRotated(-89, 0, 1, 0);
            glScaled(1.2, 1.2, 0.8);
        }

        renderGun(ItemRenderType.EQUIPPED_FIRST_PERSON, item);
        glPopMatrix();
    }

    void renderGun(ItemRenderType renderType, ItemStack item) {
        RenderUtils.applyColor(WeaponHelper.getColor(item));
        Minecraft.getMinecraft().renderEngine.bindTexture(weaponTexture);
        weaponModel.renderAll();
    }
    //Put it in a separate method
    void renderHandShotgun() {
        double length = 1.8;
        double width = 0.6;
        double depth = 0.5;
        if (!Minecraft.getMinecraft().thePlayer.isInvisible()) {
            Tessellator.instance.startDrawingQuads();
            Tessellator.instance.setNormal(0, 0, -1);
            Tessellator.instance.addVertexWithUV(0, 0, 0, 44f / 64f, 20f / 32f);
            Tessellator.instance.addVertexWithUV(0, length, 0, 44f / 64f, 1);
            Tessellator.instance.addVertexWithUV(depth, length, 0, 48f / 64f, 1);
            Tessellator.instance.addVertexWithUV(depth, 0, 0, 48f / 64f, 20f / 32f);


            Tessellator.instance.setNormal(-1, 0, 0);
            Tessellator.instance.addVertexWithUV(0, 0, 0, 44f / 64f, 20f / 32f);
            Tessellator.instance.addVertexWithUV(0, 0, width, 40f / 64f, 20f / 32f);
            Tessellator.instance.addVertexWithUV(0, length, width, 40f / 64f, 1f);
            Tessellator.instance.addVertexWithUV(0, length, 0, 44f / 64f, 1f);
            Tessellator.instance.draw();
        }
    }
}
