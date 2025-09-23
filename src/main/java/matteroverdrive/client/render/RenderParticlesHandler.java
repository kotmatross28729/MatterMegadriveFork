package matteroverdrive.client.render;

import cpw.mods.fml.common.gameevent.TickEvent;
import matteroverdrive.Reference;
import matteroverdrive.client.RenderHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.EntityFX;
import net.minecraft.client.renderer.ActiveRenderInfo;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.crash.CrashReport;
import net.minecraft.crash.CrashReportCategory;
import net.minecraft.entity.Entity;
import net.minecraft.util.ReportedException;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import org.lwjgl.opengl.GL11;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.Callable;

public class RenderParticlesHandler implements IWorldLastRenderer {
    ResourceLocation additiveTextureSheet = new ResourceLocation(Reference.PATH_PARTICLE + "particles_additive.png");
    private TextureManager renderer;
    protected World worldObj;
    private Random rand = new Random();
    List<EntityFX>[] fxes;

    public RenderParticlesHandler(World world, TextureManager renderer) {
        this.worldObj = world;
        this.renderer = renderer;
        fxes = new List[Blending.values().length];
        for (int i = 0; i < Blending.values().length; i++) {
            fxes[i] = new ArrayList<>();
        }
    }

    public void onRenderWorldLast(RenderHandler renderHandler, RenderWorldLastEvent event) {
        renderParticles(Minecraft.getMinecraft().thePlayer, event.partialTicks);
    }

    public void onClientTick(TickEvent.ClientTickEvent event) {
        if (!Minecraft.getMinecraft().isGamePaused()) {
            updateEffects();
        }
    }

    public void addEffect(EntityFX entityFX, Blending blendingLayer) {
        fxes[blendingLayer.ordinal()].add(entityFX);
    }

    private void updateEffects() {
        for (int k = 0; k < fxes.length; ++k) {

            for (int j = 0; j < this.fxes[k].size(); ++j) {
                final EntityFX entityfx = this.fxes[k].get(j);

                try {
                    if (entityfx != null) {
                        entityfx.onUpdate();
                    }
                } catch (Throwable throwable) {
                    CrashReport crashreport = CrashReport.makeCrashReport(throwable, "Ticking Particle");
                    CrashReportCategory crashreportcategory = crashreport.makeCategory("Particle being ticked");
                    crashreportcategory.addCrashSectionCallable("Particle", new Callable() {

                        public String call() {
                            return entityfx.toString();
                        }
                    });
                    throw new ReportedException(crashreport);
                }

                if (entityfx == null || entityfx.isDead) {
                    this.fxes[k].remove(j--);
                }
            }
        }
    }

    public void renderParticles(Entity entity, float f) {
        float f1 = ActiveRenderInfo.rotationX;
        float f2 = ActiveRenderInfo.rotationZ;
        float f3 = ActiveRenderInfo.rotationYZ;
        float f4 = ActiveRenderInfo.rotationXY;
        float f5 = ActiveRenderInfo.rotationXZ;
        EntityFX.interpPosX = entity.lastTickPosX + (entity.posX - entity.lastTickPosX) * (double) f;
        EntityFX.interpPosY = entity.lastTickPosY + (entity.posY - entity.lastTickPosY) * (double) f;
        EntityFX.interpPosZ = entity.lastTickPosZ + (entity.posZ - entity.lastTickPosZ) * (double) f;

        for (int k = 0; k < fxes.length; ++k) {
            if (!this.fxes[k].isEmpty()) {
                GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
                GL11.glDepthMask(false);
                GL11.glEnable(GL11.GL_BLEND);
                switch (k) {
                    case 0:
                        GL11.glBlendFunc(GL11.GL_ONE, GL11.GL_ONE);
                        renderer.bindTexture(additiveTextureSheet);
                        break;
                }


                GL11.glAlphaFunc(GL11.GL_GREATER, 0.003921569F);
                Tessellator tessellator = Tessellator.instance;
                tessellator.startDrawingQuads();

                for (int j = 0; j < this.fxes[k].size(); ++j) {
                    final EntityFX entityfx = this.fxes[k].get(j);
                    if (entityfx == null) continue;
                    tessellator.setBrightness(entityfx.getBrightnessForRender(f));

                    try {
                        entityfx.renderParticle(tessellator, f, f1, f5, f2, f3, f4);
                    } catch (Throwable throwable) {
                        CrashReport crashreport = CrashReport.makeCrashReport(throwable, "Rendering Particle");
                        CrashReportCategory crashreportcategory = crashreport.makeCategory("Particle being rendered");
                        crashreportcategory.addCrashSectionCallable("Particle", entityfx::toString);
                        throw new ReportedException(crashreport);
                    }
                }

                tessellator.draw();
                GL11.glDisable(GL11.GL_BLEND);
                GL11.glDepthMask(true);
                GL11.glAlphaFunc(GL11.GL_GREATER, 0.1F);
            }
        }
    }

    public enum Blending {
        Additive
    }

    public ResourceLocation getAdditiveTextureSheet() {
        return additiveTextureSheet;
    }
}
