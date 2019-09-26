package ru.stepan1404.notifications.commands;

import net.minecraft.command.*;
import ru.stepan1404.notifications.*;
import ru.stepan1404.notifications.network.*;
import cpw.mods.fml.common.network.simpleimpl.*;
import net.minecraft.util.*;

public class CommandNotifications extends CommandBase
{
    public String getCommandName() {
        return "nfc";
    }
    
    public String getCommandUsage(final ICommandSender p_71518_1_) {
        return "\\nfc reload - Reload messages.\n\\nfc send [letterType] [wait] [message] - Send message to player";
    }
    
    public void processCommand(final ICommandSender sender, final String[] args) {
        if (args.length == 1) {
            final String letterType = args[0];
            if ("reload".equalsIgnoreCase(letterType)) {
                NotificationsMod.getMod().getConfig().launch();
                return;
            }
        }
        if (args.length >= 4 && "send".equalsIgnoreCase(args[0])) {
            try {
                final String letterType = args[1];
                final int delay = Integer.parseInt(args[2]);
                final StringBuilder messageText = new StringBuilder();
                for (int i = 3; i < args.length; ++i) {
                    messageText.append(args[i]).append(" ");
                }
                final String message = letterType + ":" + delay + ":" + messageText.toString().trim();
                NotificationsMod.getMod().getNetwork().sendToAll((IMessage)new NotificationMessage(message));
                return;
            }
            catch (Throwable var7) {
                var7.printStackTrace();
            }
        }
        sender.addChatMessage((IChatComponent)new ChatComponentText(EnumChatFormatting.DARK_RED + "Wrong syntax."));
    }

}
