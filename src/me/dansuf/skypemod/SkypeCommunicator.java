package net.golui.skypemod;

import net.golui.skypemod.SkypeChat;
import net.golui.skypemod.win.WinSkypeCommunicator;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityClientPlayerMP;
import net.minecraft.client.gui.GuiNewChat;

public abstract class SkypeCommunicator {
	
	private static SkypeCommunicator communicator;
	public Process p;
	public Thread thread;
	
	public static void setCommunicator()
	{
		switch(Minecraft.getMinecraft().getOs())
		{
		case WINDOWS:
			communicator = new WinSkypeCommunicator();
			break;
		default:
			System.out.println("SkypeCommunicator currently doesn't support your OS");
				
		}
	}
	
	public static SkypeCommunicator getCommunicator()
	{
		return communicator;
	}

	public abstract void processCommand(EntityClientPlayerMP par1EntityClientPlayerMP, String[] par2ArrayOfStr);
	public abstract void sendMessage(GuiNewChat chat, String chatId, String message);
	//public abstract SkypeChat getChat(String name);
	public abstract void init();
	
}
