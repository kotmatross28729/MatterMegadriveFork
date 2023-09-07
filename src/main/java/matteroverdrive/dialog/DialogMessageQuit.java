package matteroverdrive.dialog;

import matteroverdrive.api.dialog.IDialogNpc;
import net.minecraft.entity.player.EntityPlayer;

public class DialogMessageQuit extends DialogMessageRandom {
    public DialogMessageQuit() {
        super();
    }

    public DialogMessageQuit(String message, String question) {
        super(message, question);
    }

    public DialogMessageQuit(String message) {
        super(message);
    }

    @Override
    public void onInteract(IDialogNpc npc, EntityPlayer player) {
        player.closeScreen();
    }

    @Override
    public boolean canInteract(IDialogNpc npc, EntityPlayer player) {
        return true;
    }
}
