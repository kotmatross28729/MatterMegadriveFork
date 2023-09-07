package matteroverdrive.dialog;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import matteroverdrive.api.dialog.IDialogNpc;
import matteroverdrive.api.dialog.IDialogQuestGiver;
import matteroverdrive.api.quest.QuestStack;
import matteroverdrive.gui.GuiDialog;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;

public class DialogMessageQuestGive extends DialogMessage {
    QuestStack questStack;
    boolean returnToMain;

    public DialogMessageQuestGive() {
        super();
    }

    public DialogMessageQuestGive(QuestStack questStack) {
        super();
        this.questStack = questStack;
    }

    @Override
    public void onInteract(IDialogNpc npc, EntityPlayer player) {
        super.onInteract(npc, player);
        if (npc != null && npc instanceof IDialogQuestGiver && player != null && !player.worldObj.isRemote) {
            ((IDialogQuestGiver) npc).giveQuest(this, questStack, player);
        }
    }

    @Override
    @SideOnly(Side.CLIENT)
    protected void setAsGuiActiveMessage(IDialogNpc npc, EntityPlayer player) {
        if (Minecraft.getMinecraft().currentScreen instanceof GuiDialog) {
            ((GuiDialog) Minecraft.getMinecraft().currentScreen).setCurrentMessage(returnToMain ? npc.getStartDialogMessage(player) : this);
        }
    }

    public DialogMessageQuestGive setReturnToMain(boolean returnToMain) {
        this.returnToMain = returnToMain;
        return this;
    }
}
