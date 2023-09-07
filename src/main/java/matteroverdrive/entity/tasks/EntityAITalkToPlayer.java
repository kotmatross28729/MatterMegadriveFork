package matteroverdrive.entity.tasks;

import matteroverdrive.api.dialog.IDialogNpc;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;

public class EntityAITalkToPlayer extends EntityAIBase {
    private IDialogNpc npc;

    public EntityAITalkToPlayer(IDialogNpc npc) {
        this.npc = npc;
        this.setMutexBits(5);
    }

    @Override
    public boolean shouldExecute() {
        if (!this.npc.getEntity().isEntityAlive()) {
            return false;
        } else {
            EntityPlayer entityplayer = this.npc.getDialogPlayer();
            return entityplayer != null && (!(this.npc.getEntity().getDistanceSqToEntity(entityplayer) > 32.0D) && entityplayer.openContainer instanceof Container);
        }
    }

    @Override
    public void startExecuting() {
        this.npc.getEntity().getNavigator().clearPathEntity();
    }

    public void resetTask() {
        this.npc.setDialogPlayer(null);
    }
}
