package matteroverdrive.api.quest;

import cpw.mods.fml.common.eventhandler.Event;
import net.minecraft.entity.player.EntityPlayer;

import java.util.List;
import java.util.Random;

public interface IQuestLogic {
    String modifyTitle(QuestStack questStack, String original);

    boolean canAccept(QuestStack questStack, EntityPlayer entityPlayer);

    String modifyInfo(QuestStack questStack, String info);

    boolean isObjectiveCompleted(QuestStack questStack, EntityPlayer entityPlayer, int objectiveIndex);

    String modifyObjective(QuestStack questStack, EntityPlayer entityPlayer, String objective, int objectiveIndex);

    int modifyObjectiveCount(QuestStack questStack, EntityPlayer entityPlayer, int count);

    void initQuestStack(Random random, QuestStack questStack);

    boolean onEvent(QuestStack questStack, Event event, EntityPlayer entityPlayer);

    boolean areQuestStacksEqual(QuestStack questStackOne, QuestStack questStackTwo);

    void onTaken(QuestStack questStack, EntityPlayer entityPlayer);

    void onCompleted(QuestStack questStack, EntityPlayer entityPlayer);

    int modifyXP(QuestStack questStack, EntityPlayer entityPlayer, int originalXp);

    void modifyRewards(QuestStack questStack, EntityPlayer entityPlayer, List<IQuestReward> rewards);

    String getID();
}
