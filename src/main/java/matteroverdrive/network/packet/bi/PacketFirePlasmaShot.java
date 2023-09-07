package matteroverdrive.network.packet.bi;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import io.netty.buffer.ByteBuf;
import matteroverdrive.MatterOverdrive;
import matteroverdrive.api.weapon.WeaponShot;
import matteroverdrive.items.weapon.EnergyWeapon;
import matteroverdrive.network.packet.AbstractBiPacketHandler;
import matteroverdrive.network.packet.PacketAbstract;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Vec3;

public class PacketFirePlasmaShot extends PacketAbstract {
    WeaponShot shot;
    private int sender;
    private Vec3 position;
    private Vec3 direction;

    public PacketFirePlasmaShot() {
    }

    public PacketFirePlasmaShot(int sender, Vec3 pos, Vec3 dir, WeaponShot shot) {
        this.shot = shot;
        this.sender = sender;
        this.position = pos;
        this.direction = dir;
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        this.shot = new WeaponShot(buf);
        this.sender = buf.readInt();
        this.position = Vec3.createVectorHelper(buf.readDouble(), buf.readDouble(), buf.readDouble());
        this.direction = Vec3.createVectorHelper(buf.readFloat(), buf.readFloat(), buf.readFloat());
    }

    @Override
    public void toBytes(ByteBuf buf) {
        shot.writeTo(buf);
        buf.writeInt(sender);
        buf.writeDouble(position.xCoord);
        buf.writeDouble(position.yCoord);
        buf.writeDouble(position.zCoord);
        buf.writeFloat((float) direction.xCoord);
        buf.writeFloat((float) direction.yCoord);
        buf.writeFloat((float) direction.zCoord);
    }

    public WeaponShot getShot() {
        return shot;
    }

    public static class BiHandler extends AbstractBiPacketHandler<PacketFirePlasmaShot> {
        @Override
        public IMessage handleClientMessage(EntityPlayer player, PacketFirePlasmaShot message, MessageContext ctx) {
            if (player.getEntityId() != message.sender) {
                Entity entity = player.worldObj.getEntityByID(message.sender);
                if (entity != null && entity instanceof EntityLivingBase) {
                    EntityLivingBase livingBase = (EntityLivingBase) entity;
                    if (livingBase.getHeldItem() != null && livingBase.getHeldItem().getItem() instanceof EnergyWeapon) {
                        ((EnergyWeapon) livingBase.getHeldItem().getItem()).onClientShot(livingBase.getHeldItem(), livingBase, message.position, message.direction, message.shot);
                    }
                }

            }
            return null;
        }

        @Override
        public IMessage handleServerMessage(EntityPlayer player, PacketFirePlasmaShot message, MessageContext ctx) {
            handleServerShot(player, message, 0);
            MatterOverdrive.packetPipeline.sendToAllAround(message, player, message.shot.getRange() + 64);
            return null;
        }

        public void handleServerShot(EntityPlayer player, PacketFirePlasmaShot shot, int delay) {
            ItemStack heldItem = player.getHeldItem();
            if (heldItem != null && heldItem.getItem() instanceof EnergyWeapon && ((EnergyWeapon) heldItem.getItem()).canFire(player.getHeldItem(), player.worldObj, player)) {
                ((EnergyWeapon) heldItem.getItem()).onServerFire(heldItem, player, shot.shot, shot.position, shot.direction, delay);
            }
        }
    }
}
