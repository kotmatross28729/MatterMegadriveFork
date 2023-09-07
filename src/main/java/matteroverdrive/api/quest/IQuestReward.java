package matteroverdrive.api.quest;

import net.minecraft.entity.player.EntityPlayer;

public interface IQuestReward {
    void giveReward(QuestStack questStack, EntityPlayer entityPlayer);
}
