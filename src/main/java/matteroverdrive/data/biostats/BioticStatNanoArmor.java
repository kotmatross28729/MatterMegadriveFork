package matteroverdrive.data.biostats;

import com.google.common.collect.Multimap;
import matteroverdrive.entity.player.AndroidPlayer;
import net.minecraft.util.EnumChatFormatting;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;

import java.text.DecimalFormat;

public class BioticStatNanoArmor extends AbstractBioticStat {
    public BioticStatNanoArmor(String name, int xp) {
        super(name, xp);
        setMaxLevel(10);
        setShowOnHud(true);
    }

    @Override
    public void onAndroidUpdate(AndroidPlayer android, int level) {

    }

    @Override
    public void onActionKeyPress(AndroidPlayer androidPlayer, int level, boolean server) {

    }

    @Override
    public void onKeyPress(AndroidPlayer androidPlayer, int level, int keycode, boolean down) {

    }

    @Override
    public String getDetails(int level) {
        return String.format(super.getDetails(level), EnumChatFormatting.GREEN + DecimalFormat.getPercentInstance().format(getDamageNegate(level)) + EnumChatFormatting.GRAY);
    }

    @Override
    public void onLivingEvent(AndroidPlayer androidPlayer, int level, LivingEvent event) {
        if (event instanceof LivingHurtEvent) {
            ((LivingHurtEvent) event).ammount *= (1 - getDamageNegate(level));
        }
    }

    @Override
    public void changeAndroidStats(AndroidPlayer androidPlayer, int level, boolean enabled) {

    }

    @Override
    public Multimap attributes(AndroidPlayer androidPlayer, int level) {
        return null;
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

    public float getDamageNegate(int level) {
        return (1 + level) * 0.07f;
    }
}
