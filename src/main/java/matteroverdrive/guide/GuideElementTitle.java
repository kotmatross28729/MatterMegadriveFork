package matteroverdrive.guide;

import org.lwjgl.opengl.GL11;
import org.w3c.dom.Element;

public class GuideElementTitle extends GuideElementTextAbstract {
    String title;
    double size = 3;

    @Override
    public void drawElement(int width, int mouseX, int mouseY) {
        GL11.glPushMatrix();
        int titleWidth = (int) (getFontRenderer().getStringWidth(title) * size);
        int x = 0;
        if (textAlign == 1) {
            x = (width - marginLeft - marginRight) / 2 - titleWidth / 2;
        } else if (textAlign == 2) {
            x = (width - marginLeft - marginRight) - titleWidth;
        }
        GL11.glTranslated(x + marginLeft, marginTop, 0);
        GL11.glScaled(size, size, size);
        getFontRenderer().drawString(title, 0, 0, color.getColor());
        GL11.glPopMatrix();
    }

    @Override
    protected void loadContent(MOGuideEntry entry, Element element, int width, int height) {
        title = handleVariables(element.getTextContent(), entry);
        if (element.hasAttribute("size")) {
            size = Double.parseDouble(element.getAttribute("size"));
        }

        this.width = width;
        this.height = (int) (getFontRenderer().FONT_HEIGHT * size);
    }
}
