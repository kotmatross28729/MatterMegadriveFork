package matteroverdrive.fx;

import matteroverdrive.client.data.Color;
import net.minecraft.client.particle.EntityFX;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.world.World;

public class PhaserBoltRecoil extends EntityFX {
    private float lavaParticleScale;

    public PhaserBoltRecoil(World world, double x, double y, double z, Color color, double dirX, double dirY, double dirZ) {
        super(world, x, y, z, dirX, dirY, dirZ);
        //this.motionX *= 0.800000011920929D;
        //this.motionY *= 0.800000011920929D;
        //this.motionZ *= 0.800000011920929D;
        this.motionY += (double) ((this.rand.nextFloat() - 0.5f) * 0.2F);
        this.motionX += (double) ((this.rand.nextFloat() - 0.5f) * 0.2F);
        this.motionZ += (double) ((this.rand.nextFloat() - 0.5f) * 0.2F);
        this.particleRed = color.getFloatR();
        this.particleGreen = color.getFloatG();
        this.particleBlue = color.getFloatB();
        this.particleScale *= this.rand.nextFloat() * 0.5F + 1F;
        this.lavaParticleScale = this.particleScale;
        this.particleMaxAge = (int) (8d / (Math.random() * 0.8D + 0.2D));
        this.noClip = false;
        this.setParticleTextureIndex(rand.nextInt(2));
    }

    public PhaserBoltRecoil(World p_i1215_1_, double x, double y, double z, Color color) {
        this(p_i1215_1_, x, y, z, color, 0, 0, 0);
    }

    public int getBrightnessForRender(float f) {
        float f1 = ((float) this.particleAge + f) / (float) this.particleMaxAge;

        if (f1 < 0.0F) {
            f1 = 0.0F;
        }

        if (f1 > 1.0F) {
            f1 = 1.0F;
        }

        int i = super.getBrightnessForRender(f);
        short short1 = 240;
        int j = i >> 16 & 255;
        return short1 | j << 16;
    }

    /**
     * Gets how bright this entity is.
     */
    public float getBrightness(float f) {
        return 1.0F;
    }

    public void renderParticle(Tessellator tessellator, float p_70539_2_, float p_70539_3_, float p_70539_4_, float p_70539_5_, float p_70539_6_, float p_70539_7_) {
        float f6 = ((float) this.particleAge + p_70539_2_) / (float) this.particleMaxAge;
        this.particleScale = this.lavaParticleScale * (1.0F - f6 * f6);
        super.renderParticle(tessellator, p_70539_2_, p_70539_3_, p_70539_4_, p_70539_5_, p_70539_6_, p_70539_7_);
    }

    /**
     * Called to update the entity's position/logic.
     */
    public void onUpdate() {
        this.prevPosX = this.posX;
        this.prevPosY = this.posY;
        this.prevPosZ = this.posZ;

        if (this.particleAge++ >= this.particleMaxAge) {
            this.setDead();
        }

        float f = (float) this.particleAge / (float) this.particleMaxAge;

        this.motionY -= 0.03D;
        try {
            this.moveEntity(this.motionX, this.motionY, this.motionZ);
        } catch (Exception e) {
            this.setDead();
        }

        this.motionX *= 0.9990000128746033D;
        this.motionY *= 0.9990000128746033D;
        this.motionZ *= 0.9990000128746033D;

        if (this.onGround) {
            this.motionX *= 0.699999988079071D;
            this.motionZ *= 0.699999988079071D;
        }
    }
}
