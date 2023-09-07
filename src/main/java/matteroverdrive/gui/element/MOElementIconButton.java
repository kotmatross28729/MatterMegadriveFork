package matteroverdrive.gui.element;

import matteroverdrive.client.data.Color;
import matteroverdrive.client.render.HoloIcon;
import matteroverdrive.container.IButtonHandler;
import matteroverdrive.gui.MOGuiBase;
import matteroverdrive.proxy.ClientProxy;
import matteroverdrive.util.RenderUtils;
import org.lwjgl.opengl.GL11;

public class MOElementIconButton extends MOElementButton {
    HoloIcon icon;
    Color iconColor;

    public MOElementIconButton(MOGuiBase gui, IButtonHandler handler, int posX, int posY, String name, int sheetX, int sheetY, int hoverX, int hoverY, int disabledX, int disabledY, int sizeX, int sizeY, String texture, HoloIcon icon) {
        super(gui, handler, posX, posY, name, sheetX, sheetY, hoverX, hoverY, disabledX, disabledY, sizeX, sizeY, texture);
        this.icon = icon;
    }

    public MOElementIconButton(MOGuiBase gui, IButtonHandler handler, int posX, int posY, String name, int sheetX, int sheetY, int hoverX, int hoverY, int sizeX, int sizeY, String texture, HoloIcon icon) {
        super(gui, handler, posX, posY, name, sheetX, sheetY, hoverX, hoverY, sizeX, sizeY, texture);
        this.icon = icon;
    }

    @Override
    public void drawForeground(int mouseX, int mouseY) {
        if (icon != null) {
            GL11.glEnable(GL11.GL_ALPHA_TEST);
            ClientProxy.holoIcons.bindSheet();
            if (iconColor != null)
                RenderUtils.applyColor(iconColor);
            else
                GL11.glColor3f(1, 1, 1);
            ClientProxy.holoIcons.renderIcon(icon, posX - icon.getOriginalWidth() / 2 + sizeX / 2, posY - icon.getOriginalHeight() / 2 + sizeY / 2);
        }
    }

    public void setIconColor(Color iconColor) {
        this.iconColor = iconColor;
    }

    public void setIcon(HoloIcon icon) {
        this.icon = icon;
    }
}
