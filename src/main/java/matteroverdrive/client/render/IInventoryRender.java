package matteroverdrive.client.render;

import net.minecraft.item.Item;
import net.minecraftforge.client.IItemRenderer;

public interface IInventoryRender {
	public Item getItemForRenderer();
	public default Item[] getItemsForRenderer() {
		return new Item[] { this.getItemForRenderer() };
	}
	public IItemRenderer getRenderer();
}
