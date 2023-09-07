package matteroverdrive.data.biostats;

import com.google.common.collect.Multimap;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import matteroverdrive.Reference;
import matteroverdrive.api.events.bionicStats.MOEventBionicStat;
import matteroverdrive.client.sound.MOPositionedSound;
import matteroverdrive.entity.player.AndroidPlayer;
import matteroverdrive.handler.ConfigurationHandler;
import matteroverdrive.util.IConfigSubscriber;
import matteroverdrive.util.MOEnergyHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.ISound;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.LivingEvent;

import java.util.EnumSet;

public class BioticStatNightVision extends AbstractBioticStat implements IConfigSubscriber {
    public static int ENERGY_PER_TICK = 32;

    public BioticStatNightVision(String name, int xp) {
        super(name, xp);
        setShowOnWheel(true);
        setShowOnHud(true);
    }

    @Override
    public String getDetails(int level) {
        return String.format(super.getDetails(level), EnumChatFormatting.YELLOW.toString() + ENERGY_PER_TICK + MOEnergyHelper.ENERGY_UNIT);
    }

    @Override
    public void onAndroidUpdate(AndroidPlayer android, int level) {
        if (!android.getPlayer().worldObj.isRemote) {
            if (isActive(android, level)) {

            }
        } else {
            manageNightvision(android, level);
        }
    }

    @SideOnly(Side.CLIENT)
    private void manageNightvision(AndroidPlayer android, int level) {
        if (isActive(android, level)) {
            android.getPlayer().addPotionEffect(new PotionEffect(Potion.nightVision.id, 500));
        }
    }

    public void setActive(AndroidPlayer androidPlayer, int level, boolean active) {
        androidPlayer.getPlayer().addPotionEffect(new PotionEffect(Potion.nightVision.id, 500));
        androidPlayer.getEffects().setBoolean("Nightvision", active);
        androidPlayer.sync(EnumSet.of(AndroidPlayer.DataType.EFFECTS), true);
    }

    @Override
    public void onActionKeyPress(AndroidPlayer android, int level, boolean server) {
        if (this.equals(android.getActiveStat())) {
            if (server) {
                if (!MinecraftForge.EVENT_BUS.post(new MOEventBionicStat(this, level, android))) {
                    setActive(android, level, !android.getEffects().getBoolean("Nightvision"));
                }
            } else {
                if (!MinecraftForge.EVENT_BUS.post(new MOEventBionicStat(this, level, android))) {
                    playSound(android);
                }
            }
        }
    }

    @SideOnly(Side.CLIENT)
    protected void playSound(AndroidPlayer android) {
        if (!android.getEffects().getBoolean("Nightvision")) {
            MOPositionedSound sound = new MOPositionedSound(new ResourceLocation(Reference.MOD_ID + ":night_vision"), 0.05f + android.getPlayer().getRNG().nextFloat() * 0.1f, 0.95f + android.getPlayer().getRNG().nextFloat() * 0.1f);
            sound.setAttenuationType(ISound.AttenuationType.NONE);
            Minecraft.getMinecraft().getSoundHandler().playSound(sound);
        } else {
            Minecraft.getMinecraft().getSoundHandler().playSound(new MOPositionedSound(new ResourceLocation(Reference.MOD_ID + ":power_down"), 0.05f + android.getPlayer().getRNG().nextFloat() * 0.1f, 0.95f + android.getPlayer().getRNG().nextFloat() * 0.1f).setAttenuationType(ISound.AttenuationType.NONE));
        }
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void onKeyPress(AndroidPlayer androidPlayer, int level, int keycode, boolean down) {

    }

    @Override
    public void onLivingEvent(AndroidPlayer androidPlayer, int level, LivingEvent event) {

    }

    @Override
    public void changeAndroidStats(AndroidPlayer androidPlayer, int level, boolean enabled) {
        if (!isEnabled(androidPlayer, level) && isActive(androidPlayer, level)) {
            setActive(androidPlayer, level, false);
        }
    }

    @Override
    public Multimap attributes(AndroidPlayer androidPlayer, int level) {
        return null;
    }

    @Override
    public boolean isActive(AndroidPlayer androidPlayer, int level) {
        return androidPlayer.getEffects().getBoolean("Nightvision");
    }

    @Override
    public int getDelay(AndroidPlayer androidPlayer, int level) {
        return 0;
    }

    @Override
    public boolean isEnabled(AndroidPlayer androidPlayer, int level) {
        return super.isEnabled(androidPlayer, level) && androidPlayer.hasEnoughEnergyScaled(ENERGY_PER_TICK);
    }

    @Override
    public void onConfigChanged(ConfigurationHandler config) {
        ENERGY_PER_TICK = config.getInt("nighvision_energy_per_tick", ConfigurationHandler.CATEGORY_ABILITIES, 16, "The energy cost of the Nightvision");
    }
}
