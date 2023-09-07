package matteroverdrive.dialog;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import matteroverdrive.api.dialog.IDialogMessage;
import matteroverdrive.api.dialog.IDialogNpc;
import matteroverdrive.gui.GuiDialog;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;

public class DialogMessageBackTo extends DialogMessageBack {
    IDialogMessage destination;

    public DialogMessageBackTo() {
        super();
    }

    public DialogMessageBackTo(String message, IDialogMessage destination) {
        super(message);
        this.destination = destination;
    }

    @SideOnly(Side.CLIENT)
    @Override
    protected void setAsGuiActiveMessage(IDialogNpc npc, EntityPlayer player) {
        if (Minecraft.getMinecraft().currentScreen instanceof GuiDialog) {
            ((GuiDialog) Minecraft.getMinecraft().currentScreen).setCurrentMessage(destination);
        }
    }
}
