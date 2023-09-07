package matteroverdrive.api.gravity;

/**
 * Entities that implement this interface can disable gravitational effect on them.
 */
public interface IGravityEntity {
    /**
     * @param anomaly
     * @return is the entity affected by the gravitational anomaly.
     */
    boolean isAffectedByAnomaly(IGravitationalAnomaly anomaly);

    /**
     * Called when the entity is consumed by the anomaly.
     *
     * @param anomaly the anomaly
     */
    void onEntityConsumed(IGravitationalAnomaly anomaly);
}
