package ru.stepan1404.notifications.event;

import net.minecraftforge.client.event.*;
import ru.stepan1404.notifications.gui.*;
import cpw.mods.fml.common.eventhandler.*;
import cpw.mods.fml.relauncher.*;

public class RenderEvent
{
    private static boolean minecraftResized;
    private static int oldWidth;
    private static int oldHeight;
    
    @SubscribeEvent
    @SideOnly(Side.CLIENT)
    public void onRender(final RenderGameOverlayEvent.Post event) {
        if (event.type == RenderGameOverlayEvent.ElementType.ALL) {
            if (RenderEvent.oldWidth != event.resolution.getScaledWidth() || RenderEvent.oldHeight != event.resolution.getScaledHeight()) {
                RenderEvent.oldWidth = event.resolution.getScaledWidth();
                RenderEvent.oldHeight = event.resolution.getScaledHeight();
                RenderEvent.minecraftResized = true;
            }
            else {
                RenderEvent.minecraftResized = false;
            }
            NotificationGui.getInstance().drawList();
        }
    }
    
    public static boolean isMinecraftResized() {
        return RenderEvent.minecraftResized;
    }
    
    static {
        RenderEvent.minecraftResized = false;
    }
}
