package matteroverdrive.machines;

import matteroverdrive.api.inventory.UpgradeTypes;
import matteroverdrive.api.machines.IUpgradeHandler;

import java.util.HashMap;
import java.util.Map;

/**
 * This Implementation of the {@link IUpgradeHandler} handles Machine stat that need ot have a lower value by clamping to that value.
 */
public class UpgradeHandlerMinimum implements IUpgradeHandler {
    double totalMinimum;
    Map<UpgradeTypes, Double> mins;

    public UpgradeHandlerMinimum(double totalMinimum) {
        mins = new HashMap<>();
        this.totalMinimum = totalMinimum;
    }

    public UpgradeHandlerMinimum addUpgradeMinimum(UpgradeTypes type, double minimum) {
        mins.put(type, minimum);
        return this;
    }

    @Override
    public double affectUpgrade(UpgradeTypes type, double multiply) {
        if (mins.containsKey(type)) {
            multiply = Math.max(multiply, mins.get(type));
        }
        return Math.max(multiply, totalMinimum);
    }
}
