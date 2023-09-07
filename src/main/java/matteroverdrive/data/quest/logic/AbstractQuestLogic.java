package matteroverdrive.data.quest.logic;

import cpw.mods.fml.common.registry.EntityRegistry;
import matteroverdrive.api.quest.IQuestLogic;
import matteroverdrive.api.quest.QuestStack;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;

import java.util.Random;

public abstract class AbstractQuestLogic implements IQuestLogic {
    protected boolean autoComplete;
    protected String id;

    @Override
    public String modifyTitle(QuestStack questStack, String original) {
        return original;
    }

    @Override
    public boolean canAccept(QuestStack questStack, EntityPlayer entityPlayer) {
        return true;
    }

    @Override
    public int modifyObjectiveCount(QuestStack questStack, EntityPlayer entityPlayer, int count) {
        return count;
    }

    @Override
    public boolean areQuestStacksEqual(QuestStack questStackOne, QuestStack questStackTwo) {
        return true;
    }

    @Override
    public int modifyXP(QuestStack questStack, EntityPlayer entityPlayer, int originalXp) {
        return originalXp;
    }

    public int random(Random random, int min, int max) {
        int randomCount = max - min;
        return min + (randomCount > 0 ? random.nextInt(randomCount) : 0);
    }

    protected String getEntityClassName(Class<? extends Entity> entityClass, String unknownTargetName) {
        if (entityClass != null) {
            EntityRegistry.EntityRegistration entityRegistration = EntityRegistry.instance().lookupModSpawn(entityClass, true);
            if (entityRegistration != null) {
                return entityRegistration.getEntityName();
            } else {
                String name = (String) EntityList.classToStringMapping.get(entityClass);
                if (name != null) {
                    return name;
                }
            }
        }
        return unknownTargetName;
    }

    public AbstractQuestLogic setAutoComplete(boolean autoComplete) {
        this.autoComplete = autoComplete;
        return this;
    }

    @Override
    public String getID() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    protected boolean hasTag(QuestStack questStack) {
        if (getID() == null) {
            return questStack.getTagCompound() != null;
        } else {
            return questStack.getTagCompound() != null && questStack.getTagCompound().hasKey(getID());
        }
    }

    protected void initTag(QuestStack questStack) {
        if (!hasTag(questStack)) {
            if (getID() == null) {
                questStack.setTagCompound(new NBTTagCompound());
            } else {
                NBTTagCompound tagCompound = questStack.getTagCompound();
                if (tagCompound == null)
                    tagCompound = new NBTTagCompound();
                tagCompound.setTag(getID(), new NBTTagCompound());
                questStack.setTagCompound(tagCompound);
            }
        }
    }

    protected NBTTagCompound getTag(QuestStack questStack) {
        if (getID() == null) {
            return questStack.getTagCompound();
        } else {
            return questStack.getTagCompound().getCompoundTag(getID());
        }
    }
}
