package matteroverdrive.guide;

import matteroverdrive.MatterOverdrive;
import matteroverdrive.util.MOLog;
import org.apache.logging.log4j.Level;
import org.lwjgl.opengl.GL11;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class GuideElementPage extends GuideElementAbstract {
    List<IGuideElement> elements;

    public GuideElementPage() {
        elements = new ArrayList<>();
    }

    @Override
    public void loadElement(MOGuideEntry entry, Element element, Map<String, String> styleSheetMap, int width, int height) {
        super.loadElement(entry, element, styleSheetMap, width, height);
        NodeList children = element.getChildNodes();
        for (int c = 0; c < children.getLength(); c++) {

            if (!(children.item(c) instanceof Element))
                continue;

            Element child = (Element) children.item(c);

            Class<? extends IGuideElement> guideElementClass = MatterOverdriveGuide.getElementHandler(child.getTagName());
            if (guideElementClass != null) {
                try {
                    IGuideElement e = guideElementClass.newInstance();
                    if (e != null) {
                        e.setGUI(gui);
                        e.loadElement(entry, child, styleSheetMap, width - marginLeft - marginRight, height - marginBottom - marginTop);
                        elements.add(e);
                    }
                } catch (Exception e) {
                    MOLog.log(Level.ERROR, e, "Could not create Guide Element of class %s", guideElementClass);
                }
            }
        }
    }

    @Override
    protected void loadContent(MOGuideEntry entry, Element element, int width, int height) {

    }

    @Override
    public void drawElement(int width, int mouseX, int mouseY) {
        GL11.glPushMatrix();
        GL11.glTranslated(marginLeft, marginTop, 0);
        int x = 0;
        int y = 0;
        int floatRightX = width;
        int maxHeight = 0;


        for (IGuideElement element : elements) {
            GL11.glPushMatrix();
            if (element.getFloating() == 2) {
                GL11.glTranslated(floatRightX - element.getWidth() - marginLeft, 0, 0);
            }
            GL11.glTranslated(x, y, 0);
            element.drawElement(width - marginLeft - marginRight, mouseX - x - marginLeft, mouseY - y - marginTop);
            if (element.getFloating() == 1) {
                if (x + element.getWidth() > width) {
                    y += maxHeight;
                    x = 0;
                    floatRightX = width;
                    maxHeight = 0;
                } else {
                    maxHeight = Math.max(element.getHeight(), maxHeight);
                    x += element.getWidth();
                }
            } else if (element.getFloating() == 2) {
                if (floatRightX - element.getWidth() < 0) {
                    maxHeight = Math.max(maxHeight, element.getHeight());
                    floatRightX = width;
                    y += maxHeight;
                    maxHeight = 0;
                } else {
                    floatRightX -= element.getWidth();
                }
            } else {
                maxHeight = Math.max(maxHeight, element.getHeight());
                y += maxHeight;
                maxHeight = 0;
                x = 0;
                floatRightX = width;
            }
            GL11.glPopMatrix();
        }
        GL11.glPopMatrix();
    }
}
