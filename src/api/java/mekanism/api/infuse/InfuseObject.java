package mekanism.api.infuse;

/**
 * InfuseObject - an object associated with an ItemStack that can modify a Metallurgic Infuser's internal infuse.
 */
public class InfuseObject {
    /**
     * The type of infuse this item stores
     */
    public InfuseType type;

    /**
     * How much infuse this item stores
     */
    public int stored;

    public InfuseObject(InfuseType infusion, int i) {
        type = infusion;
        stored = i;
    }
}
