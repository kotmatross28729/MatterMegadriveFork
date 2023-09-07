package matteroverdrive.api.events;

import cpw.mods.fml.common.eventhandler.Event;
import matteroverdrive.api.matter.IMatterEntry;

/**
 * Triggered when registering matter entries in the Matter Registry.
 * When canceled, the entry will not be registered.
 * This is a good in-game way to remove matter for items, even when the configs have custom values.
 */
public class MOEventRegisterMatterEntry extends Event {
    /**
     * The matter entry being registered.
     */
    public final IMatterEntry entry;

    public MOEventRegisterMatterEntry(IMatterEntry entry) {
        this.entry = entry;
    }

    @Override
    public boolean isCancelable() {
        return true;
    }
}
