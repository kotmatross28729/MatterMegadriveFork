package matteroverdrive.init;

import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.relauncher.Side;
import matteroverdrive.api.dialog.IDialogRegistry;
import matteroverdrive.dialog.*;
import matteroverdrive.entity.EntityVillagerMadScientist;
import matteroverdrive.handler.ConfigurationHandler;

public class MatterOverdriveDialogs {
    public static DialogMessage backMessage;
    public static DialogMessage backHomeMessage;
    public static DialogMessage quitMessage;
    public static DialogMessage trade;

    public static void init(FMLInitializationEvent event, ConfigurationHandler configurationHandler, IDialogRegistry registry) {
        backMessage = new DialogMessageBack("").loadQuestionFromLocalization("dialog.generic.back.questions");
        registry.registerMessage(backMessage);
        quitMessage = new DialogMessageQuit("").loadQuestionFromLocalization("dialog.generic.quit.questions");
        registry.registerMessage(quitMessage);
        backHomeMessage = new DialogMessageBackToMain().loadQuestionFromLocalization("dialog.generic.back_home.questions");
        registry.registerMessage(backHomeMessage);
        trade = new DialogMessageTrade().loadQuestionFromLocalization("dialog.generic.trade.questions");
        registry.registerMessage(trade);

        if (event.getSide() == Side.CLIENT) {
            backMessage.setHoloIcon("mini_quit");
            quitMessage.setHoloIcon("mini_quit");
            backHomeMessage.setHoloIcon("mini_quit");
            trade.setHoloIcon("trade");
        }


        EntityVillagerMadScientist.registerDialogMessages(registry, event.getSide());
    }
}
