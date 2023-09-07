package matteroverdrive.data.quest;

import cpw.mods.fml.common.eventhandler.Event;
import matteroverdrive.MatterOverdrive;
import matteroverdrive.api.quest.QuestStack;
import matteroverdrive.entity.player.MOExtendedProperties;
import matteroverdrive.network.packet.client.quest.PacketUpdateQuest;
import matteroverdrive.util.MOLog;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraftforge.common.util.Constants;
import org.apache.logging.log4j.Level;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;

public class PlayerQuestData {
    List<QuestStack> activeQuests;
    List<QuestStack> completedQuests;
    MOExtendedProperties extendedProperties;

    public PlayerQuestData(MOExtendedProperties extendedProperties) {
        activeQuests = new ArrayList<>();
        completedQuests = new ArrayList<>();
        this.extendedProperties = extendedProperties;
    }

    public void writeToNBT(NBTTagCompound tagCompound, EnumSet<DataType> dataTypes) {
        if (dataTypes.contains(DataType.COMPLETED_QUESTS)) {
            if (completedQuests.size() > 0) {
                NBTTagList activeQuestsTagList = new NBTTagList();
                for (QuestStack questStack : completedQuests) {
                    NBTTagCompound questStackNBT = new NBTTagCompound();
                    questStack.writeToNBT(questStackNBT);
                    activeQuestsTagList.appendTag(questStackNBT);
                }
                tagCompound.setTag("CompletedQuests", activeQuestsTagList);
            }
        }
        if (dataTypes.contains(DataType.ACTIVE_QUESTS)) {
            if (activeQuests.size() > 0) {
                NBTTagList activeQuestsTagList = new NBTTagList();
                for (QuestStack questStack : activeQuests) {
                    NBTTagCompound questStackNBT = new NBTTagCompound();
                    questStack.writeToNBT(questStackNBT);
                    activeQuestsTagList.appendTag(questStackNBT);
                }
                tagCompound.setTag("ActiveQuests", activeQuestsTagList);
            }
        }
    }

    public void readFromNBT(NBTTagCompound tagCompound, EnumSet<DataType> dataTypes) {
        if (dataTypes.contains(DataType.COMPLETED_QUESTS)) {
            completedQuests.clear();
            try {
                if (tagCompound.hasKey("CompletedQuests", Constants.NBT.TAG_LIST)) {
                    NBTTagList activeQuestsTagList = tagCompound.getTagList("CompletedQuests", Constants.NBT.TAG_COMPOUND);
                    for (int i = 0; i < activeQuestsTagList.tagCount(); i++) {
                        completedQuests.add(QuestStack.loadFromNBT(activeQuestsTagList.getCompoundTagAt(i)));
                    }
                }
            } catch (Exception e) {
                MOLog.log(Level.ERROR, e, "There was a problem while loading Completed Quests");
            }
        }
        if (dataTypes.contains(DataType.ACTIVE_QUESTS)) {
            activeQuests.clear();
            try {
                if (tagCompound.hasKey("ActiveQuests", Constants.NBT.TAG_LIST)) {
                    NBTTagList activeQuestsTagList = tagCompound.getTagList("ActiveQuests", Constants.NBT.TAG_COMPOUND);
                    for (int i = 0; i < activeQuestsTagList.tagCount(); i++) {
                        activeQuests.add(QuestStack.loadFromNBT(activeQuestsTagList.getCompoundTagAt(i)));
                    }
                }
            } catch (Exception e) {
                MOLog.log(Level.ERROR, e, "There was a problem while loading Active Quests");
            }
        }
    }

    public void manageQuestCompletion() {
        int i = 0;
        while (i < activeQuests.size()) {
            if (activeQuests.get(i).isCompleted()) {
                QuestStack questStack = activeQuests.remove(i);
                extendedProperties.onQuestCompleted(questStack, i);
            } else {
                i++;
            }
        }
    }

    public boolean hasCompletedQuest(QuestStack quest) {
        for (QuestStack q : completedQuests) {
            if (q.getQuest().areQuestStacksEqual(q, quest)) {
                return true;
            }
        }
        return false;
    }

    public boolean hasQuest(QuestStack questStack) {
        for (QuestStack q : activeQuests) {
            if (q.getQuest().areQuestStacksEqual(q, questStack)) {
                return true;
            }
        }
        return false;
    }

    public QuestStack addQuest(QuestStack questStack) {
        if (questStack.getQuest() != null && activeQuests.add(questStack)) {
            return questStack;
        }
        return null;
    }

    public void addQuestToCompleted(QuestStack questStack) {
        if (questStack.getQuest() != null && !completedQuests.contains(questStack)) {
            completedQuests.add(questStack);
        }
    }

    public void onEvent(Event event) {
        if (extendedProperties != null && extendedProperties.getPlayer() != null && extendedProperties.getPlayer() instanceof EntityPlayerMP) {
            for (int i = 0; i < activeQuests.size(); i++) {
                if (activeQuests.get(i).getQuest() != null) {
                    if (activeQuests.get(i).getQuest().onEvent(activeQuests.get(i), event, extendedProperties.getPlayer())) {
                        MatterOverdrive.packetPipeline.sendTo(new PacketUpdateQuest(i, this, PacketUpdateQuest.UPDATE_QUEST), (EntityPlayerMP) extendedProperties.getPlayer());
                    }
                }
            }
        }
    }

    public void clearActiveQuests() {
        activeQuests.clear();
    }

    public void clearCompletedQuests() {
        completedQuests.clear();
    }

    public void removeQuest(QuestStack questStack) {
        activeQuests.remove(questStack);
    }

    public void removeQuest(int id) {
        activeQuests.remove(id);
    }

    public List<QuestStack> getActiveQuests() {
        return activeQuests;
    }

    public List<QuestStack> getCompletedQuests() {
        return completedQuests;
    }

    public enum DataType {
        ACTIVE_QUESTS, COMPLETED_QUESTS
    }
}
