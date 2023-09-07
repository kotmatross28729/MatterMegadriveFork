package matteroverdrive.data.biostats;

import com.google.common.collect.Multimap;
import matteroverdrive.entity.player.AndroidPlayer;
import matteroverdrive.handler.ConfigurationHandler;
import matteroverdrive.util.IConfigSubscriber;
import net.minecraftforge.event.entity.living.LivingEvent;

public class BiostatNanobots extends AbstractBioticStat implements IConfigSubscriber {
    public final static float REGEN_AMOUNT_PER_TICK = 0.03f;
    public static int ENERGY_PER_REGEN = 32;

    public BiostatNanobots(String name, int xp) {
        super(name, xp);
        setShowOnHud(true);
    }

    @Override
    public void onAndroidUpdate(AndroidPlayer android, int level) {
        if (android.getPlayer().worldObj.getWorldTime() % 20 == 0) {
            if (android.getPlayer().getHealth() > 0 && !android.getPlayer().isDead && android.getPlayer().getHealth() < android.getPlayer().getMaxHealth() && android.hasEnoughEnergyScaled(ENERGY_PER_REGEN)) {
                android.getPlayer().heal(REGEN_AMOUNT_PER_TICK * 35);
                android.extractEnergyScaled(ENERGY_PER_REGEN * 35);
            }
        }
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

    @Override
    public void onConfigChanged(ConfigurationHandler config) {
        ENERGY_PER_REGEN = config.getInt("heal_energy_per_regen", ConfigurationHandler.CATEGORY_ABILITIES, 40, "The energy cost of each heal by the Nanobots ability");
    }
}
