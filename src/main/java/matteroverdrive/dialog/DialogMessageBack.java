package matteroverdrive.dialog;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import matteroverdrive.api.dialog.IDialogMessage;
import matteroverdrive.api.dialog.IDialogNpc;
import matteroverdrive.gui.GuiDialog;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;

public class DialogMessageBack extends DialogMessageRandom {
    public DialogMessageBack() {
        super();
    }

    public DialogMessageBack(String message) {
        super(message);
    }

    @SideOnly(Side.CLIENT)
    protected void setAsGuiActiveMessage(IDialogNpc npc, EntityPlayer player) {
        if (Minecraft.getMinecraft().currentScreen instanceof GuiDialog) {
            IDialogMessage message = ((GuiDialog) Minecraft.getMinecraft().currentScreen).getCurrentMessage();
            if (message != null && message.getParent(npc, player) != null) {
                ((GuiDialog) Minecraft.getMinecraft().currentScreen).setCurrentMessage(message.getParent(npc, player));
            }
        }
    }
}
