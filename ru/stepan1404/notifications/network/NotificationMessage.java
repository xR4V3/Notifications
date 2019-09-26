package ru.stepan1404.notifications.network;

import io.netty.buffer.*;
import cpw.mods.fml.common.network.*;
import cpw.mods.fml.common.network.simpleimpl.*;
import ru.stepan1404.notifications.model.*;
import ru.stepan1404.notifications.gui.*;

public class NotificationMessage implements IMessage, IMessageHandler<NotificationMessage, IMessage>
{
    private String text;
    
    public NotificationMessage() {
    }
    
    public NotificationMessage(final String text) {
        this.text = text;
    }
    
    public void fromBytes(final ByteBuf buf) {
        this.text = ByteBufUtils.readUTF8String(buf);
    }
    
    public void toBytes(final ByteBuf buf) {
        ByteBufUtils.writeUTF8String(buf, this.text);
    }
    
    public IMessage onMessage(final NotificationMessage message, final MessageContext ctx) {
        try {
            final String[] args = message.text.split(":");
            final String letterType = args[0];
            final String messageText = args[2];
            final int lifetime = Integer.parseInt(args[1]);
            final Notification model = new Notification(letterType, messageText, lifetime);
            NotificationGui.getInstance().getNotificationList().add(model);
        }
        catch (Throwable e) {
            return null;
        }
        return null;
    }
}
