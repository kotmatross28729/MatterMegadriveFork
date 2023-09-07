package matteroverdrive.dialog;

import matteroverdrive.api.dialog.IDialogMessage;
import matteroverdrive.api.dialog.IDialogRegistry;

import java.util.HashMap;
import java.util.Map;

public class DialogRegistry implements IDialogRegistry {
    int nextMessageID = 0;
    Map<Integer, IDialogMessage> messageMap;
    Map<IDialogMessage, Integer> messageIntegerMap;

    public DialogRegistry() {
        messageMap = new HashMap<>();
        messageIntegerMap = new HashMap<>();
    }

    public IDialogMessage getMessage(int id) {
        return messageMap.get(id);
    }

    public int getMessageId(IDialogMessage dialogMessage) {
        Integer id = messageIntegerMap.get(dialogMessage);
        if (id == null)
            return -1;
        else return id;
    }

    public void registerMessage(IDialogMessage message) {
        messageMap.put(nextMessageID, message);
        messageIntegerMap.put(message, nextMessageID);
        nextMessageID++;
    }
}
