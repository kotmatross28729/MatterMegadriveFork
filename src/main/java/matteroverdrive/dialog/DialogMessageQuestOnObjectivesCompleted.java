package matteroverdrive.dialog;

import matteroverdrive.api.dialog.IDialogNpc;
import matteroverdrive.api.quest.QuestStack;
import matteroverdrive.entity.player.MOExtendedProperties;
import net.minecraft.entity.player.EntityPlayer;

public class DialogMessageQuestOnObjectivesCompleted extends DialogMessage {
    QuestStack questStack;
    int[] completedObjectives;

    public DialogMessageQuestOnObjectivesCompleted() {
        super();
    }

    public DialogMessageQuestOnObjectivesCompleted(QuestStack questStack, int[] completedObjectives) {
        super();
        this.questStack = questStack;
        this.completedObjectives = completedObjectives;
    }

    @Override
    public boolean isVisible(IDialogNpc npc, EntityPlayer player) {
        MOExtendedProperties extendedProperties = MOExtendedProperties.get(player);
        if (extendedProperties != null) {
            for (QuestStack questStack : extendedProperties.getQuestData().getActiveQuests()) {
                if (questStack.getQuest().areQuestStacksEqual(questStack, this.questStack)) {
                    for (int i = 0; i < completedObjectives.length; i++) {
                        if (!questStack.isObjectiveCompleted(player, completedObjectives[i])) {
                            return false;
                        }
                    }
                    return true;
                }
            }
        }
        return false;
    }

    public void setQuest(QuestStack questStack) {
        this.questStack = questStack;
    }

    public void setCompletedObjectives(int[] completedObjectives) {
        this.completedObjectives = completedObjectives;
    }
}
