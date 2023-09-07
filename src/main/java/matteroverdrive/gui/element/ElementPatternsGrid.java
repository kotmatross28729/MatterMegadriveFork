package matteroverdrive.gui.element;

import matteroverdrive.data.ItemPattern;
import matteroverdrive.gui.MOGuiBase;
import net.minecraft.item.ItemStack;

import java.util.List;

public class ElementPatternsGrid extends ElementGrid {
    String filter = "";

    public ElementPatternsGrid(MOGuiBase guiBase, int x, int y, int width, int height) {
        super(guiBase, x, y, width, height, width);
        setMargins(0, 0, 4, 0);
    }


    public void updateStackList(List<ItemPattern> patterns) {
        ItemStack stack;
        elements.clear();

        for (ItemPattern pattern : patterns) {
            addElement(new ElementMonitorItemPattern(gui, pattern, gui));
        }
    }

    public void setFilter(String filter) {
        this.filter = filter;
    }

    public boolean shouldBeDisplayed(MOElementBase element) {
        if (element.getName() != null) {
            return element.getName().toLowerCase().contains(filter.toLowerCase());
        }
        return false;
    }
}
