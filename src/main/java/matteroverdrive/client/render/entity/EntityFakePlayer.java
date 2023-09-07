package matteroverdrive.client.render.entity;

import com.mojang.authlib.GameProfile;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ChunkCoordinates;
import net.minecraft.util.IChatComponent;
import net.minecraft.world.World;

public class EntityFakePlayer extends EntityPlayer {
    public EntityFakePlayer(World world, GameProfile gameProfile) {
        super(world, gameProfile);
    }

    @Override
    public void addChatMessage(IChatComponent chatComponent) {

    }

    @Override
    public boolean canCommandSenderUseCommand(int permissionLevel, String command) {
        return false;
    }

    @Override
    public ChunkCoordinates getPlayerCoordinates() {
        return null;
    }
}
