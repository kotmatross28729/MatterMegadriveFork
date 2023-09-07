package matteroverdrive.guide.infograms;

import matteroverdrive.MatterOverdrive;
import matteroverdrive.Reference;
import matteroverdrive.guide.GuideElementAbstract;
import matteroverdrive.guide.MOGuideEntry;
import matteroverdrive.util.MOLog;
import matteroverdrive.util.RenderUtils;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;
import org.w3c.dom.Element;

public class InfogramCreates extends GuideElementAbstract {
    private static final ResourceLocation background = new ResourceLocation(Reference.PATH_ELEMENTS + "guide_info_creates.png");
    ItemStack from;
    ItemStack to;

    @Override
    public void drawElement(int width, int mouseX, int mouseY) {
        bindTexture(background);
        GL11.glColor3d(1, 1, 1);
        RenderUtils.drawPlane(marginLeft, marginTop, 0, 115, 36);
        if (from != null) {
            GL11.glPushMatrix();
            GL11.glTranslated(marginLeft + 5, marginTop + 5, 0);
            GL11.glScaled(1.5, 1.5, 1.5);
            RenderUtils.renderStack(0, 0, from);
            GL11.glPopMatrix();
        }
        if (to != null) {
            GL11.glPushMatrix();
            GL11.glTranslated(marginLeft + 86, marginTop + 5, 0);
            GL11.glScaled(1.5, 1.5, 1.5);
            RenderUtils.renderStack(0, 0, to);
            GL11.glPopMatrix();
        }
    }

    @Override
    protected void loadContent(MOGuideEntry entry, Element element, int width, int height) {
        if (element.hasAttribute("to")) {
            to = shortCodeToStack(decodeShortcode(element.getAttribute("to")));
            if (to == null) {
                MOLog.warn("Invalid to Itemstack in infogram of type create: %s", element.hasAttribute("to"));
            }
        } else {
            MOLog.warn("There is no to Itemstack in infogram of type create");
        }

        if (element.hasAttribute("from")) {
            from = shortCodeToStack(decodeShortcode(element.getAttribute("from")));
            if (from == null) {
                MOLog.warn("Invalid from Itemstack in infogram of type create: %", element.getAttribute("from"));
            }
        } else {
            if (entry.getStackIcons().length > 0)
                from = entry.getStackIcons()[0];
        }
        this.height = 36 + 16;
        this.width = 100;
    }
}
