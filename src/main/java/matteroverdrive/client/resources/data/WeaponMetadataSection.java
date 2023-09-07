package matteroverdrive.client.resources.data;

import net.minecraft.client.resources.data.IMetadataSection;
import net.minecraft.util.Vec3;

public class WeaponMetadataSection implements IMetadataSection {
    private final Vec3 scopePosition;

    public WeaponMetadataSection(Vec3 scopePosition) {
        this.scopePosition = scopePosition;
    }

    public Vec3 getScopePosition() {
        return scopePosition;
    }
}
