package matteroverdrive.api.events.weapon;

import cpw.mods.fml.common.eventhandler.Event;
import cpw.mods.fml.relauncher.Side;
import matteroverdrive.entity.weapon.PlasmaBolt;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MovingObjectPosition;

/**
 * Triggered when a Plasma bolt hits a target.
 * It can be either a block or an Entity.
 */
public class MOEventPlasmaBlotHit extends Event {
    public final ItemStack weapon;
    public final MovingObjectPosition hit;
    public final PlasmaBolt plasmaBolt;
    public final Side side;

    public MOEventPlasmaBlotHit(ItemStack weapon, MovingObjectPosition hit, PlasmaBolt plasmaBolt, Side side) {
        this.weapon = weapon;
        this.hit = hit;
        this.plasmaBolt = plasmaBolt;
        this.side = side;
    }
}
