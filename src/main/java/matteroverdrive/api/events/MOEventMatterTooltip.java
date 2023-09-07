package matteroverdrive.api.events;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.entity.player.PlayerEvent;

/**
 * This event is triggered when you hold shift while the item tooltip is active.
 * It is connected to showing the matter values for all items.
 * When canceled, the matter info will not be shown.
 */
public class MOEventMatterTooltip extends PlayerEvent {
    public final ItemStack itemStack;
    public int matter;

    public MOEventMatterTooltip(ItemStack itemStack, int matter, EntityPlayer player) {
        super(player);
        this.itemStack = itemStack;
        this.matter = matter;
    }

    public boolean isCancelable() {
        return true;
    }
}
