package matteroverdrive.tile;

import cpw.mods.fml.common.gameevent.TickEvent;
import net.minecraft.world.World;

public interface IMOTickable {
    void onServerTick(TickEvent.Phase phase, World world);
}
