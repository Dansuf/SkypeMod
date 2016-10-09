package net.golui.skypemod;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringWriter;
import java.io.Writer;

import net.golui.skypemod.gui.GuiModifiedChat;
import net.golui.skypemod.win.OutputStreamHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityClientPlayerMP;

public class Helper {
	
	/*public static String makeMessage(String author, String content)
	{
		
		SkypeUser[] friends = SkypeDatabase.getInstance().getFriends();
		for(int i = 0; i < friends.length; i++)
		{
			if(friends[i].getUsername().equals(author))
				author = friends[i].getDisplayName();
		}
		return "<" + author + "> " + content;
	}*/
	
	public static void shutdown()
	{
		if(SkypeCommunicator.getCommunicator() != null)
		{
			if(SkypeCommunicator.getCommunicator().p != null)
				OutputStreamHelper.shutdownCommunicator();
			if(SkypeCommunicator.getCommunicator().thread != null)
				SkypeCommunicator.getCommunicator().thread.interrupt();
		}
	}
	
	 public static boolean sendChatMessage(String par1Str, EntityClientPlayerMP player)
	    {
	    	if(par1Str.startsWith("/skype "))
	    	{
	    		SkypeCommunicator.getCommunicator().processCommand(player, par1Str.substring(7).split(" "));
	    		
	    	}
	    	else
	    	{
	    		GuiModifiedChat chat = (GuiModifiedChat) Minecraft.getMinecraft().ingameGUI.getChatGUI();
	    		if(chat.getActiveTab()!= 0)
	    		{
	    			SkypeCommunicator.getCommunicator().sendMessage(chat, SkypeDatabase.getInstance().getActiveChats().get(chat.getActiveTab()).getId(), par1Str);
	    		} else
	    			return true;
	    	}
	    	return false;
	    }
	 
	 
}
