package matteroverdrive.api.dialog;

/**
 * Used by Dialog Messages that need a new random options each time the conversation has started.
 */
public interface IDialogMessageSeedable {
    /**
     * Sets the seed.
     *
     * @param seed The seed.
     */
    void setSeed(long seed);
}
