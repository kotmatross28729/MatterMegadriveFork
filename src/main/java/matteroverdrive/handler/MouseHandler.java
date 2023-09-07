package matteroverdrive.handler;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import matteroverdrive.api.weapon.IWeapon;
import matteroverdrive.gui.GuiAndroidHud;
import net.minecraft.client.Minecraft;
import net.minecraftforge.client.event.MouseEvent;

@SideOnly(Side.CLIENT)
public class MouseHandler {

    @SideOnly(Side.CLIENT)
    @SubscribeEvent
    public void onMouseEvent(MouseEvent event) {
        if (GuiAndroidHud.showRadial) {
            GuiAndroidHud.radialDeltaX -= event.dx / 100D;
            GuiAndroidHud.radialDeltaY += event.dy / 100D;

            double mag = Math.sqrt(GuiAndroidHud.radialDeltaX * GuiAndroidHud.radialDeltaX + GuiAndroidHud.radialDeltaY * GuiAndroidHud.radialDeltaY);
            if (mag > 1.0D) {
                GuiAndroidHud.radialDeltaX /= mag;
                GuiAndroidHud.radialDeltaY /= mag;
            }
        }
        if (Minecraft.getMinecraft().thePlayer.getHeldItem() != null && Minecraft.getMinecraft().thePlayer.getHeldItem().getItem() instanceof IWeapon) {
            if (event.button == 0 && event.buttonstate) {
                if (((IWeapon) Minecraft.getMinecraft().thePlayer.getHeldItem().getItem()).onLeftClick(Minecraft.getMinecraft().thePlayer.getHeldItem(), Minecraft.getMinecraft().thePlayer)) {
                    event.setCanceled(true);
                }
            }
        }
    }
}
