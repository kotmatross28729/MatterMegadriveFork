package matteroverdrive.dialog;

import matteroverdrive.api.dialog.IDialogNpc;
import net.minecraft.entity.IMerchant;
import net.minecraft.entity.player.EntityPlayer;

public class DialogMessageTrade extends DialogMessageRandom {

    public DialogMessageTrade() {
        super();
    }

    public DialogMessageTrade(String message, String shortMessage) {
        super(message, shortMessage);
    }

    public DialogMessageTrade(String message) {
        super(message);
    }

    @Override
    public void onInteract(IDialogNpc npc, EntityPlayer player) {
        if (!player.worldObj.isRemote && npc.getEntity() instanceof IMerchant) {
            ((IMerchant) npc.getEntity()).setCustomer(player);
            player.displayGUIMerchant((IMerchant) npc, npc.getEntity().getCommandSenderName());
        }
    }
}
