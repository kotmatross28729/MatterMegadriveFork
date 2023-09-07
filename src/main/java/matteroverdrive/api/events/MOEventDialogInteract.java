package matteroverdrive.api.events;

import matteroverdrive.api.dialog.IDialogMessage;
import matteroverdrive.api.dialog.IDialogNpc;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.event.entity.player.PlayerEvent;

public class MOEventDialogInteract extends PlayerEvent {
    public final IDialogNpc npc;
    public final IDialogMessage dialogMessage;

    public MOEventDialogInteract(EntityPlayer player, IDialogNpc npc, IDialogMessage dialogMessage) {
        super(player);
        this.npc = npc;
        this.dialogMessage = dialogMessage;
    }
}
