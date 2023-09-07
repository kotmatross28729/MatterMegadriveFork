package matteroverdrive.core;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import matteroverdrive.MatterOverdrive;
import matteroverdrive.api.IMOApi;
import matteroverdrive.api.android.IAndroidStatRegistry;
import matteroverdrive.api.android.IAndroidStatRenderRegistry;
import matteroverdrive.api.dialog.IDialogRegistry;
import matteroverdrive.api.matter.IMatterRegistry;
import matteroverdrive.api.renderer.IBionicPartRenderRegistry;
import matteroverdrive.api.starmap.IStarmapRenderRegistry;
import matteroverdrive.proxy.ClientProxy;

public abstract class Api implements IMOApi {
    public static final Api INSTANCE = new Api() {
        @Override
        public IBionicPartRenderRegistry getBionicStatRenderRegistry() {
            return null;
        }
    };

    @Override
    public IMatterRegistry getMatterRegistry() {
        return MatterOverdrive.matterRegistry;
    }

    @Override
    public IAndroidStatRegistry getAndroidStatRegistry() {
        return MatterOverdrive.statRegistry;
    }

    @Override
    public IDialogRegistry getDialogRegistry() {
        return MatterOverdrive.dialogRegistry;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public IAndroidStatRenderRegistry getAndroidStatRenderRegistry() {
        return ClientProxy.renderHandler.getStatRenderRegistry();
    }
/**
    @Override
    public IBionicPartRenderRegistry getBionicStatRenderRegistry() {
        return ClientProxy.renderHandler.getBionicPartRenderRegistry();
    }
*/
    @Override
    @SideOnly(Side.CLIENT)
    public IStarmapRenderRegistry getStarmapRenderRegistry() {
        return ClientProxy.renderHandler.getStarmapRenderRegistry();
    }
}
