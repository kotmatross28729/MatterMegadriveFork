package matteroverdrive.api.renderer;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import matteroverdrive.starmap.data.Galaxy;
import matteroverdrive.starmap.data.SpaceBody;
import matteroverdrive.tile.TileEntityMachineStarMap;

@SideOnly(Side.CLIENT)
public interface ISpaceBodyHoloRenderer {
    void renderBody(Galaxy galaxy, SpaceBody spaceBody, TileEntityMachineStarMap starMap, float partialTicks, float viewerDistance);

    void renderGUIInfo(Galaxy galaxy, SpaceBody spaceBody, TileEntityMachineStarMap starMap, float partialTicks, float opacity);

    boolean displayOnZoom(int zoom, SpaceBody spaceBody);

    double getHologramHeight(SpaceBody spaceBody);
}
