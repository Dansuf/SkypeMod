package net.golui.skypemod.win;

import java.io.IOException;

import net.golui.skypemod.SkypeCommunicator;
import net.golui.skypemod.gui.GuiModifiedChat;
import net.minecraft.client.entity.EntityClientPlayerMP;
import net.minecraft.client.gui.GuiNewChat;

public class WinSkypeCommunicator extends SkypeCommunicator {

	@Override
	public void processCommand(EntityClientPlayerMP par1EntityClientPlayerMP,
			String[] par2ArrayOfStr) {
		Thread thread = new ProcessCommandThread(par1EntityClientPlayerMP, par2ArrayOfStr);
		thread.start();
	}

	@Override
	public void sendMessage(GuiNewChat chat, String chatId, String message) {
		OutputStreamHelper.sendMessage(chatId, message);
		((GuiModifiedChat)chat).addMessageToCurrentChat("Me: " + message);
	}


	@Override
	public void init() {
		try 
		{
		ProcessBuilder builder = new ProcessBuilder();
		builder.command("D:\\Tomek\\Projekty\\VS\\WindowsApplication1\\WindowsApplication1\\bin\\x86\\Debug\\WindowsApplication1.exe");
		builder.redirectErrorStream(true);
			SkypeCommunicator.getCommunicator().p = builder.start();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		thread = new InputStreamHandler();
		thread.start();
		OutputStreamHelper.init();
		
	}

}
