package matteroverdrive.guide;

import matteroverdrive.util.RenderUtils;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MathHelper;
import org.lwjgl.opengl.GL11;
import org.w3c.dom.Element;

import java.util.Random;

public class GuideElementPreview extends GuideElementAbstract {
    private static Random random = new Random();
    double size = 1;
    ItemStack itemStack;

    @Override
    public void drawElement(int width, int mouseX, int mouseY) {
        if (itemStack != null) {
            GL11.glPushMatrix();

            if (textAlign == 1)
                GL11.glTranslated(width / 2 - 8 * size, 0, 0);
            else if (textAlign == 2)
                GL11.glTranslated(width - 16 * size, 0, 0);

            GL11.glTranslated(marginLeft, marginTop, 0);
            GL11.glScaled(size, size, size);
            RenderUtils.renderStack(0, 0, itemStack);
            GL11.glPopMatrix();
        }
    }

    @Override
    protected void loadContent(MOGuideEntry entry, Element element, int width, int height) {
        if (element.hasAttribute("item")) {
            itemStack = shortCodeToStack(decodeShortcode(element.getAttribute("item")));
        } else if (entry.getStackIcons() != null && entry.getStackIcons().length > 0) {
            int index = random.nextInt(entry.getStackIcons().length);
            if (element.hasAttribute("index")) {
                index = Integer.parseInt(element.getAttribute("index"));
            }
            index = MathHelper.clamp_int(index, 0, entry.getStackIcons().length - 1);
            itemStack = entry.getStackIcons()[index];
        }
        if (element.hasAttribute("size")) {
            size = Double.parseDouble(element.getAttribute("size"));
        }
        this.height = (int) (16 * size);
        this.width = (int) (16 * size);
    }
}
