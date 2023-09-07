package matteroverdrive.dialog;

import matteroverdrive.api.dialog.IDialogNpc;
import matteroverdrive.entity.player.AndroidPlayer;
import matteroverdrive.init.MatterOverdriveItems;
import matteroverdrive.util.MOStringHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.ChatStyle;
import net.minecraft.util.EnumChatFormatting;

public class DialogMessageAndroidTransformation extends DialogMessage {
    public DialogMessageAndroidTransformation() {
        super();
    }

    public DialogMessageAndroidTransformation(String message, String question) {
        super(message, question);
    }

    public DialogMessageAndroidTransformation(String message) {
        super(message);
    }

    @Override
    public boolean canInteract(IDialogNpc npc, EntityPlayer player) {
        boolean[] hasParts = new boolean[4];
        int[] slots = new int[4];

        for (int i = 0; i < player.inventory.getSizeInventory(); i++) {
            if (player.inventory.getStackInSlot(i) != null && player.inventory.getStackInSlot(i).getItem() == MatterOverdriveItems.androidParts) {
                int damage = player.inventory.getStackInSlot(i).getItemDamage();
                if (damage < hasParts.length) {
                    hasParts[damage] = true;
                    slots[damage] = i;
                }
            }
        }

        for (boolean hasPart : hasParts) {
            if (!hasPart) {
                return false;
            }
        }

        return true;
    }

    @Override
    public void onInteract(IDialogNpc npc, EntityPlayer player) {
        boolean[] hasParts = new boolean[4];
        int[] slots = new int[4];

        for (int i = 0; i < player.inventory.getSizeInventory(); i++) {
            if (player.inventory.getStackInSlot(i) != null && player.inventory.getStackInSlot(i).getItem() == MatterOverdriveItems.androidParts) {
                int damage = player.inventory.getStackInSlot(i).getItemDamage();
                if (damage < hasParts.length) {
                    hasParts[damage] = true;
                    slots[damage] = i;
                }
            }
        }

        for (boolean hasPart : hasParts) {
            if (!hasPart) {
                if (!player.worldObj.isRemote) {
                    ChatComponentText componentText = new ChatComponentText(EnumChatFormatting.GOLD + "<Mad Scientist>" + EnumChatFormatting.RED + MOStringHelper.translateToLocal("entity.mad_scientist.line.fail." + player.getRNG().nextInt(4)));
                    componentText.setChatStyle(new ChatStyle().setColor(EnumChatFormatting.RED));
                    player.addChatMessage(componentText);
                }
                return;
            }
        }

        if (!player.worldObj.isRemote) {
            for (int slot : slots) {
                player.inventory.decrStackSize(slot, 1);
            }
        }

        AndroidPlayer.get(player).startConversion();
        player.closeScreen();
    }

    @Override
    public boolean isVisible(IDialogNpc npc, EntityPlayer player) {
        return AndroidPlayer.get(player) == null || !AndroidPlayer.get(player).isAndroid();
    }
}
