package net.golui.skypemod.win;

import java.util.List;
import net.golui.skypemod.SkypeChat;
import net.golui.skypemod.SkypeDatabase;
import net.golui.skypemod.SkypeUser;
import net.golui.skypemod.gui.GuiModifiedChat;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityClientPlayerMP;

public class ProcessCommandThread extends Thread
{
	EntityClientPlayerMP	player;
	String[]				params;
	
	public ProcessCommandThread(EntityClientPlayerMP par1EntityClientPlayerMP, String[] par2ArrayOfStr)
	{
		this.player = par1EntityClientPlayerMP;
		this.params = par2ArrayOfStr;
	}
	
	@Override
	public void run()
	{
		try
		{
			if (this.params[0].equals("new"))
			{
				
				if (this.params.length < 2)
					Minecraft.getMinecraft().thePlayer.sendChatToPlayer("Usage: /skype new <user/chat>");
				else
				{
					SkypeChat skypeChat = SkypeDatabase.getInstance().getChat(this.params[1]);
					if (skypeChat == null)
					{
						this.player.sendChatToPlayer("Typed chat or username could not be found!");
						return;
					}
					
					GuiModifiedChat chat = (GuiModifiedChat) Minecraft.getMinecraft().ingameGUI.getChatGUI();
					int tabId = chat.getTabId(skypeChat);
					if (tabId == -1)
					{
						tabId = chat.addTab(skypeChat);
						chat.setTabAsActive(tabId);
						chat.addMessageToCurrentChat("Tab created successfully");
					} else
						this.player.sendChatToPlayer("Typed chat or username has been already opened!");
				}
			} else if (this.params[0].equals("onlinelist") || this.params[0].equals("online"))
			{
				List<SkypeUser> friends = SkypeDatabase.getInstance().getFriends();
				this.player.sendChatToPlayer("\u00a76Users online:");
				for (SkypeUser friend : friends)
				{
					String status = "";
					switch (friend.getStatus())
					{
						case 2:
							status = "\u00a7e<AFK>\u00a7f";
							break;
						case 4:
							status = "\u00a7c<Busy>\u00a7f";
							break;
					}
					if (friend.getStatus() != 0)
						this.player.sendChatToPlayer(status + friend.getDisplayName());
				}
			} else if (this.params[0].equals("close"))
			{
				if (((GuiModifiedChat) Minecraft.getMinecraft().ingameGUI.getChatGUI()).getActiveTab() != 0)
					((GuiModifiedChat) Minecraft.getMinecraft().ingameGUI.getChatGUI()).closeCurrentTab();
				else
					this.player.sendChatToPlayer("You can not close the Minecraft Tab!");
			}
		} catch (IndexOutOfBoundsException e)
		{
			this.player.sendChatToPlayer("Available commands:");
			this.player.sendChatToPlayer("   send");
			this.player.sendChatToPlayer("   onlinelist");
			this.player.sendChatToPlayer("   new");
		}
	}
}
