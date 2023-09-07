package matteroverdrive.client.sound;

import net.minecraft.client.audio.ISound;
import net.minecraft.client.audio.PositionedSound;
import net.minecraft.util.ResourceLocation;

public class MOPositionedSound extends PositionedSound {
    public MOPositionedSound(ResourceLocation p_i45103_1_, float volume, float pitch) {
        super(p_i45103_1_);
        this.field_147663_c = pitch;
        this.volume = volume;
    }

    public void setPosition(float x, float y, float z) {
        this.xPosF = x;
        this.yPosF = y;
        this.zPosF = z;
    }

    public void setRepeat(boolean repeat) {
        this.repeat = repeat;
    }

    public MOPositionedSound setAttenuationType(ISound.AttenuationType type) {
        field_147666_i = type;
        return this;
    }
}
