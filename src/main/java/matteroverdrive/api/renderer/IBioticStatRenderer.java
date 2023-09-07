package matteroverdrive.api.renderer;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import matteroverdrive.api.android.IBionicStat;
import net.minecraftforge.client.event.RenderWorldLastEvent;

/**
 * Used by bionic stats (android abilities) to render special stats.
 * One example is the Shield Ability {@link matteroverdrive.data.biostats.BioticStatShield} uses a renderer to render it's shield.
 * This is used in the {@link matteroverdrive.api.android.IAndroidStatRenderRegistry}.
 */
@SideOnly(Side.CLIENT)
public interface IBioticStatRenderer<T extends IBionicStat> {
    /**
     * This method is called to render the stat.
     * It is called when rendering the world.
     *
     * @param stat  the bionic stat (android ability) being rendered.
     * @param level the unlocked level of the stat/ability.
     * @param event the world render event. This event holds useful information such as the partial render ticks.
     */
    void onWorldRender(T stat, int level, RenderWorldLastEvent event);
}
