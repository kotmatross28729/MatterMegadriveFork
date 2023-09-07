package matteroverdrive.util;

import matteroverdrive.api.dialog.IDialogNpc;
import matteroverdrive.api.dialog.IDialogRegistry;
import matteroverdrive.client.render.conversation.DialogShot;
import matteroverdrive.dialog.DialogMessage;
import net.minecraft.entity.player.EntityPlayer;

public class DialogFactory {
    private IDialogRegistry registry;

    public DialogFactory(IDialogRegistry registry) {
        this.registry = registry;
    }

    public DialogMessage[] constructMultipleLineDialog(Class<? extends DialogMessage> mainMessageType, String unlocalizedName, int lines, String nextLineQuestion) {

        DialogMessage[] messages = new DialogMessage[lines];
        try {
            messages[0] = mainMessageType.newInstance();
        } catch (InstantiationException e) {
            messages[0] = new DialogMessage();
        } catch (IllegalAccessException e) {
            messages[0] = new DialogMessage();
        } finally {
            registry.registerMessage(messages[0]);
        }
        messages[0].loadMessageFromLocalization(String.format("%s.%s.line", unlocalizedName, 0));
        messages[0].loadQuestionFromLocalization(unlocalizedName + ".question");

        DialogMessage lastChild = messages[0];
        for (int i = 1; i < lines; i++) {
            DialogMessage child = new DialogMessage("", nextLineQuestion);
            registry.registerMessage(child);
            child.loadMessageFromLocalization(String.format("%s.%s.line", unlocalizedName, i));
            if (MOStringHelper.hasTranslation(String.format("%s.%s.question", unlocalizedName, i))) {
                child.loadQuestionFromLocalization(String.format("%s.%s.question", unlocalizedName, i));
            }
            child.setParent(lastChild);
            lastChild.addOption(child);
            lastChild = child;
            messages[i] = child;
        }

        return messages;
    }

    public DialogMessage addOnlyVisibleOptions(EntityPlayer entityPlayer, IDialogNpc dialogNpc, DialogMessage parent, DialogMessage... options) {
        for (DialogMessage option : options) {
            if (option.isVisible(dialogNpc, entityPlayer)) {
                parent.addOption(option);
            }
        }
        return parent;
    }

    public void addRandomShots(DialogMessage dialogMessage) {
        dialogMessage.setShots(DialogShot.closeUp, DialogShot.dramaticCloseUp, DialogShot.wideNormal, DialogShot.wideOpposite, DialogShot.fromBehindLeftClose, DialogShot.fromBehindLeftFar, DialogShot.fromBehindRightClose, DialogShot.fromBehindRightFar);
    }
}
