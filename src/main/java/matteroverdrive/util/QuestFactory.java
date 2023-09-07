package matteroverdrive.util;


import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import matteroverdrive.MatterOverdrive;
import matteroverdrive.Reference;
import matteroverdrive.api.quest.IQuest;
import matteroverdrive.api.quest.QuestStack;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumChatFormatting;

import java.util.List;
import java.util.Random;

public class QuestFactory {
    public QuestStack generateQuestStack(Random random, IQuest quest) {
        QuestStack questStack = new QuestStack(quest);
        quest.initQuestStack(random, questStack);
        return questStack;
    }

    @SideOnly(Side.CLIENT)
    public String getFormattedQuestObjective(EntityPlayer entityPlayer, QuestStack questStack, int objectiveInex) {
        boolean isCompleted = questStack.isObjectiveCompleted(entityPlayer, objectiveInex);
        if (isCompleted) {
            //completed
            return EnumChatFormatting.GREEN + Reference.UNICODE_COMPLETED_OBJECTIVE + " " + questStack.getObjective(entityPlayer, objectiveInex);
        } else {
            //not completed
            return EnumChatFormatting.DARK_GREEN + Reference.UNICODE_UNCOMPLETED_OBJECTIVE + " " + questStack.getObjective(entityPlayer, objectiveInex);
        }
    }

    @SideOnly(Side.CLIENT)
    public List<String> getFormattedQuestObjective(EntityPlayer entityPlayer, QuestStack questStack, int objectiveInex, int length) {
        List<String> objectiveLines = Minecraft.getMinecraft().fontRenderer.listFormattedStringToWidth(questStack.getObjective(entityPlayer, objectiveInex), length);
        boolean isObjectiveComplete = questStack.isObjectiveCompleted(Minecraft.getMinecraft().thePlayer, objectiveInex);
        for (int o = 0; o < objectiveLines.size(); o++) {
            String line = "";
            if (isObjectiveComplete) {
                line += EnumChatFormatting.GREEN;
                if (o == 0)
                    line += Reference.UNICODE_COMPLETED_OBJECTIVE + " ";
            } else {
                line += EnumChatFormatting.DARK_GREEN;
                if (o == 0)
                    line += Reference.UNICODE_UNCOMPLETED_OBJECTIVE + " ";
            }

            line += objectiveLines.get(o);
            objectiveLines.set(o, line);
        }
        return objectiveLines;
    }

    public QuestStack generateQuestStack(String questName) {
        IQuest quest = MatterOverdrive.quests.getQuestByName(questName);
        if (quest != null) {
            QuestStack questStack = new QuestStack(quest);
            quest.initQuestStack(MatterOverdrive.quests.random, questStack);
            return questStack;
        }
        return null;
    }
}
