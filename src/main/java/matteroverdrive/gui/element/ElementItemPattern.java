package matteroverdrive.gui.element;

import matteroverdrive.data.ItemPattern;
import matteroverdrive.data.ScaleTexture;
import matteroverdrive.gui.MOGuiBase;
import matteroverdrive.util.MatterDatabaseHelper;
import matteroverdrive.util.RenderUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;

import java.util.List;

public class ElementItemPattern extends ElementSlot {
    ScaleTexture texture;
    ItemPattern pattern;
    ItemStack itemStack;
    int amount = 0;

    public ElementItemPattern(MOGuiBase gui, ItemPattern pattern, String bgType, int width, int height) {
        super(gui, 0, 0, width, height, bgType);
        this.texture = new ScaleTexture(getTexture(bgType), width, height).setOffsets(2, 2, 2, 2);
        this.pattern = pattern;
        if (pattern != null) {
            itemStack = pattern.toItemStack(false);
            this.name = itemStack.getDisplayName();
        }
    }

    @Override
    public void drawForeground(int mouseX, int mouseY) {
        if (pattern != null) {
            itemStack.stackSize = amount;
            RenderUtils.renderStack(posX + 3, posY + 3, 0, itemStack, true);
        }
    }

    @Override
    public void drawBackground(int mouseX, int mouseY, float gameTicks) {
        texture.render(posX, posY, sizeX, sizeY);
    }

    @Override
    public void addTooltip(List<String> list, int mouseX, int mouseY) {
        if (pattern != null) {
            if (itemStack != null) {
                list.addAll(itemStack.getTooltip(Minecraft.getMinecraft().thePlayer, false));
                String name = list.get(0);
                int progress = pattern.getProgress();
                name = MatterDatabaseHelper.getPatternInfoColor(progress) + name + " [" + progress + "%]";
                list.set(0, name);
            }
        }
    }

    public ItemPattern getPattern() {
        return pattern;
    }

    public void setPattern(ItemPattern pattern) {
        this.pattern = pattern;
        if (this.pattern != null)
            itemStack = pattern.toItemStack(false);
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public ScaleTexture getTexture() {
        return texture;
    }
}
