package matteroverdrive.gui.element.starmap;

import matteroverdrive.Reference;
import matteroverdrive.api.starmap.IShip;
import matteroverdrive.client.render.HoloIcon;
import matteroverdrive.container.slot.MOSlot;
import matteroverdrive.gui.MOGuiBase;
import matteroverdrive.gui.element.ElementInventorySlot;
import matteroverdrive.tile.TileEntityMachineStarMap;
import matteroverdrive.util.MOStringHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class ElementSlotShip extends ElementInventorySlot {
    TileEntityMachineStarMap starMap;

    public ElementSlotShip(MOGuiBase gui, MOSlot slot, int posX, int posY, int width, int height, String type, HoloIcon icon, TileEntityMachineStarMap starMap) {
        super(gui, slot, posX, posY, width, height, type, icon);
        this.starMap = starMap;
    }

    @Override
    public void drawForeground(int mouseX, int mouseY) {
        if (starMap.getPlanet() != null) {
            if (getSlot().getStack() != null) {
                if (getSlot().getStack().getItem() instanceof IShip) {
                    List<String> info = new ArrayList<>();
                    if (starMap.getPlanet().canBuild((IShip) getSlot().getStack().getItem(), getSlot().getStack(), info)) {
                        ItemStack shipStack = getSlot().getStack();
                        String time = MOStringHelper.formatRemainingTime(((IShip) shipStack.getItem()).getRemainingBuildTimeTicks(shipStack, starMap.getPlanet(), Minecraft.getMinecraft().theWorld) / 20);
                        int length = getFontRenderer().getStringWidth(time);
                        getFontRenderer().drawString(time, posX - length - 4, posY + 6, Reference.COLOR_HOLO.getColor());
                    } else {
                        String infoText = String.join(". ", info);
                        int width = getFontRenderer().getStringWidth(infoText);
                        getFontRenderer().drawString(infoText, posX - width - 4, posY + 7, Reference.COLOR_HOLO_RED.getColor());
                    }
                }
            }
        }
    }
}
