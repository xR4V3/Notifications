package ru.stepan1404.notifications.utils;

import cpw.mods.fml.common.*;
import net.minecraftforge.common.*;

public class Events
{
    public static void registerEvent(final Object o) {
        FMLCommonHandler.instance().bus().register(o);
        MinecraftForge.EVENT_BUS.register(o);
    }
}
