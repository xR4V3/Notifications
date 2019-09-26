package ru.stepan1404.notifications;

import ru.stepan1404.notifications.config.*;
import cpw.mods.fml.common.network.simpleimpl.*;
import java.io.*;
import cpw.mods.fml.common.*;
import cpw.mods.fml.relauncher.*;
import ru.stepan1404.notifications.event.*;
import ru.stepan1404.notifications.utils.*;
import cpw.mods.fml.common.network.*;
import ru.stepan1404.notifications.network.*;
import ru.stepan1404.notifications.commands.*;
import net.minecraft.command.*;
import cpw.mods.fml.common.event.*;
import ru.stepan1404.notifications.task.*;
import java.util.*;

@Mod(modid = "notifications", name = "Notification", version = "1.0.0")
public class NotificationsMod
{
    @Mod.Instance
    private static NotificationsMod mod;
    private Config config;
    private SimpleNetworkWrapper network;
    private File configFile;
    
    @Mod.EventHandler
    public void onPreInit(final FMLPreInitializationEvent event) {
        this.initModules(event.getSuggestedConfigurationFile());
        if (FMLCommonHandler.instance().getEffectiveSide() == Side.CLIENT) {
            Events.registerEvent(new RenderEvent());
        }
    }
    
    @SuppressWarnings("unchecked")
	private void initModules(final File file) {
        this.configFile = file;
        (this.network = NetworkRegistry.INSTANCE.newSimpleChannel("lp_notifications")).registerMessage((Class)NotificationMessage.class, (Class)NotificationMessage.class, 0, Side.CLIENT);
        this.network.registerMessage((Class)NotificationPluginMessage.class, (Class)NotificationPluginMessage.class, 1, Side.CLIENT);
    }
    
    @Mod.EventHandler
    public void onServerStarting(final FMLServerStartingEvent event) {
        event.registerServerCommand((ICommand)new CommandNotifications());
    }
    
    @Mod.EventHandler
    public void onServerStarted(final FMLServerStartedEvent event) {
        (this.config = new Config(this.configFile)).launch();
        final Timer timer = new Timer();
        timer.scheduleAtFixedRate(new NotificationTask(), 0L, getMod().getConfig().getDelay() * 1000);
    }
    
    public static NotificationsMod getMod() {
        return NotificationsMod.mod;
    }
    
    public Config getConfig() {
        return this.config;
    }
    
    public SimpleNetworkWrapper getNetwork() {
        return this.network;
    }
}
