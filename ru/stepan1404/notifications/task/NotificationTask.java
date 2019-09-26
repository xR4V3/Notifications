package ru.stepan1404.notifications.task;

import net.minecraft.server.*;
import ru.stepan1404.notifications.*;
import ru.stepan1404.notifications.network.*;
import cpw.mods.fml.common.network.simpleimpl.*;
import java.util.*;

public class NotificationTask extends TimerTask
{
    @Override
    public void run() {
        if (MinecraftServer.getServer().getConfigurationManager().playerEntityList.size() > 0) {
            final Random random = new Random();
            final List<String> messages = NotificationsMod.getMod().getConfig().getMessages();
            final String message = messages.get(random.nextInt(messages.size()));
            NotificationsMod.getMod().getNetwork().sendToAll((IMessage)new NotificationMessage(message));
        }
    }
}
