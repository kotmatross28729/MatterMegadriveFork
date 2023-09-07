package matteroverdrive.client.render.entity;

import matteroverdrive.Reference;
import net.minecraft.client.renderer.entity.RenderVillager;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;

public class EntityRendererMadScientist extends RenderVillager {
    public static final ResourceLocation texture = new ResourceLocation(Reference.PATH_ENTITIES + "mad_scientist.png");

    @Override
    protected ResourceLocation getEntityTexture(Entity entity) {
        return texture;
    }
}
