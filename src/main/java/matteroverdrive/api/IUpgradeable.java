package matteroverdrive.api;

import matteroverdrive.api.inventory.UpgradeTypes;
import matteroverdrive.api.machines.IUpgradeHandler;

/**
 * Implemented by Machines that can be upgraded.
 */
public interface IUpgradeable {
    /**
     * Is the machine affected by curtain upgrade type.
     *
     * @param type the type of upgrade.
     * @return can the type affect the machine.
     */
    boolean isAffectedByUpgrade(UpgradeTypes type);

    /**
     * Gets the upgrade handler for the machine.
     * Used to control how upgrades are affected in machine.
     *
     * @return the upgrade handler.
     */
    IUpgradeHandler getUpgradeHandler();
}
