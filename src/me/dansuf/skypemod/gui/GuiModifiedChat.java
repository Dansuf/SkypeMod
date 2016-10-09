package net.golui.skypemod.gui;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import net.golui.skypemod.SkypeChat;
import net.golui.skypemod.SkypeDatabase;
import net.golui.skypemod.asm.SkypeModModContainer;
import net.golui.skypemod.win.OutputStreamHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ChatClickData;
import net.minecraft.client.gui.ChatLine;
import net.minecraft.client.gui.GuiChat;
import net.minecraft.client.gui.GuiNewChat;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.util.MathHelper;
import net.minecraft.util.StringTranslate;
import net.minecraft.util.StringUtils;
import org.lwjgl.opengl.GL11;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class GuiModifiedChat extends GuiNewChat
{
	/** The Minecraft instance. */
	private final Minecraft	mc;
	
	/** A list of messages previously sent through the chat GUI */
	private final List		sentMessages	= new ArrayList();
	
	/** Chat lines to be displayed in the chat box */
	private final List		chatLines		= new ArrayList();
	private final List		messages		= new ArrayList();
	private int				field_73768_d	= 0;
	private boolean			field_73769_e	= false;
	
	/** Some custom testing variables */
	private int				activeTab;
	
	public GuiModifiedChat(Minecraft par1Minecraft)
	{
		super(par1Minecraft);
		this.mc = par1Minecraft;
	}
	
	public int addTab(SkypeChat par1Chat)
	{
		SkypeDatabase.getInstance().getActiveChats().add(par1Chat);
		this.messages.add(new ArrayList());
		SkypeModModContainer.tabs.addButton(par1Chat.getName());
		return SkypeDatabase.getInstance().getActiveChats().size() - 1;
	}
	
	public void closeCurrentTab()
	{
		if (this.activeTab != 0)
		{
			this.setTabAsActive(this.activeTab - 1);
			SkypeDatabase.getInstance().getActiveChats().remove(this.activeTab + 1);
			this.messages.remove(this.activeTab + 1);
			SkypeModModContainer.tabs.closeTab(this.activeTab + 1);
		}
	}
	
	public void setTabAsActive(int par1Id)
	{
		SkypeModModContainer.tabs.setTabAsActive(par1Id);
		List<SkypeChat> chats = SkypeDatabase.getInstance().getActiveChats();
		this.activeTab = par1Id;
	}
	
	public int getTabId(String par1Name)
	{
		List<SkypeChat> chats = SkypeDatabase.getInstance().getActiveChats();
		for (int i = 0; i < chats.size(); i++)
		{
			if (chats.get(i).getName().contains(par1Name))
				return i;
		}
		return -1;
	}
	
	public int getTabId(SkypeChat par1Chat)
	{
		List<SkypeChat> chats = SkypeDatabase.getInstance().getActiveChats();
		for (int i = 0; i < chats.size(); i++)
		{
			if (chats.get(i).getName().equals(par1Chat.getName()))
				return i;
		}
		return -1;
	}
	
	public int getActiveTab()
	{
		return this.activeTab;
	}
	
	public void nextTab()
	{
		if (this.activeTab + 1 < SkypeDatabase.getInstance().getActiveChats().size())
			this.setTabAsActive(this.activeTab + 1);
		else
			this.setTabAsActive(0);
		this.resetScroll();
	}
	
	public void prevTab()
	{
		if (this.activeTab - 1 < 0)
			this.setTabAsActive(SkypeDatabase.getInstance().getActiveChats().size() - 1);
		else
			this.setTabAsActive(this.activeTab - 1);
		this.resetScroll();
	}
	
	public void addMessage(int par1Id, String par2Message)
	{
		boolean flag1 = this.getChatOpen();
		boolean flag2 = true;
		int par3 = this.mc.ingameGUI.getUpdateCounter();
		
		Iterator iterator = this.mc.fontRenderer.listFormattedStringToWidth(par2Message,
				MathHelper.floor_float(this.func_96126_f() / this.func_96131_h())).iterator();
		
		while (iterator.hasNext())
		{
			String s1 = (String) iterator.next();
			
			if (flag1 && this.field_73768_d > 0)
			{
				this.field_73769_e = true;
				this.scroll(1);
			}
			
			if (!flag2)
			{
				s1 = " " + s1;
			}
			
			flag2 = false;
			((List) this.messages.get(par1Id)).add(0, new ChatLine(par3, s1, 0));
		}
		
		while (((List) this.messages.get(par1Id)).size() > 100)
		{
			((List) this.messages.get(par1Id)).remove(((List) this.messages.get(0)).size() - 1);
		}
		
		if (par1Id != this.activeTab)
		{
			SkypeModModContainer.tabs.setNewMessageState(par1Id);
		}
		
	}
	
	public void addMessageToCurrentChat(String par1Message)
	{
		this.addMessage(this.activeTab, par1Message);
	}
	
	@Override
	public void drawChat(int par1)
	{
		if (this.mc.gameSettings.chatVisibility != 2)
		{
			int j = this.func_96127_i();
			boolean flag = false;
			int k = 0;
			int l = ((List) this.messages.get(this.activeTab)).size();
			float f = this.mc.gameSettings.chatOpacity * 0.9F + 0.1F;
			
			if (l > 0)
			{
				if (this.getChatOpen())
				{
					flag = true;
				}
				
				float f1 = this.func_96131_h();
				int i1 = MathHelper.ceiling_float_int(this.func_96126_f() / f1);
				GL11.glPushMatrix();
				GL11.glTranslatef(2.0F, 20.0F, 0.0F);
				GL11.glScalef(f1, f1, 1.0F);
				int j1;
				int k1;
				int l1;
				
				// Modification starts
				// drawRect(0, -this.func_96133_g(), i1 + 4, 0, (int)( 255*f /
				// 2) << 24);
				SkypeModModContainer.tabs.drawScreen();
				// Modification ends
				
				for (j1 = 0; j1 + this.field_73768_d < ((List) this.messages.get(this.activeTab)).size() && j1 < j; ++j1)
				{
					ChatLine chatline = (ChatLine) ((List) this.messages.get(this.activeTab)).get(j1 + this.field_73768_d);
					
					if (chatline != null)
					{
						k1 = par1 - chatline.getUpdatedCounter();
						
						if (k1 < 200 || flag)
						{
							double d0 = k1 / 200.0D;
							d0 = 1.0D - d0;
							d0 *= 10.0D;
							
							if (d0 < 0.0D)
							{
								d0 = 0.0D;
							}
							
							if (d0 > 1.0D)
							{
								d0 = 1.0D;
							}
							
							d0 *= d0;
							l1 = (int) (255.0D * d0);
							
							if (flag)
							{
								l1 = 255;
							}
							
							l1 = (int) (l1 * f);
							++k;
							
							if (l1 > 3)
							{
								byte b0 = 0;
								int i2 = -j1 * 9;
								drawRect(b0, i2 - 9, b0 + i1 + 4, i2, l1 / 2 << 24);
								GL11.glEnable(GL11.GL_BLEND);
								String s = chatline.getChatLineString();
								
								if (!this.mc.gameSettings.chatColours)
								{
									s = StringUtils.stripControlCodes(s);
								}
								
								this.mc.fontRenderer.drawStringWithShadow(s, b0, i2 - 8, 16777215 + (l1 << 24));
							}
						}
					}
				}
				
				if (flag)
				{
					j1 = this.mc.fontRenderer.FONT_HEIGHT;
					GL11.glTranslatef(-3.0F, 0.0F, 0.0F);
					int j2 = l * j1 + l;
					k1 = k * j1 + k;
					int k2 = this.field_73768_d * k1 / l;
					int l2 = k1 * k1 / j2;
					
					if (j2 != k1)
					{
						l1 = k2 > 0 ? 170 : 96;
						int i3 = this.field_73769_e ? 13382451 : 3355562;
						drawRect(0, -k2, 2, -k2 - l2, i3 + (l1 << 24));
						drawRect(2, -k2, 1, -k2 - l2, 13421772 + (l1 << 24));
					}
				}
				
				GL11.glPopMatrix();
			}
		}
	}
	
	/**
	 * Clears the chat.
	 */
	@Override
	public void clearChatMessages()
	{
		this.chatLines.clear();
		this.sentMessages.clear();
		
		this.activeTab = 0;
		this.messages.clear();
		this.messages.add(new ArrayList());
		SkypeDatabase.getInstance().getActiveChats().clear();
		SkypeDatabase.getInstance().getActiveChats().add(new SkypeChat("MC", ""));
		SkypeModModContainer.tabs = new GuiTabs();
		SkypeModModContainer.tabs.addButton("MC");
		SkypeModModContainer.tabs.setTabAsActive(this.activeTab);
		OutputStreamHelper.changeInGameStatus(true);
	}
	
	/**
	 * takes a String and prints it to chat
	 */
	@Override
	public void printChatMessage(String par1Str)
	{
		this.printChatMessageWithOptionalDeletion(par1Str, 0);
	}
	
	/**
	 * prints the String to Chat. If the ID is not 0, deletes an existing Chat
	 * Line of that ID from the GUI
	 */
	@Override
	public void printChatMessageWithOptionalDeletion(String par1Str, int par2)
	{
		this.func_96129_a(par1Str, par2, this.mc.ingameGUI.getUpdateCounter(), false);
		this.mc.getLogAgent().logInfo("[CHAT] " + par1Str);
		
		if (this.activeTab != 0)
		{
			SkypeModModContainer.tabs.setNewMessageState(0);
		}
	}
	
	private void func_96129_a(String par1Str, int par2, int par3, boolean par4)
	{
		boolean flag1 = this.getChatOpen();
		boolean flag2 = true;
		
		if (par2 != 0)
		{
			this.deleteChatLine(par2);
		}
		
		Iterator iterator = this.mc.fontRenderer.listFormattedStringToWidth(par1Str,
				MathHelper.floor_float(this.func_96126_f() / this.func_96131_h())).iterator();
		
		while (iterator.hasNext())
		{
			String s1 = (String) iterator.next();
			
			if (flag1 && this.field_73768_d > 0)
			{
				this.field_73769_e = true;
				this.scroll(1);
			}
			
			if (!flag2)
			{
				s1 = " " + s1;
			}
			
			flag2 = false;
			((List) this.messages.get(0)).add(0, new ChatLine(par3, s1, par2));
		}
		
		while (((List) this.messages.get(0)).size() > 100)
		{
			((List) this.messages.get(0)).remove(((List) this.messages.get(0)).size() - 1);
		}
		
		if (!par4)
		{
			this.chatLines.add(0, new ChatLine(par3, par1Str.trim(), par2));
			
			while (this.chatLines.size() > 100)
			{
				this.chatLines.remove(this.chatLines.size() - 1);
			}
		}
	}
	
	@Override
	public void func_96132_b()
	{
		((List) this.messages.get(0)).clear();
		this.resetScroll();
		
		for (int i = this.chatLines.size() - 1; i >= 0; --i)
		{
			ChatLine chatline = (ChatLine) this.chatLines.get(i);
			this.func_96129_a(chatline.getChatLineString(), chatline.getChatLineID(), chatline.getUpdatedCounter(), true);
		}
	}
	
	/**
	 * Gets the list of messages previously sent through the chat GUI
	 */
	@Override
	public List getSentMessages()
	{
		return this.sentMessages;
	}
	
	/**
	 * Adds this string to the list of sent messages, for recall using the
	 * up/down arrow keys
	 */
	@Override
	public void addToSentMessages(String par1Str)
	{
		if (this.sentMessages.isEmpty() || !((String) this.sentMessages.get(this.sentMessages.size() - 1)).equals(par1Str))
		{
			this.sentMessages.add(par1Str);
		}
	}
	
	/**
	 * Resets the chat scroll (executed when the GUI is closed)
	 */
	@Override
	public void resetScroll()
	{
		this.field_73768_d = 0;
		this.field_73769_e = false;
	}
	
	/**
	 * Scrolls the chat by the given number of lines.
	 */
	@Override
	public void scroll(int par1)
	{
		this.field_73768_d += par1;
		int j = ((List) this.messages.get(this.activeTab)).size();
		
		if (this.field_73768_d > j - this.func_96127_i())
		{
			this.field_73768_d = j - this.func_96127_i();
		}
		
		if (this.field_73768_d <= 0)
		{
			this.field_73768_d = 0;
			this.field_73769_e = false;
		}
	}
	
	@Override
	public ChatClickData func_73766_a(int par1, int par2)
	{
		if (!this.getChatOpen())
		{
			return null;
		} else
		{
			ScaledResolution scaledresolution = new ScaledResolution(this.mc.gameSettings, this.mc.displayWidth, this.mc.displayHeight);
			int k = scaledresolution.getScaleFactor();
			float f = this.func_96131_h();
			int l = par1 / k - 3;
			int i1 = par2 / k - 25;
			l = MathHelper.floor_float(l / f);
			i1 = MathHelper.floor_float(i1 / f);
			
			if (l >= 0 && i1 >= 0)
			{
				int j1 = Math.min(this.func_96127_i(), ((List) this.messages.get(0)).size());
				
				if (l <= MathHelper.floor_float(this.func_96126_f() / this.func_96131_h()) && i1 < this.mc.fontRenderer.FONT_HEIGHT * j1 + j1)
				{
					int k1 = i1 / (this.mc.fontRenderer.FONT_HEIGHT + 1) + this.field_73768_d;
					return new ChatClickData(this.mc.fontRenderer, (ChatLine) ((List) this.messages.get(this.activeTab)).get(k1), l, i1
							- (k1 - this.field_73768_d) * this.mc.fontRenderer.FONT_HEIGHT + k1);
				} else
				{
					return null;
				}
			} else
			{
				return null;
			}
		}
	}
	
	/**
	 * Adds a message to the chat after translating to the client's locale.
	 */
	@Override
	public void addTranslatedMessage(String par1Str, Object... par2ArrayOfObj)
	{
		this.printChatMessage(StringTranslate.getInstance().translateKeyFormat(par1Str, par2ArrayOfObj));
	}
	
	/**
	 * @return {@code true} if the chat GUI is open
	 */
	@Override
	public boolean getChatOpen()
	{
		return this.mc.currentScreen instanceof GuiChat;
	}
	
	/**
	 * finds and deletes a Chat line by ID
	 */
	@Override
	public void deleteChatLine(int par1)
	{
		Iterator iterator = ((List) this.messages.get(0)).iterator();
		ChatLine chatline;
		
		do
		{
			if (!iterator.hasNext())
			{
				iterator = this.chatLines.iterator();
				
				do
				{
					if (!iterator.hasNext())
					{
						return;
					}
					
					chatline = (ChatLine) iterator.next();
				} while (chatline.getChatLineID() != par1);
				
				iterator.remove();
				return;
			}
			
			chatline = (ChatLine) iterator.next();
		} while (chatline.getChatLineID() != par1);
		
		iterator.remove();
	}
	
	@Override
	public int func_96126_f()
	{
		return func_96128_a(this.mc.gameSettings.chatWidth);
	}
	
	@Override
	public int func_96133_g()
	{
		return func_96130_b(this.getChatOpen() ? this.mc.gameSettings.chatHeightFocused : this.mc.gameSettings.chatHeightUnfocused);
	}
	
	@Override
	public float func_96131_h()
	{
		return this.mc.gameSettings.chatScale;
	}
	
	@Override
	public int func_96127_i()
	{
		return this.func_96133_g() / 9;
	}
}
