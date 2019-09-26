package ru.stepan1404.notifications.config;

import net.minecraftforge.common.config.*;
import java.io.*;
import java.util.*;

public class Config
{
    private Configuration configuration;
    private int delay;
    private List<String> messages;
    
    public Config(final File file) {
        this.configuration = new Configuration(file);
    }
    
    public void launch() {
        this.load();
        this.setConfigs();
        this.save();
    }
    
    private void load() {
        this.configuration.load();
    }
    
    private void save() {
        this.configuration.save();
    }
    
    private void setConfigs() {
        this.delay = this.configuration.getInt("delay", "general", 60, 1, Integer.MAX_VALUE, "Message delay");
        this.messages = new ArrayList<String>(Arrays.asList(this.configuration.getStringList("messages", "messages", new String[] { "S:10:Lol kek!", "W:10:Kek lol!" }, "Set your announcement! Syntax: \"TYPE:TIME:MESSAGE\". Type - W,I,S. Time - seconds(INT). Message - String.")));
    }
    
    public int getDelay() {
        return this.delay;
    }
    
    public List<String> getMessages() {
        return this.messages;
    }
}
