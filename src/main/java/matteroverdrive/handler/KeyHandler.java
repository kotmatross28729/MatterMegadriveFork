package matteroverdrive.handler;

import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.InputEvent;
import cpw.mods.fml.common.gameevent.TickEvent;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import matteroverdrive.MatterOverdrive;
import matteroverdrive.api.android.IBionicStat;
import matteroverdrive.entity.player.AndroidPlayer;
import matteroverdrive.items.MatterScanner;
import matteroverdrive.network.packet.server.PacketBioticActionKey;
import matteroverdrive.proxy.ClientProxy;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiChat;
import net.minecraft.client.settings.KeyBinding;
import org.lwjgl.input.Keyboard;

@SideOnly(Side.CLIENT)
public class KeyHandler {
    public static final int MATTER_SCANNER_KEY = 0;
    public static final int ABILITY_USE_KEY = 1;
    public static final int ABILITY_SWITCH_KEY = 2;
    private static final String[] keyDesc = {"Open Matter Scanner GUI", "Android Ability key", "Android Switch Ability key"};
    private static final int[] keyValues = {Keyboard.KEY_C, Keyboard.KEY_X, Keyboard.KEY_TAB};
    private final KeyBinding[] keys;

    public KeyHandler() {
        keys = new KeyBinding[keyValues.length];
        for (int i = 0; i < keys.length; i++) {
            keys[i] = new KeyBinding(keyDesc[i], keyValues[i], "Matter Megadrive");
            ClientRegistry.registerKeyBinding(keys[i]);
        }
    }

    @SubscribeEvent
    public void onKeyInput(InputEvent.KeyInputEvent event) {
        if (!FMLClientHandler.instance().isGUIOpen(GuiChat.class)) {
            int key = Keyboard.getEventKey();
            boolean isDown = Keyboard.getEventKeyState();

            //Matter Scanner key
            if (isDown && keys[MATTER_SCANNER_KEY].getKeyCode() == key) {
                //send packet to open gui
                MatterScanner.DisplayGuiScreen();
            }

            manageBiostats(key, isDown);
        }
    }

    @SubscribeEvent
    public void onClientTick(TickEvent.ClientTickEvent event) {
        if (Minecraft.getMinecraft().thePlayer == null || Minecraft.getMinecraft().theWorld == null || Minecraft.getMinecraft().isGamePaused() || Minecraft.getMinecraft().currentScreen != null)
            return;

        AndroidPlayer androidPlayer = AndroidPlayer.get(FMLClientHandler.instance().getClientPlayerEntity());
        if (androidPlayer.isAndroid() && ClientProxy.keyHandler.getBinding(KeyHandler.ABILITY_USE_KEY).isPressed()) {
            for (IBionicStat stat : MatterOverdrive.statRegistry.getStats()) {
                int level = androidPlayer.getUnlockedLevel(stat);
                if (level > 0 && stat.isEnabled(androidPlayer, level)) {
                    stat.onActionKeyPress(androidPlayer, androidPlayer.getUnlockedLevel(stat), false);
                }
            }
            MatterOverdrive.packetPipeline.sendToServer(new PacketBioticActionKey());
        }
    }

    public void manageBiostats(int keyCode, boolean state) {
        AndroidPlayer androidPlayer = AndroidPlayer.get(FMLClientHandler.instance().getClientPlayerEntity());
        if (androidPlayer.isAndroid()) {
            for (IBionicStat stat : MatterOverdrive.statRegistry.getStats()) {
                int level = androidPlayer.getUnlockedLevel(stat);
                if (level > 0 && stat.isEnabled(androidPlayer, level)) {
                    stat.onKeyPress(androidPlayer, androidPlayer.getUnlockedLevel(stat), keyCode, state);
                }
            }
        }
    }

    public KeyBinding getBinding(int id) {
        return keys[id];
    }
}
