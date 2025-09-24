package matteroverdrive.client.render.tileentity;

import matteroverdrive.Reference;
import matteroverdrive.init.MatterOverdriveIcons;
import matteroverdrive.util.RenderUtils;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.AdvancedModelLoader;
import net.minecraftforge.client.model.IModelCustom;
import net.minecraftforge.client.model.obj.GroupObject;
import net.minecraftforge.client.model.obj.WavefrontObject;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.vector.Matrix4f;
import matteroverdrive.client.data.Color;

public class TileEntityRendererTritaniumCrate extends TileEntitySpecialRenderer {
	private final IModelCustom model;

	public TileEntityRendererTritaniumCrate() {
		model = AdvancedModelLoader.loadModel(new ResourceLocation(Reference.MODEL_TRITANIUM_CRATE));
	}

	protected void renderBlock(Matrix4f mat, int x, int y, int z, int brightness, int color) {
		Tessellator.instance.startDrawing(GL11.GL_TRIANGLES);
		GroupObject base = ((WavefrontObject) model).groupObjects.get(0);
		GroupObject overlay = ((WavefrontObject) model).groupObjects.get(1);

		RenderUtils.tesseleteModelAsBlock(mat, base, MatterOverdriveIcons.tritanium_crate_base, x, y, z, brightness, true, null);

		RenderUtils.tesseleteModelAsBlock(mat, overlay, MatterOverdriveIcons.tritanium_crate_overlay, x, y, z, brightness, true, new Color(color));
		Tessellator.instance.draw();
	}

	@Override
	public void renderTileEntityAt(TileEntity tileEntity, double x, double y, double z, float p_147500_8_) {
		if (tileEntity != null) {
			GL11.glPushMatrix();
			GL11.glTranslated(x, y, z);
			bindTexture(TextureMap.locationBlocksTexture);

			Matrix4f rot = new Matrix4f();

			Block block = tileEntity.getWorldObj().getBlock(tileEntity.xCoord, tileEntity.yCoord, tileEntity.zCoord);

			RenderUtils.rotateFromBlock(rot, tileEntity.getWorldObj(), tileEntity.xCoord, tileEntity.yCoord, tileEntity.zCoord);
			renderBlock(rot, 0, 0, 0, block.getMixedBrightnessForBlock(tileEntity.getWorldObj(), tileEntity.xCoord, tileEntity.yCoord, tileEntity.zCoord), block.getBlockColor());
			GL11.glPopMatrix();
		}
	}
	
}

