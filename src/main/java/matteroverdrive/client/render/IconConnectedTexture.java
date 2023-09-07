package matteroverdrive.client.render;

import net.minecraft.util.IIcon;
import net.minecraft.util.MathHelper;

public class IconConnectedTexture implements IIcon {

    IIcon icon;
    int type;
    public static final int[] table = {0, 12, 4, 8, 3, 15, 7, 11, 1, 13, 5, 9, 2, 14, 6, 10};

    public IconConnectedTexture(IIcon icon) {
        this.icon = icon;
    }

    public void setType(int type) {
        this.type = MathHelper.clamp_int(type, 0, 15);
    }

    @Override
    public int getIconWidth() {
        return icon.getIconWidth() / 4;
    }

    @Override
    public int getIconHeight() {
        return icon.getIconHeight() / 4;
    }

    @Override
    public float getMinU() {
        int posX = (table[type] % 4);
        float segment = ((icon.getMaxU() - icon.getMinU()) / 4f);
        return icon.getMinU() + segment * posX;
    }

    @Override
    public float getMaxU() {
        int posX = (table[type] % 4) + 1;
        float segment = ((icon.getMaxU() - icon.getMinU()) / 4f);
        return icon.getMinU() + segment * posX;
    }

    @Override
    public float getInterpolatedU(double p_94214_1_) {
        float f = this.getMaxU() - this.getMinU();
        return this.getMinU() + f * ((float) p_94214_1_ / 16.0F);
    }

    @Override
    public float getMinV() {
        int posY = Math.floorDiv(table[type], 4);
        float segment = ((icon.getMaxV() - icon.getMinV()) / 4f);
        return icon.getMinV() + segment * posY;
    }

    @Override
    public float getMaxV() {
        int posY = Math.floorDiv(table[type], 4);
        float segment = ((icon.getMaxV() - icon.getMinV()) / 4f);
        return icon.getMinV() + segment + segment * posY;
    }

    @Override
    public float getInterpolatedV(double p_94207_1_) {
        float f = this.getMaxV() - this.getMinV();
        return this.getMinV() + f * ((float) p_94207_1_ / 16.0F);
    }

    @Override
    public String getIconName() {
        return icon.getIconName();
    }
}
