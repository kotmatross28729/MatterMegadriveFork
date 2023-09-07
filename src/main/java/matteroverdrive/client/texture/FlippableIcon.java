package matteroverdrive.client.texture;

import net.minecraft.util.IIcon;

public class FlippableIcon implements IIcon {

    protected IIcon original;
    boolean flip_u;
    boolean flip_v;

    public FlippableIcon(IIcon o) {

        if (o == null)
            throw new RuntimeException("Cannot create a wrapper holoIcon with a null holoIcon.");

        this.original = o;
        this.flip_u = false;
        this.flip_v = false;
    }

    @Override
    public int getIconWidth() {
        return this.original.getIconWidth();
    }

    @Override
    public int getIconHeight() {
        return this.original.getIconHeight();
    }

    @Override
    public float getMinU() {
        if (flip_u)
            return this.original.getMaxU();
        return this.original.getMinU();
    }

    @Override
    public float getMaxU() {
        if (flip_u)
            return this.original.getMinU();
        return this.original.getMaxU();
    }

    @Override
    public float getInterpolatedU(double px) {
        if (flip_u)
            return this.original.getInterpolatedU(16 - px);
        return this.original.getInterpolatedU(px);
    }

    @Override
    public float getMinV() {
        if (flip_v)
            return this.original.getMaxV();
        return this.original.getMinV();
    }

    @Override
    public float getMaxV() {
        if (flip_v)
            return this.original.getMinV();
        return this.original.getMaxV();
    }

    @Override
    public float getInterpolatedV(double px) {
        if (flip_v)
            return this.original.getInterpolatedV(16 - px);
        return this.original.getInterpolatedV(px);
    }

    @Override
    public String getIconName() {
        return this.original.getIconName();
    }

    public IIcon getOriginal() {
        return this.original;
    }

    public void setFlip(boolean u, boolean v) {
        flip_u = u;
        flip_v = v;
    }

    public int setFlip(int orientation) {
        flip_u = (orientation & 8) == 8;
        flip_v = (orientation & 16) == 16;
        return orientation & 7;
    }

}