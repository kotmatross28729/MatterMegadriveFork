package matteroverdrive.proxy;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import matteroverdrive.MatterOverdrive;
import matteroverdrive.compat.MatterOverdriveCompat;
import matteroverdrive.handler.weapon.CommonWeaponHandler;
import matteroverdrive.starmap.GalaxyServer;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.common.MinecraftForge;

public class CommonProxy {
    private CommonWeaponHandler commonWeaponHandler;

    public CommonProxy() {
        commonWeaponHandler = new CommonWeaponHandler();
    }
    
    public void registerItemRenderers() {}
    
    public void registerProxies() {
        MinecraftForge.EVENT_BUS.register(GalaxyServer.getInstance());
        MinecraftForge.EVENT_BUS.register(getWeaponHandler());
        FMLCommonHandler.instance().bus().register(GalaxyServer.getInstance());
        MatterOverdrive.configHandler.subscribe(GalaxyServer.getInstance());
        MatterOverdrive.configHandler.subscribe(GalaxyServer.getInstance().getGalaxyGenerator());
    }
    
    public void registerCompatModules() {
        MatterOverdriveCompat.registerModules();
    }

    public void registerBlockIcons(IIconRegister register) {
    }

    public EntityPlayer getPlayerEntity(MessageContext ctx) {
        return ctx.getServerHandler().playerEntity;
    }

    public void init(FMLInitializationEvent event) {
        registerProxies();
    }

    public void postInit(FMLPostInitializationEvent event) {
    }

    public CommonWeaponHandler getWeaponHandler() {
        return commonWeaponHandler;
    }
}
