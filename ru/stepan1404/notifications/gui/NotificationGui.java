package ru.stepan1404.notifications.gui;

import ru.stepan1404.notifications.model.*;
import org.lwjgl.opengl.*;
import java.util.*;

public class NotificationGui
{
    private static NotificationGui instance;
    private List<Notification> notificationList;
    
    public NotificationGui() {
        this.notificationList = new ArrayList<Notification>();
    }
    
    public void drawList() {
        final Iterator<Notification> iterator = this.notificationList.iterator();
        int translateY = 0;
        while (iterator.hasNext()) {
            final Notification notification = iterator.next();
            if (notification.isShowed()) {
                iterator.remove();
            }
            GL11.glTranslatef(0.0f, (float)translateY, 0.0f);
            notification.draw();
            GL11.glTranslatef(0.0f, (float)(-translateY), 0.0f);
            translateY += 4 + notification.getArea().getHeight();
        }
    }
    
    public static NotificationGui getInstance() {
        return NotificationGui.instance;
    }
    
    public List<Notification> getNotificationList() {
        return this.notificationList;
    }
    
    static {
        NotificationGui.instance = new NotificationGui();
    }
}
