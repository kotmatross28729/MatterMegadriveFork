package matteroverdrive.data.biostats;

import com.google.common.collect.Multimap;
import matteroverdrive.api.events.bionicStats.MOEventBionicStat;
import matteroverdrive.entity.player.AndroidPlayer;
import matteroverdrive.handler.ConfigurationHandler;
import matteroverdrive.util.IConfigSubscriber;
import net.minecraft.util.EnumChatFormatting;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.LivingEvent;

public class BioticStatHighJump extends AbstractBioticStat implements IConfigSubscriber {

    public static int ENERGY_PER_JUMP = 1024;

    public BioticStatHighJump(String name, int xp) {
        super(name, xp);
        setMaxLevel(10);
        setShowOnHud(true);
    }

    @Override
    public void onAndroidUpdate(AndroidPlayer android, int level) {

    }

    @Override
    public String getDetails(int level) {
        return String.format(super.getDetails(level), EnumChatFormatting.YELLOW.toString() + ENERGY_PER_JUMP + " RF" + EnumChatFormatting.GRAY);
    }

    @Override
    public void onActionKeyPress(AndroidPlayer androidPlayer, int level, boolean server) {

    }

    @Override
    public void onKeyPress(AndroidPlayer androidPlayer, int level, int keycode, boolean down) {

    }

    @Override
    public void onLivingEvent(AndroidPlayer androidPlayer, int level, LivingEvent event) {
        if (event instanceof LivingEvent.LivingJumpEvent) {
            if (!MinecraftForge.EVENT_BUS.post(new MOEventBionicStat(this, level, androidPlayer))) {
                if (!event.entity.worldObj.isRemote)
                    androidPlayer.extractEnergyScaled(ENERGY_PER_JUMP);

                event.entityLiving.addVelocity(0, 0.5, 0);
            }
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
        return super.isEnabled(android, level) && android.hasEnoughEnergyScaled(ENERGY_PER_JUMP);
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
        ENERGY_PER_JUMP = config.getInt("high_jump_energy", ConfigurationHandler.CATEGORY_ABILITIES, 1024, "The energy cost of each High Jump");
    }
}
