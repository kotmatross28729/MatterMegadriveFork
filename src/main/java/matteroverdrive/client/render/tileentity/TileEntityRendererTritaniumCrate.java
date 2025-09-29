package matteroverdrive.client.render.tileentity;

import static matteroverdrive.client.render.TE_Resource_Manager.TRITANIUM_CRATE_BASE_TEXTURE;
import static matteroverdrive.client.render.TE_Resource_Manager.TRITANIUM_CRATE_MODEL;
import static matteroverdrive.client.render.TE_Resource_Manager.TRITANIUM_CRATE_OVERLAY_TEXTURE;
import matteroverdrive.util.RenderUtils;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import org.lwjgl.opengl.GL11;

public class TileEntityRendererTritaniumCrate extends TileEntitySpecialRenderer {
	

	@Override
	public void renderTileEntityAt(TileEntity tileEntity, double x, double y, double z, float p_147500_8_) {
		if (tileEntity != null) {
			GL11.glPushMatrix();
			GL11.glTranslated(x + 0.5f, y, z + 0.5f);
			RenderUtils.rotateFromBlock(tileEntity.getWorldObj(), tileEntity.xCoord, tileEntity.yCoord, tileEntity.zCoord);
			
			bindTexture(TRITANIUM_CRATE_BASE_TEXTURE);
			TRITANIUM_CRATE_MODEL.renderPart("base");
			
			bindTexture(TRITANIUM_CRATE_OVERLAY_TEXTURE);
			TRITANIUM_CRATE_MODEL.renderPart("overlay");
			
			GL11.glPopMatrix();
		}
	}
	
}

