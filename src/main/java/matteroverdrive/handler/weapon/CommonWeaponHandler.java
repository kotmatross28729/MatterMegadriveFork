package matteroverdrive.handler.weapon;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import matteroverdrive.MatterOverdrive;
import matteroverdrive.api.events.weapon.MOEventEnergyWeapon;
import matteroverdrive.entity.player.AndroidPlayer;
import matteroverdrive.network.packet.bi.PacketFirePlasmaShot;
import net.minecraft.entity.player.EntityPlayer;

import java.util.HashMap;
import java.util.Map;

public class CommonWeaponHandler {
    private static final PacketFirePlasmaShot.BiHandler firePlasmaShotHandler = new PacketFirePlasmaShot.BiHandler();
    Map<EntityPlayer, Long> weaponTimestamps;

    public CommonWeaponHandler() {
        weaponTimestamps = new HashMap<>();
    }

    public void addTimestamp(EntityPlayer player, long timestamp) {
        weaponTimestamps.put(player, timestamp);
    }

    public boolean hasTimestamp(EntityPlayer player) {
        return weaponTimestamps.containsKey(player);
    }

    public long getTimestamp(EntityPlayer entityPlayer) {
        return weaponTimestamps.get(entityPlayer);
    }

    public void handlePlasmaShotFire(EntityPlayer entityPlayer, PacketFirePlasmaShot plasmaShot, long timeStamp) {
        int delay = (int) (timeStamp - getTimestamp(entityPlayer));
        firePlasmaShotHandler.handleServerShot(entityPlayer, plasmaShot, delay);
        MatterOverdrive.packetPipeline.sendToAllAround(plasmaShot, entityPlayer, plasmaShot.getShot().getRange() + 64);
    }

    @SubscribeEvent
    public void onEnergyWeaponEvent(MOEventEnergyWeapon eventEnergyWeapon) {
        if (eventEnergyWeapon.entityLiving != null && eventEnergyWeapon.entityLiving instanceof EntityPlayer) {
            AndroidPlayer androidPlayer = AndroidPlayer.get((EntityPlayer) eventEnergyWeapon.entityLiving);
            if (androidPlayer != null) {
                androidPlayer.onWeaponEvent(eventEnergyWeapon);
            }
        }
    }
}
