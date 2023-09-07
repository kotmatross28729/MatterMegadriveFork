package matteroverdrive.api.events.bionicStats;

import matteroverdrive.api.android.IBionicStat;
import matteroverdrive.entity.player.AndroidPlayer;
import net.minecraftforge.event.entity.player.PlayerEvent;

/**
 * Triggered by most {@link IBionicStat}.
 * For example the {@link matteroverdrive.data.biostats.BioticStatTeleport} triggers the event when used by the player.
 */
public class MOEventBionicStat extends PlayerEvent {
    /**
     * The android player using the ability.
     */
    public final AndroidPlayer android;
    /**
     * The Ability itself.
     */
    public final IBionicStat stat;
    /**
     * The level of the ability being used.
     */
    public final int level;


    public MOEventBionicStat(IBionicStat stat, int level, AndroidPlayer android) {
        super(android.getPlayer());
        this.android = android;
        this.stat = stat;
        this.level = level;
    }

    @Override
    public boolean isCancelable() {
        return true;
    }
}
