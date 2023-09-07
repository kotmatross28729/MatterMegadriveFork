package matteroverdrive.handler;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import matteroverdrive.api.events.MOEventTransport;
import matteroverdrive.api.events.anomaly.MOEventGravitationalAnomalyConsume;
import matteroverdrive.data.quest.PlayerQuestData;
import matteroverdrive.entity.player.AndroidPlayer;
import matteroverdrive.entity.player.MOExtendedProperties;
import matteroverdrive.init.MatterOverdriveItems;
import matteroverdrive.util.MatterHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.event.entity.EntityEvent;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.living.*;
import net.minecraftforge.event.entity.player.EntityItemPickupEvent;

import java.util.EnumSet;

public class EntityHandler {
    @SubscribeEvent
    public void onEntityConstructing(EntityEvent.EntityConstructing event) {
        if (event.entity instanceof EntityPlayer) {
            if (AndroidPlayer.get((EntityPlayer) event.entity) == null) {
                AndroidPlayer.register((EntityPlayer) event.entity);
            }
            if (MOExtendedProperties.get((EntityPlayer) event.entity) == null) {
                MOExtendedProperties.register((EntityPlayer) event.entity);
            }
        }
    }

    @SubscribeEvent
    public void onLivingFallEvent(LivingFallEvent event) {
        if (event.entityLiving instanceof EntityPlayer) {
            AndroidPlayer androidPlayer = AndroidPlayer.get((EntityPlayer) event.entityLiving);
            if (androidPlayer.isAndroid())
                androidPlayer.onEntityFall(event);
        }
    }

    @SubscribeEvent
    public void onEntityJoinWorld(EntityJoinWorldEvent event) {
        if (!event.entity.worldObj.isRemote && event.entity instanceof EntityPlayer) {
            AndroidPlayer.get((EntityPlayer) event.entity).sync(EnumSet.allOf(AndroidPlayer.DataType.class));
            MOExtendedProperties.get((EntityPlayer) event.entity).sync(EnumSet.allOf(PlayerQuestData.DataType.class));
        }
    }

    @SubscribeEvent
    public void onEntityJump(LivingEvent.LivingJumpEvent event) {
        if (event.entityLiving instanceof EntityPlayer) {
            AndroidPlayer androidPlayer = AndroidPlayer.get((EntityPlayer) event.entityLiving);
            if (androidPlayer != null && androidPlayer.isAndroid()) {
                androidPlayer.onEntityJump(event);
                androidPlayer.triggerEventOnStats(event);
            }
        }
    }

    @SubscribeEvent
    public void onPlayerClone(net.minecraftforge.event.entity.player.PlayerEvent.Clone event) {
        AndroidPlayer newAndroidPlayer = AndroidPlayer.get(event.entityPlayer);
        AndroidPlayer oldAndroidPlayer = AndroidPlayer.get(event.original);
        if (newAndroidPlayer != null && oldAndroidPlayer != null) {
            newAndroidPlayer.copy(oldAndroidPlayer);
            if (event.wasDeath) {
                newAndroidPlayer.onPlayerRespawn();
            }
            newAndroidPlayer.sync(EnumSet.allOf(AndroidPlayer.DataType.class));
        }
        MOExtendedProperties newExtendedProperties = MOExtendedProperties.get(event.entityPlayer);
        MOExtendedProperties oldExtenderDProperties = MOExtendedProperties.get(event.original);
        if (newExtendedProperties != null && oldExtenderDProperties != null) {
            newExtendedProperties.copy(oldExtenderDProperties);
            newExtendedProperties.sync(EnumSet.allOf(PlayerQuestData.DataType.class));
        }
    }

    @SubscribeEvent
    public void onEntityAttack(LivingAttackEvent event) {
        if (event.entityLiving instanceof EntityPlayer) {
            AndroidPlayer.get((EntityPlayer) event.entityLiving).triggerEventOnStats(event);
        }
    }

    @SubscribeEvent
    public void onEntityDeath(LivingDeathEvent deathEvent) {
        if (deathEvent.source != null && deathEvent.source.getEntity() instanceof EntityPlayer) {
            MOExtendedProperties extendedProperties = MOExtendedProperties.get((EntityPlayer) deathEvent.source.getEntity());
            extendedProperties.onEvent(deathEvent);
        }
    }

    @SubscribeEvent
    public void onEntityHurt(LivingHurtEvent event) {
        if (event.entityLiving instanceof EntityPlayer) {
            AndroidPlayer androidPlayer = AndroidPlayer.get((EntityPlayer) event.entityLiving);
            if (androidPlayer.isAndroid())
                androidPlayer.onEntityHurt(event);
        }
    }

    @SubscribeEvent
    public void onEntityItemPickup(EntityItemPickupEvent event) {
        if (event.entityPlayer != null) {
            if (event.item.getEntityItem() != null && MatterHelper.containsMatter(event.item.getEntityItem())) {
                for (int i = 0; i < 9; i++) {
                    if (event.entityPlayer.inventory.getStackInSlot(i) != null && event.entityPlayer.inventory.getStackInSlot(i).getItem() == MatterOverdriveItems.portableDecomposer) {
                        MatterOverdriveItems.portableDecomposer.decomposeItem(event.entityPlayer.inventory.getStackInSlot(i), event.item.getEntityItem());
                    }
                }
            }
            MOExtendedProperties extendedProperties = MOExtendedProperties.get(event.entityPlayer);
            if (extendedProperties != null) {
                extendedProperties.onEvent(event);
            }
        }
    }

    @SubscribeEvent
    public void onEntityTransport(MOEventTransport eventTransport) {
        if (eventTransport.entity instanceof EntityPlayer) {
            MOExtendedProperties extendedProperties = MOExtendedProperties.get((EntityPlayer) eventTransport.entity);
            if (extendedProperties != null) {
                extendedProperties.onEvent(eventTransport);
            }
        }
    }

    @SubscribeEvent
    public void onEntityAnomalyConsume(MOEventGravitationalAnomalyConsume.Post event) {
        if (event.entity instanceof EntityPlayer) {
            MOExtendedProperties extendedProperties = MOExtendedProperties.get((EntityPlayer) event.entity);
            if (extendedProperties != null) {
                extendedProperties.onEvent(event);
            }
        }
    }
}
