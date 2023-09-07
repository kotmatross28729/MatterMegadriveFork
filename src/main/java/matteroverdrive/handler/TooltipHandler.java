package matteroverdrive.handler;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import matteroverdrive.api.events.MOEventMatterTooltip;
import matteroverdrive.util.MOStringHelper;
import matteroverdrive.util.MatterHelper;
import net.minecraft.util.EnumChatFormatting;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import org.lwjgl.input.Keyboard;

public class TooltipHandler {
    @SubscribeEvent
    public void onItemTooltip(ItemTooltipEvent event) {
        if (Keyboard.isKeyDown(Keyboard.KEY_LSHIFT) || Keyboard.isKeyDown(Keyboard.KEY_RSHIFT)) {
            MOEventMatterTooltip tooltipEvent = new MOEventMatterTooltip(event.itemStack, MatterHelper.getMatterAmountFromItem(event.itemStack), event.entityPlayer);
            if (!MinecraftForge.EVENT_BUS.post(tooltipEvent)) {
                if (tooltipEvent.matter > 0) {
                    event.toolTip.add(EnumChatFormatting.BLUE + MOStringHelper.translateToLocal("gui.tooltip.matter") + ": " + EnumChatFormatting.GOLD + MatterHelper.formatMatter(tooltipEvent.matter));
                } else {
                    event.toolTip.add(EnumChatFormatting.BLUE + MOStringHelper.translateToLocal("gui.tooltip.matter") + ": " + EnumChatFormatting.RED + MOStringHelper.translateToLocal("gui.tooltip.matter.none"));
                }
            }
        }
    }
}
