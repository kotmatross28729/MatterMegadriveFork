package matteroverdrive.api;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import matteroverdrive.api.android.IAndroidStatRegistry;
import matteroverdrive.api.android.IAndroidStatRenderRegistry;
import matteroverdrive.api.dialog.IDialogRegistry;
import matteroverdrive.api.matter.IMatterRegistry;
import matteroverdrive.api.renderer.IBionicPartRenderRegistry;
import matteroverdrive.api.starmap.IStarmapRenderRegistry;

public interface IMOApi {
    IMatterRegistry getMatterRegistry();

    IAndroidStatRegistry getAndroidStatRegistry();

    IDialogRegistry getDialogRegistry();

    @SideOnly(Side.CLIENT)
    IAndroidStatRenderRegistry getAndroidStatRenderRegistry();

    @SideOnly(Side.CLIENT)
    IBionicPartRenderRegistry getBionicStatRenderRegistry();

    @SideOnly(Side.CLIENT)
    IStarmapRenderRegistry getStarmapRenderRegistry();

}
