package matteroverdrive.data.biostats;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import matteroverdrive.data.MOAttributeModifier;
import matteroverdrive.entity.player.AndroidPlayer;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.util.EnumChatFormatting;
import net.minecraftforge.event.entity.living.LivingEvent;

import java.util.UUID;

public class BioticStatSpeed extends AbstractBioticStat {
    UUID modifierID;

    public BioticStatSpeed(String name, int xp) {
        super(name, xp);
        setMaxLevel(5);
        modifierID = UUID.fromString("d13345c8-14f7-48fd-bc52-c787c9857a6c");
    }

    @Override
    public void onAndroidUpdate(AndroidPlayer android, int level) {

    }

    public float getSpeedModify(int level) {
        return level * 0.1f;
    }

    public String getDetails(int level) {
        // Base speed of 100%, plus 10% per level.
        Integer nextMovementSpeed = 100 + (20 * (level + 1));
        return String.format(super.getDetails(level), EnumChatFormatting.GREEN + Integer.toString(nextMovementSpeed) + "%" + EnumChatFormatting.GRAY);
    }

    @Override
    public void onActionKeyPress(AndroidPlayer androidPlayer, int level, boolean server) {

    }

    @Override
    public void onKeyPress(AndroidPlayer androidPlayer, int level, int keycode, boolean down) {

    }

    @Override
    public void onLivingEvent(AndroidPlayer androidPlayer, int level, LivingEvent event) {

    }

    @Override
    public void changeAndroidStats(AndroidPlayer androidPlayer, int level, boolean enabled) {

    }

    @Override
    public Multimap attributes(AndroidPlayer androidPlayer, int level) {
        Multimap multimap = HashMultimap.create();
        multimap.put(SharedMonsterAttributes.movementSpeed.getAttributeUnlocalizedName(), new MOAttributeModifier(modifierID, "Android Speed", getSpeedModify(level), 2).setSaved(false));
        return multimap;
    }

    @Override
    public boolean isEnabled(AndroidPlayer android, int level) {
        return super.isEnabled(android, level) && android.getEnergyStored() > 0;
    }

    @Override
    public boolean isActive(AndroidPlayer androidPlayer, int level) {
        return false;
    }

    @Override
    public int getDelay(AndroidPlayer androidPlayer, int level) {
        return 0;
    }
}
