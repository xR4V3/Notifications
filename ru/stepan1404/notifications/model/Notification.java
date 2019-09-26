package ru.stepan1404.notifications.model;

import ru.stepan1404.notifications.render.*;
import java.util.*;
import net.minecraft.client.*;
import ru.stepan1404.notifications.event.*;
import org.lwjgl.opengl.*;
import net.minecraft.client.gui.*;
import net.minecraft.client.renderer.*;
import net.minecraft.client.renderer.entity.*;
import net.minecraft.item.*;
import org.lwjgl.util.*;
import net.minecraft.init.*;

public class Notification
{
    private static final int maxTranslateX = 144;
    private int translateX;
    private Area area;
    private long lifetime;
    private long lifestart;
    private NotificationType type;
    private List<String> message;
    private boolean showed;
    
    public Notification(final String typeLetter, final String message, final int lifetime) {
        final Minecraft minecraft = Minecraft.getMinecraft();
        final FontRenderer fonts = minecraft.fontRenderer;
        final ScaledResolution resolution = new ScaledResolution(minecraft, minecraft.displayWidth, minecraft.displayHeight);
        this.type = NotificationType.getTypeByLetter(typeLetter);
        this.message = (List<String>)fonts.listFormattedStringToWidth(message, 118);
        this.lifetime = lifetime * 1000;
        this.lifestart = System.currentTimeMillis();
        final int height = ((fonts.FONT_HEIGHT + 2) * this.message.size() + 4 > 20) ? ((fonts.FONT_HEIGHT + 2) * this.message.size() + 4) : 20;
        this.area = new Area(resolution.getScaledWidth(), 8, 144, height);
    }
    
    public void draw() {
        if (RenderEvent.isMinecraftResized()) {
            final Minecraft minecraft = Minecraft.getMinecraft();
            final ScaledResolution resolution = new ScaledResolution(minecraft, minecraft.displayWidth, minecraft.displayHeight);
            this.area = new Area(resolution.getScaledWidth(), this.area.getY(), this.area.getWidth(), this.area.getHeight());
        }
        if (System.currentTimeMillis() > this.lifetime + this.lifestart) {
            if (this.translateX > 0) {
                this.translateX -= 4;
                this.drawNote();
            }
            else {
                this.showed = true;
            }
        }
        else {
            if (this.translateX < 144) {
                this.translateX += 4;
            }
            this.drawNote();
        }
    }
    
    private void drawNote() {
        final int color = Integer.MIN_VALUE;
        GL11.glTranslatef((float)(-this.translateX), 0.0f, 0.0f);
        Gui.drawRect(this.area.getX(), this.area.getY(), this.area.getWidth() + this.area.getX(), this.area.getHeight() + this.area.getY(), color);
        Gui.drawRect(this.area.getX(), this.area.getY(), this.area.getX() + 4, this.area.getHeight() + this.area.getY(), this.type.getColorInt());
        GL11.glEnable(32826);
        RenderHelper.enableGUIStandardItemLighting();
        RenderItem.getInstance().renderItemAndEffectIntoGUI(Minecraft.getMinecraft().fontRenderer, Minecraft.getMinecraft().getTextureManager(), this.type.itemStack, this.area.getX() + 6, this.area.getY() + this.area.getHeight() / 2 - 8);
        RenderItem.getInstance().renderItemOverlayIntoGUI(Minecraft.getMinecraft().fontRenderer, Minecraft.getMinecraft().getTextureManager(), this.type.itemStack, this.area.getX() + 6, this.area.getY() + this.area.getHeight() / 2 - 8);
        RenderHelper.disableStandardItemLighting();
        GL11.glDisable(32826);
        for (int i = 0; i < this.message.size(); ++i) {
            final String s = this.message.get(i);
            int j = 0;
            if (this.area.getHeight() == 20) {
                j += 3;
            }
            Minecraft.getMinecraft().fontRenderer.drawStringWithShadow(s, this.area.getX() + 24, 2 + (i + 1) * (Minecraft.getMinecraft().fontRenderer.FONT_HEIGHT + 2) + j, 16777215);
        }
        GL11.glTranslatef((float)this.translateX, 0.0f, 0.0f);
    }
    
    public Area getArea() {
        return this.area;
    }
    
    public boolean isShowed() {
        return this.showed;
    }
    
    public enum NotificationType
    {
        WARNING(new ItemStack(Items.lava_bucket), new Color(204, 32, 2, 140)), 
        INFO(new ItemStack(Items.feather), new Color(8, 149, 214, 140)), 
        SERVER(new ItemStack(Items.emerald), new Color(83, 63, 175, 140)), 
        SALE(new ItemStack(Items.diamond), new Color(156, 20, 224, 140));
        
        private ItemStack itemStack;
        private Color color;
        
        private NotificationType(final ItemStack itemStack, final Color color) {
            this.itemStack = itemStack;
            this.color = color;
        }
        
        public int getColorInt() {
            return (this.color.getAlpha() << 24) + (this.color.getRed() << 16) + (this.color.getGreen() << 8) + this.color.getBlue();
        }
        
        public static NotificationType getTypeByLetter(final String letter) {
            if (letter.equalsIgnoreCase("W")) {
                return NotificationType.WARNING;
            }
            if (letter.equalsIgnoreCase("S")) {
                return NotificationType.SALE;
            }
            if (letter.equalsIgnoreCase("B")) {
                return NotificationType.SERVER;
            }
            return NotificationType.INFO;
        }
        
        public ItemStack getItemStack() {
            return this.itemStack;
        }
        
        public Color getColor() {
            return this.color;
        }
    }
}
