package matteroverdrive.data;

import io.netty.buffer.ByteBuf;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;

public class MinimapEntityInfo {
    boolean isAttacking;
    int entityID;

    public MinimapEntityInfo() {
    }

    public MinimapEntityInfo(EntityLivingBase entityLivingBase, EntityPlayer player) {
        if (entityLivingBase instanceof EntityLiving && ((EntityLiving) entityLivingBase).getAttackTarget() != null) {
            isAttacking = ((EntityLiving) entityLivingBase).getAttackTarget().equals(player);
        }

        entityID = entityLivingBase.getEntityId();
    }

    public MinimapEntityInfo writeToBuffer(ByteBuf buf) {
        buf.writeBoolean(isAttacking);
        buf.writeInt(entityID);
        return this;
    }

    public MinimapEntityInfo readFromBuffer(ByteBuf buf) {
        isAttacking = buf.readBoolean();
        entityID = buf.readInt();
        return this;
    }

    public static boolean hasInfo(EntityLivingBase entityLivingBase, EntityPlayer player) {
        if (entityLivingBase instanceof EntityLiving && ((EntityLiving) entityLivingBase).getAttackTarget() != null) {
            return ((EntityLiving) entityLivingBase).getAttackTarget().equals(player);
        }
        return false;
    }

    public int getEntityID() {
        return entityID;
    }

    public void setEntityID(int entityID) {
        this.entityID = entityID;
    }

    public boolean isAttacking() {
        return isAttacking;
    }

    public void setIsAttacking(boolean isAttacking) {
        this.isAttacking = isAttacking;
    }
}
