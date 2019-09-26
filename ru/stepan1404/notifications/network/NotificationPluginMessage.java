package ru.stepan1404.notifications.network;

import io.netty.buffer.*;
import cpw.mods.fml.common.network.*;
import cpw.mods.fml.common.network.simpleimpl.*;
import ru.stepan1404.notifications.model.*;
import ru.stepan1404.notifications.gui.*;

public class NotificationPluginMessage implements IMessage, IMessageHandler<NotificationPluginMessage, IMessage>
{
    private static final String TYPE = "B";
    private static final int DELAY = 10;
    private String text;
    
    public NotificationPluginMessage() {
    }
    
    public NotificationPluginMessage(final String text) {
        this.text = text;
    }
    
    public void fromBytes(final ByteBuf buf) {
        this.text = ByteBufUtils.readUTF8String(buf).substring(1);
    }
    
    public void toBytes(final ByteBuf buf) {
        ByteBufUtils.writeUTF8String(buf, this.text);
    }
    
    public IMessage onMessage(final NotificationPluginMessage message, final MessageContext ctx) {
        final String messageText = message.text;
        final Notification model = new Notification("B", messageText, 10);
        NotificationGui.getInstance().getNotificationList().add(model);
        return null;
    }
}
