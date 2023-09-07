package matteroverdrive.commands;

import matteroverdrive.MatterOverdrive;
import matteroverdrive.Reference;
import matteroverdrive.api.android.IBionicStat;
import matteroverdrive.entity.player.AndroidPlayer;
import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;
import java.util.stream.Collectors;

public class AndroidCommands extends CommandBase {
    @Override
    public String getCommandName() {
        return "android";
    }

    public int getRequiredPermissionLevel() {
        return 2;
    }

    @Override
    public String getCommandUsage(ICommandSender sender) {
        return "android <command> <value> <player>";
    }

    @Override
    public List getCommandAliases() {
        return new ArrayList();
    }

    @Override
    public void processCommand(ICommandSender sender, String[] parameters) {
        if (parameters.length == 0) {
            sender.addChatMessage(new ChatComponentText("Invalid Parameters"));
            return;
        }

        if (parameters.length >= 2) {
            EntityPlayer player;
            if (parameters.length >= 3) {
                player = getPlayer(sender, parameters[2]);
            } else {
                player = getCommandSenderAsPlayer(sender);
            }

            if (player != null) {
                AndroidPlayer androidPlayer = AndroidPlayer.get(player);
                if (androidPlayer != null) {

                    boolean validCommand = false;
                    String commandInfo = "";

                    if (parameters[0].equalsIgnoreCase("set")) {
                        boolean android = parseBoolean(sender, parameters[1]);
                        androidPlayer.setAndroid(android);
                        validCommand = true;
                        if (android) {
                            commandInfo = sender.getCommandSenderName() + " is now an Android";
                        } else {
                            commandInfo = sender.getCommandSenderName() + " is no longer an Android";
                        }
                    } else if (parameters[0].equalsIgnoreCase("stats")) {
                        if (parameters[1].equalsIgnoreCase("reset")) {
                            androidPlayer.resetUnlocked();
                            validCommand = true;
                            commandInfo = sender.getCommandSenderName() + " stats are now Reset";
                        }
                    } else if (parameters[0].equalsIgnoreCase("unlock")) {
                        if (MatterOverdrive.statRegistry.hasStat(parameters[1])) {
                            IBionicStat stat = MatterOverdrive.statRegistry.getStat(parameters[1]);
                            androidPlayer.unlock(stat, stat.maxLevel());
                            validCommand = true;
                            commandInfo = sender.getCommandSenderName() + " now has the ability " + EnumChatFormatting.GREEN + "[" + stat.getDisplayName(androidPlayer, stat.maxLevel()) + "]";
                        }
                    } else if (parameters[0].equalsIgnoreCase("forget")) {
                        if (MatterOverdrive.statRegistry.hasStat(parameters[1])) {
                            IBionicStat stat = MatterOverdrive.statRegistry.getStat(parameters[1]);
                            androidPlayer.reset(stat);
                            validCommand = true;
                            commandInfo = EnumChatFormatting.GREEN + "[" + stat.getDisplayName(androidPlayer, stat.maxLevel()) + "]" + EnumChatFormatting.RESET + " removed from " + sender.getCommandSenderName();
                        }
                    }

                    if (validCommand) {
                        androidPlayer.sync(EnumSet.allOf(AndroidPlayer.DataType.class), false);
                        sender.addChatMessage(new ChatComponentText(EnumChatFormatting.GOLD + "[" + Reference.MOD_NAME + "] " + EnumChatFormatting.RESET + commandInfo));
                        return;
                    }
                }
            }
        }

        sender.addChatMessage(new ChatComponentText("Invalid Android Command. Use /help to learn more."));
    }

    @Override
    public List addTabCompletionOptions(ICommandSender commandSender, String[] parameters) {
        List<String> commands = new ArrayList<>();

        if (parameters.length == 2) {
            if (parameters[0].equalsIgnoreCase("set")) {
                commands.add("true");
                commands.add("false");
            } else if (parameters[0].equalsIgnoreCase("stats")) {
                commands.add("reset");
            } else if (parameters[0].equalsIgnoreCase("unlock")) {
                commands.addAll(MatterOverdrive.statRegistry.getStats().stream().map(IBionicStat::getUnlocalizedName).collect(Collectors.toList()));
            } else if (parameters[0].equalsIgnoreCase("forget")) {
                commands.addAll(MatterOverdrive.statRegistry.getStats().stream().map(IBionicStat::getUnlocalizedName).collect(Collectors.toList()));
            }
        } else {
            commands.add("set");
            commands.add("stats");
            commands.add("unlock");
            commands.add("forget");
        }
        return commands;
    }

    @Override
    public boolean isUsernameIndex(String[] params, int index) {
        return index == 2;
    }
}
