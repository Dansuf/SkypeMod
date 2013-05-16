package net.golui.skypemod.gui;

import org.lwjgl.input.Mouse;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiNewChat;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.util.StatCollector;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class GuiTabs extends GuiScreen{
	
	public final int DEFAULT_TAB_WIDTH = 50;
	public final int MARGIN_WIDTH = 5;
	public final int TAB_WITH_MARGIN_WIDTH = DEFAULT_TAB_WIDTH + MARGIN_WIDTH;
	
	public int activeTab = 0;
	public float timer = 5;
	//public int tabWidth = DEFAULT_TAB_WIDTH;

	 public GuiTabs()
	 {
		 this.mc = Minecraft.getMinecraft();
	 }
	 
	 public void showTabs()
	 {
		 timer = 5;
	 }
	 
	 public void addButton(String text)
	 {
		 this.buttonList.add(new GuiTab(this.buttonList.size(), 1 + buttonList.size()*TAB_WITH_MARGIN_WIDTH , text, this.mc));
		 showTabs(); 
		 
	 }
	 
	 public void setTabAsActive(int id)
	 {
		 ((GuiTab)this.buttonList.get(activeTab)).setState(GuiTab.NORMAL);
		 ((GuiTab)this.buttonList.get(id)).setState(GuiTab.ACTIVE);
		 activeTab = id;
	 }
	 
	 public void setNewMessageState(int id)
	 {
		 ((GuiTab)this.buttonList.get(id)).setState(GuiTab.NEW_MESSAGES);
		 showTabs();
	 }
	 
	 public void closeTab(int id)
	 {
		 this.buttonList.remove(id);
	 }
	 
	 private int updateTabWidth()
	 {
		 int tabWidth;
		 if(GuiNewChat.func_96128_a(this.mc.gameSettings.chatWidth)/this.mc.gameSettings.chatScale < this.buttonList.size()*TAB_WITH_MARGIN_WIDTH - MARGIN_WIDTH)
		 {
			 tabWidth = (int) ((GuiNewChat.func_96128_a(this.mc.gameSettings.chatWidth)/this.mc.gameSettings.chatScale + MARGIN_WIDTH)/ this.buttonList.size() - MARGIN_WIDTH);
		 } else
		 {
			 tabWidth = DEFAULT_TAB_WIDTH;
		 }
		 for(int i = 0; i < this.buttonList.size(); i++)
		 {
			 ((GuiTab) buttonList.get(i)).setXPosition(i*(tabWidth+MARGIN_WIDTH));
		 }
		 return tabWidth;
	 }
	 
	 
	 public void drawScreen()
	 {
		 int width = updateTabWidth();
		 int opacity = (int)((float)255 * (this.mc.gameSettings.chatOpacity * 0.9F + 0.1F));
		 ScaledResolution scaledresolution = new ScaledResolution(this.mc.gameSettings, this.mc.displayWidth, this.mc.displayHeight);
         int sh = scaledresolution.getScaledHeight();
		 int mouseX = Mouse.getX() * scaledresolution.getScaledWidth() / this.mc.displayWidth;
         int mouseY = sh - Mouse.getY() * sh / this.mc.displayHeight - 1;
         
         if(this.timer < 1)
         {
        	 opacity *= this.timer;
        	 if(opacity > 10)
        	 {
        		 this.timer -= 0.016;
        	 } else
        		 opacity = 10;
         } else
         {
        	 this.timer -= 0.016;
         }
         
         for (int k = 0; k < this.buttonList.size(); ++k)
         {
             GuiTab guitab = (GuiTab)this.buttonList.get(k);
             guitab.drawButton(this.mc, mouseX, mouseY, width, opacity);
         }
	 }
}
