package matteroverdrive.api.machines;

import matteroverdrive.api.inventory.UpgradeTypes;

/**
 * Used by machines to affect the upgrade amount from Machine Upgrades.
 * Used mainly to check if speed or some other Machine Stat doesn't reach 0 as in the implementation {@link matteroverdrive.machines.UpgradeHandlerMinimum}.
 *
 * @see matteroverdrive.machines.UpgradeHandlerMinimum
 */
public interface IUpgradeHandler {
    /**
     * Called when a specified Upgrade Type is changed.
     *
     * @param type     The Upgrade Type being changed.
     * @param multiply The multiply amount of all the upgrades that change the given Upgrade Type
     * @return The affected and handled multiply of the original Multiply parameter.
     */
    double affectUpgrade(UpgradeTypes type, double multiply);
}
