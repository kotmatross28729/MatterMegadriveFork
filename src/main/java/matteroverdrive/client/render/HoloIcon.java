package matteroverdrive.client.render;

import net.minecraft.util.IIcon;

public class HoloIcon {
    private IIcon icon;
    private int originalWidth;
    private int originalHeight;

    public HoloIcon(IIcon icon, int originalX, int originalY) {
        this.icon = icon;
        setOriginalSize(originalX, originalY);
    }

    public void setOriginalSize(int originalX, int originalY) {
        this.originalWidth = originalX;
        this.originalHeight = originalY;
    }

    public int getOriginalWidth() {
        return originalWidth;
    }

    public int getOriginalHeight() {
        return originalHeight;
    }

    public IIcon getIcon() {
        return icon;
    }
}
