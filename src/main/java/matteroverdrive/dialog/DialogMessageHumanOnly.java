package matteroverdrive.dialog;

import matteroverdrive.api.dialog.IDialogNpc;
import matteroverdrive.entity.player.AndroidPlayer;
import net.minecraft.entity.player.EntityPlayer;

public class DialogMessageHumanOnly extends DialogMessageRandom {
    public DialogMessageHumanOnly() {
        super();
    }

    public DialogMessageHumanOnly(String message) {
        super(message);
    }

    public DialogMessageHumanOnly(String message, String question) {
        super(message, question);
    }

    @Override
    public boolean isVisible(IDialogNpc npc, EntityPlayer player) {
        return AndroidPlayer.get(player) == null || !AndroidPlayer.get(player).isAndroid();
    }
}
