package matteroverdrive.api.entity;

import net.minecraft.entity.EntityCreature;
import net.minecraft.util.Vec3;

public interface IPathableMob<T extends EntityCreature> {
    Vec3 getCurrentTarget();

    void onTargetReached(Vec3 pos);

    boolean isNearTarget(Vec3 pos);

    T getEntity();
}
