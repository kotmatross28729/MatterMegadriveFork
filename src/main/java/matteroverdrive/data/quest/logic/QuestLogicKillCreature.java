package matteroverdrive.data.quest.logic;

import cpw.mods.fml.common.eventhandler.Event;
import matteroverdrive.api.quest.IQuestReward;
import matteroverdrive.api.quest.QuestStack;
import matteroverdrive.entity.player.MOExtendedProperties;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.entity.living.LivingDeathEvent;

import java.util.List;
import java.util.Random;

public class QuestLogicKillCreature extends AbstractQuestLogic {
    String regex;
    ItemStack killWithItemStack;
    Item killWithItem;
    boolean explosionOnly;
    boolean burnOnly;
    boolean shootOnly;
    boolean onlyChildren;
    int minKillCount;
    int maxKillCount;
    int xpPerKill;
    Class<? extends EntityLivingBase>[] creatureClasses;

    public QuestLogicKillCreature(Class<? extends EntityLivingBase> creatureClass, int minKillCount, int maxKillCount, int xpPerKill) {
        this(new Class[]{creatureClass}, minKillCount, maxKillCount, xpPerKill);
    }

    public QuestLogicKillCreature(Class<? extends EntityLivingBase>[] creatureClasses, int minKillCount, int maxKillCount, int xpPerKill) {
        this.creatureClasses = creatureClasses;
        this.minKillCount = minKillCount;
        this.maxKillCount = maxKillCount;
        this.xpPerKill = xpPerKill;
    }

    @Override
    public String modifyInfo(QuestStack questStack, String info) {
        return String.format(info, "", Integer.toString(getMaxKillCount(questStack)), getTargetName(questStack));
    }

    @Override
    public boolean isObjectiveCompleted(QuestStack questStack, EntityPlayer entityPlayer, int objectiveIndex) {
        return getKillCount(questStack) >= getMaxKillCount(questStack);
    }

    @Override
    public String modifyObjective(QuestStack questStack, EntityPlayer entityPlayer, String objetive, int objectiveIndex) {
        return String.format(objetive, "", getKillCount(questStack), getMaxKillCount(questStack), getTargetName(questStack));
    }

    @Override
    public void initQuestStack(Random random, QuestStack questStack) {
        initTag(questStack);
        getTag(questStack).setInteger("MaxKillCount", random(random, minKillCount, maxKillCount));
        getTag(questStack).setByte("KillType", (byte) random.nextInt(creatureClasses.length));
    }

    @Override
    public boolean onEvent(QuestStack questStack, Event event, EntityPlayer entityPlayer) {
        if (event instanceof LivingDeathEvent) {
            LivingDeathEvent deathEvent = (LivingDeathEvent) event;
            Class targetClass = creatureClasses[getKillType(questStack)];
            if (deathEvent.entityLiving != null && targetClass.isInstance(deathEvent.entityLiving)) {
                if (regex != null && !isTargetNameValid(((LivingDeathEvent) event).entity))
                    return false;
                if (shootOnly && !((LivingDeathEvent) event).source.isProjectile())
                    return false;
                if (burnOnly && !((LivingDeathEvent) event).source.isFireDamage())
                    return false;
                if (explosionOnly && !((LivingDeathEvent) event).source.isExplosion())
                    return false;
                if (killWithItem != null && (entityPlayer.getHeldItem() == null || entityPlayer.getHeldItem().getItem() != killWithItem))
                    return false;
                if (killWithItemStack != null && (entityPlayer.getHeldItem() == null || !ItemStack.areItemStacksEqual(entityPlayer.getHeldItem(), killWithItemStack)))
                    return false;
                if (onlyChildren && !((LivingDeathEvent) event).entityLiving.isChild())
                    return false;

                initTag(questStack);
                int currentKillCount = getKillCount(questStack);
                if (currentKillCount < getMaxKillCount(questStack)) {
                    setKillCount(questStack, ++currentKillCount);
                    if (isObjectiveCompleted(questStack, entityPlayer, 0) && autoComplete) {
                        MOExtendedProperties extendedProperties = MOExtendedProperties.get(entityPlayer);
                        if (extendedProperties != null) {
                            questStack.markComplited(entityPlayer, false);
                        }
                    }

                    return true;
                }
            }
        }
        return false;
    }

    protected boolean isTargetNameValid(Entity entity) {
        if (entity instanceof EntityLiving && ((EntityLiving) entity).hasCustomNameTag()) {
            if (!((EntityLiving) entity).getCustomNameTag().matches(regex)) {
                return false;
            }
        } else {
            if (!entity.getCommandSenderName().matches(regex)) {
                return false;
            }
        }
        return true;
    }

    @Override
    public void onTaken(QuestStack questStack, EntityPlayer entityPlayer) {

    }

    public int getKillType(QuestStack questStack) {
        if (hasTag(questStack)) {
            return getTag(questStack).getByte("KillType");
        }
        return 0;
    }

    public int getMaxKillCount(QuestStack questStack) {
        if (hasTag(questStack))
            return getTag(questStack).getInteger("MaxKillCount");
        return 0;
    }

    public int getKillCount(QuestStack questStack) {
        if (hasTag(questStack))
            return getTag(questStack).getInteger("KillCount");
        return 0;
    }

    public void setKillCount(QuestStack questStack, int killCount) {
        initTag(questStack);
        getTag(questStack).setInteger("KillCount", killCount);
    }

    public String getTargetName(QuestStack questStack) {
        return getEntityClassName(creatureClasses[getKillType(questStack)], "Unknown Target");
    }

    public Class<? extends EntityLivingBase>[] getCreatureClasses() {
        return creatureClasses;
    }

    @Override
    public void onCompleted(QuestStack questStack, EntityPlayer entityPlayer) {

    }

    @Override
    public void modifyRewards(QuestStack questStack, EntityPlayer entityPlayer, List<IQuestReward> rewards) {

    }

    @Override
    public int modifyXP(QuestStack questStack, EntityPlayer entityPlayer, int originalXp) {

        return originalXp + xpPerKill * getMaxKillCount(questStack);
    }

    public QuestLogicKillCreature setOnlyChildren(boolean onlyChildren) {
        this.onlyChildren = onlyChildren;
        return this;
    }

    public QuestLogicKillCreature setShootOnly(boolean shootOnly) {
        this.shootOnly = shootOnly;
        return this;
    }

    public QuestLogicKillCreature setBurnOnly(boolean burnOnly) {
        this.burnOnly = burnOnly;
        return this;
    }

    public QuestLogicKillCreature setExplosionOnly(boolean explosionOnly) {
        this.explosionOnly = explosionOnly;
        return this;
    }

    public void setNameRegex(String regex) {
        this.regex = regex;
    }
}
