package matteroverdrive.starmap;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.TickEvent;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import matteroverdrive.api.starmap.IShip;
import matteroverdrive.starmap.data.Planet;
import matteroverdrive.starmap.data.Star;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

@SideOnly(Side.CLIENT)
public class GalaxyClient extends GalaxyCommon {
    //region Private Vars
    private static GalaxyClient instance;
    //endregion

    //region Constructors
    public GalaxyClient() {
        super();
    }
    //endregion

    public boolean canSeePlanetInfo(Planet planet, EntityPlayer player) {
        if (planet.isOwner(player) || player.capabilities.isCreativeMode) {
            return true;
        }

        for (ItemStack shipStack : planet.getFleet()) {
            if (((IShip) shipStack.getItem()).isOwner(shipStack, player)) {
                return true;
            }
        }

        return false;
    }

    public boolean canSeeStarInfo(Star star, EntityPlayer player) {
        for (Planet planet : star.getPlanets()) {
            if (canSeePlanetInfo(planet, player)) {
                return true;
            }
        }
        return false;
    }

    //region Events
    @SubscribeEvent
    public void onClientTick(TickEvent.ClientTickEvent event) {
        if (Minecraft.getMinecraft().theWorld != null &&
                theGalaxy != null &&
                !Minecraft.getMinecraft().isGamePaused() &&
                Minecraft.getMinecraft().theWorld.isRemote &&
                Minecraft.getMinecraft().theWorld.provider.dimensionId == 0 &&
                event.phase == TickEvent.Phase.START &&
                Minecraft.getMinecraft().theWorld != null) {
            theGalaxy.update(Minecraft.getMinecraft().theWorld);
        }
    }
    //endregion

    //region Getters and Setters
    public static GalaxyClient getInstance() {
        if (instance == null) {
            instance = new GalaxyClient();
        }

        return instance;
    }
    //endregion
}
