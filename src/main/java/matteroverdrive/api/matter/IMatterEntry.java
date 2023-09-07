package matteroverdrive.api.matter;

/**
 * All matter values for items stored in instances of this class.
 * Used in the {@link IMatterRegistry} to store matter values on items.
 */
public interface IMatterEntry {
    /**
     * The amount of matter the entry is composed of.
     *
     * @return The matter amount of the entry.
     */
    int getMatter();

    /**
     * Sets the matter amount of the entry.
     *
     * @param matter
     */
    void setMatter(int matter);

    /**
     * @return the name (key) of the matter entry
     */
    String getName();

    /**
     * Is the matter entry calculated or added by configs or by Matter Megadrive.
     * Used to show if the matter entry was created by the automatic recipe calculation process.
     */
    boolean getCalculated();
}
