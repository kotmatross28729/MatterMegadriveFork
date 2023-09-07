package matteroverdrive.dialog;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import matteroverdrive.api.dialog.IDialogNpc;
import matteroverdrive.gui.GuiDialog;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;

public class DialogMessageBackToMain extends DialogMessageBack {
    public DialogMessageBackToMain() {
        super();
    }

    public DialogMessageBackToMain(String message) {
        super(message);
    }

    @SideOnly(Side.CLIENT)
    @Override
    protected void setAsGuiActiveMessage(IDialogNpc npc, EntityPlayer player) {
        if (Minecraft.getMinecraft().currentScreen instanceof GuiDialog) {
            ((GuiDialog) Minecraft.getMinecraft().currentScreen).setCurrentMessage(npc.getStartDialogMessage(player));
        }
    }
}
