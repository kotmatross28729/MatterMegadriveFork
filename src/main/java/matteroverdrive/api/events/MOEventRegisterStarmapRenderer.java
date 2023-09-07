package matteroverdrive.api.events;

import cpw.mods.fml.common.eventhandler.Event;
import matteroverdrive.api.renderer.ISpaceBodyHoloRenderer;
import matteroverdrive.starmap.data.SpaceBody;

/**
 * Triggered when a Starmap zoom renderer is being rendered.
 */
public class MOEventRegisterStarmapRenderer extends Event {
    /**
     * The type of space body being displayed.
     */
    public final Class<? extends SpaceBody> spaceBodyType;
    /**
     * The Space body render itself.
     */
    public final ISpaceBodyHoloRenderer renderer;

    public MOEventRegisterStarmapRenderer(Class<? extends SpaceBody> spaceBodyType, ISpaceBodyHoloRenderer renderer) {
        this.spaceBodyType = spaceBodyType;
        this.renderer = renderer;
    }

    @Override
    public boolean isCancelable() {
        return true;
    }
}
