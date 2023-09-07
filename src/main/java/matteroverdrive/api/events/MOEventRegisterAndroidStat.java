package matteroverdrive.api.events;

import cpw.mods.fml.common.eventhandler.Event;
import matteroverdrive.api.android.IBionicStat;

/**
 * Triggered when an Android Ability is registered.
 * When canceled, the ability will not be registered.
 */
public class MOEventRegisterAndroidStat extends Event {
    /**
     * The Bionic Stat to be registered
     */
    public final IBionicStat stat;

    public MOEventRegisterAndroidStat(IBionicStat stat) {
        this.stat = stat;
    }

    public boolean isCancelable() {
        return true;
    }
}
