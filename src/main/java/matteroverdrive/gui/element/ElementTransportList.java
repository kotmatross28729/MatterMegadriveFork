package matteroverdrive.gui.element;

import matteroverdrive.Reference;
import matteroverdrive.api.transport.TransportLocation;
import matteroverdrive.gui.MOGuiBase;
import matteroverdrive.gui.events.IListHandler;
import matteroverdrive.machines.transporter.TileEntityMachineTransporter;

public class ElementTransportList extends MOElementListBox {
    TileEntityMachineTransporter transporter;

    public ElementTransportList(MOGuiBase containerScreen, IListHandler listHandler, int x, int y, int width, int height, TileEntityMachineTransporter transporter) {
        super(containerScreen, listHandler, x, y, width, height);
        this.transporter = transporter;
    }

    @Override
    public void DrawElement(int i, int x, int y, int selectedLineColor, int selectedTextColor, boolean selected, boolean BG) {
        TransportLocation position = transporter.getPositions().get(i);

        if (BG) {
            if (selected && transporter.isLocationValid(position))
                MOElementButton.NORMAL_TEXTURE.render(x, y, getElementWidth(i), getElementHeight(i));
            else {
                MOElementButton.HOVER_TEXTURE_DARK.render(x, y, getElementWidth(i), getElementHeight(i));
            }
        } else {

            String info = "[ X: " + (position.x + transporter.xCoord) + ", Y: " + (position.y + transporter.yCoord) + ", Z: " + (position.z + transporter.zCoord) + " ]";
            gui.drawCenteredString(getFontRenderer(), position.name, x + getElementWidth(i) / 2, y + getElementHeight(i) / 2 - 4, transporter.isLocationValid(position) ? selectedTextColor : Reference.COLOR_HOLO_RED.getColor());
            //gui.drawCenteredString(getFontRenderer(), EnumChatFormatting.YELLOW + info,x + getElementWidth(i)/2,y + getElementHeight(i) / 2 + 2,0xFFFFFF);
        }
    }

    @Override
    public void drawElementTooltip(int index, int mouseX, int mouseY) {

    }

    @Override
    public int getElementHeight(int id) {
        return 20;
    }

    @Override
    public int getElementWidth(int id) {
        return sizeX - 4;
    }

    @Override
    protected boolean shouldBeDisplayed(IMOListBoxElement element) {
        return true;
    }

    @Override
    public IMOListBoxElement getElement(int index) {

        return null;
    }

    public int getElementCount() {

        return transporter.getPositions().size();
    }
}
