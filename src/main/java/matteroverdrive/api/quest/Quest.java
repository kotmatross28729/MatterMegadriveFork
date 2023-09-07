package matteroverdrive.api.quest;

import net.minecraft.entity.player.EntityPlayer;

import java.util.ArrayList;
import java.util.List;

public abstract class Quest implements IQuest {
    protected String title;
    protected int xpReward;
    protected List<IQuestReward> questRewards;

    public Quest(String title, int xpReward) {
        this.title = title;
        this.xpReward = xpReward;
        this.questRewards = new ArrayList<>();
    }

    public String getTitle(QuestStack questStack) {
        return title;
    }

    public String getTitle(QuestStack questStack, EntityPlayer entityPlayer) {
        return getTitle(questStack);
    }

    public Quest addQuestRewards(IQuestReward... questRewards) {
        for (IQuestReward itemStack : questRewards) {
            this.questRewards.add(itemStack);
        }
        return this;
    }

    @Override
    public void setCompleted(QuestStack questStack, EntityPlayer entityPlayer) {
        questStack.completed = true;
    }
}
