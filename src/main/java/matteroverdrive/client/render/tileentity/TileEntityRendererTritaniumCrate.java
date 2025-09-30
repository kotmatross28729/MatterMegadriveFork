package matteroverdrive.client.render.tileentity;

import matteroverdrive.client.data.Color;
import matteroverdrive.client.render.IInventoryRender;
import matteroverdrive.client.render.ItemRenderBase;
import static matteroverdrive.client.render.TE_Resource_Manager.TRITANIUM_CRATE_BASE_TEXTURE;
import static matteroverdrive.client.render.TE_Resource_Manager.TRITANIUM_CRATE_MODEL;
import static matteroverdrive.client.render.TE_Resource_Manager.TRITANIUM_CRATE_OVERLAY_TEXTURE;
import matteroverdrive.init.MatterOverdriveBlocks;
import matteroverdrive.util.RenderUtils;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.client.IItemRenderer;
import org.lwjgl.opengl.GL11;

public class TileEntityRendererTritaniumCrate extends TileEntitySpecialRenderer implements IInventoryRender {

	@Override
	public void renderTileEntityAt(TileEntity tileEntity, double x, double y, double z, float p_147500_8_) {
		if (tileEntity != null) {
			GL11.glPushMatrix();
			GL11.glTranslated(x + 0.5f, y, z + 0.5f);
			RenderUtils.rotateFromBlock(tileEntity.getWorldObj(), tileEntity.xCoord, tileEntity.yCoord, tileEntity.zCoord);
			Block block = tileEntity.getWorldObj().getBlock(tileEntity.xCoord, tileEntity.yCoord, tileEntity.zCoord);
			
			bindTexture(TRITANIUM_CRATE_BASE_TEXTURE);
			TRITANIUM_CRATE_MODEL.renderPart("base");
			
			bindTexture(TRITANIUM_CRATE_OVERLAY_TEXTURE);
			Color color = new Color(block.getBlockColor());
			GL11.glColor3f(color.getFloatR(), color.getFloatG(), color.getFloatB());
			TRITANIUM_CRATE_MODEL.renderPart("overlay");
			
			GL11.glPopMatrix();
		}
	}
	
	@Override
	public Item getItemForRenderer() {
		return null;
	}
	
	@Override
	public Item[] getItemsForRenderer() {
		Item[] items = new Item[MatterOverdriveBlocks.tritaniumCrate.length];
		for(int i = 0; i < MatterOverdriveBlocks.tritaniumCrate.length; i++) {
			items[i] = Item.getItemFromBlock(MatterOverdriveBlocks.tritaniumCrate[i]);
		}
		return items;
	}
	
	@Override
	public IItemRenderer getRenderer() {
		return new ItemRenderBase() {
			public void renderInventory() {
				GL11.glTranslated(0, -2, 0);
				GL11.glScaled(5, 5, 5);
			}
			
			public void renderCommonWithStack(ItemStack item) {
				GL11.glScaled(2, 2, 2);
				
				bindTexture(TRITANIUM_CRATE_BASE_TEXTURE);
				TRITANIUM_CRATE_MODEL.renderPart("base");
				
				bindTexture(TRITANIUM_CRATE_OVERLAY_TEXTURE);
				Color color = new Color(Block.getBlockFromItem(item.getItem()).getBlockColor());
				GL11.glColor3f(color.getFloatR(), color.getFloatG(), color.getFloatB());
				TRITANIUM_CRATE_MODEL.renderPart("overlay");
			}
		};
	}
	
}

