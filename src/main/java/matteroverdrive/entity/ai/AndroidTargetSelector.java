package matteroverdrive.entity.ai;

import matteroverdrive.entity.monster.EntityMutantScientist;
import matteroverdrive.entity.monster.EntityRogueAndroidMob;
import matteroverdrive.entity.player.AndroidPlayer;
import net.minecraft.command.IEntitySelector;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;

public class AndroidTargetSelector implements IEntitySelector {
    final EntityRogueAndroidMob mob;

    public AndroidTargetSelector(EntityRogueAndroidMob mob) {
        this.mob = mob;
    }

    @Override
    public boolean isEntityApplicable(Entity entity) {
        if (entity instanceof EntityPlayer) {
            if (mob.hasTeam()) {
                return ((EntityPlayer) entity).getTeam() != null && !((EntityPlayer) entity).getTeam().isSameTeam(mob.getTeam());
            } else {
                AndroidPlayer androidPlayer = AndroidPlayer.get((EntityPlayer) entity);
                if (androidPlayer == null || !androidPlayer.isAndroid()) {
                    return true;
                }
            }
        } else if (entity instanceof EntityMutantScientist) {
            return true;
        } else if (entity instanceof EntityRogueAndroidMob) {
            if (mob.hasTeam() && ((EntityRogueAndroidMob) entity).hasTeam()) {
                return !((EntityRogueAndroidMob) entity).getTeam().isSameTeam(mob.getTeam());
            }
        }
        return false;
    }
}
