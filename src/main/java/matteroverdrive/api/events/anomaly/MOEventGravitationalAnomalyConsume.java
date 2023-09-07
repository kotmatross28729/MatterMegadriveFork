package matteroverdrive.api.events.anomaly;

import cpw.mods.fml.common.eventhandler.Event;
import net.minecraft.entity.Entity;

/**
 * Triggered when a {@link matteroverdrive.tile.TileEntityGravitationalAnomaly} consumes an entity or an item.
 */
public class MOEventGravitationalAnomalyConsume extends Event {
    /**
     * The entity being consumed.
     */
    public final Entity entity;
    /**
     * The X coordinate of the anomaly.
     */
    public final int anomalyX;
    /**
     * The Y coordinate of the anomaly.
     */
    public final int anomalyY;
    /**
     * The Z coordinate of the anomaly.
     */
    public final int anomalyZ;

    public MOEventGravitationalAnomalyConsume(Entity entity, int x, int y, int z) {
        this.entity = entity;
        this.anomalyX = x;
        this.anomalyY = y;
        this.anomalyZ = z;
    }

    public static class Pre extends MOEventGravitationalAnomalyConsume {
        public Pre(Entity entity, int x, int y, int z) {
            super(entity, x, y, z);
        }

        @Override
        public boolean isCancelable() {
            return true;
        }
    }

    public static class Post extends MOEventGravitationalAnomalyConsume {
        public Post(Entity entity, int x, int y, int z) {
            super(entity, x, y, z);
        }
    }
}
