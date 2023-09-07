package matteroverdrive.api.events;

import cpw.mods.fml.common.eventhandler.Event;
import matteroverdrive.api.android.IBionicStat;
import matteroverdrive.api.renderer.IBioticStatRenderer;

/**
 * Triggered by special Bionic Stats that have custom renderers, such as the Shield ability.
 */
public class MOEventRegisterAndroidStatRenderer extends Event {
    /**
     * The type of bionic stat that is being rendered.
     */
    public final Class<? extends IBionicStat> statClass;
    /**
     * The Bionic Stat renderer itself.
     */
    public final IBioticStatRenderer renderer;

    public MOEventRegisterAndroidStatRenderer(Class<? extends IBionicStat> statClass, IBioticStatRenderer renderer) {
        this.statClass = statClass;
        this.renderer = renderer;
    }

    public boolean isCancelable() {
        return true;
    }
}
