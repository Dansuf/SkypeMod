package net.golui.skypemod.win;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import net.golui.skypemod.SkypeCommunicator;

public class OutputStreamHelper
{
	
	private static BufferedWriter	writer;
	
	public static void init()
	{
		writer = new BufferedWriter(new OutputStreamWriter(SkypeCommunicator.getCommunicator().p.getOutputStream()));
	}
	
	public static void sendMessage(String chatId, String message)
	{
		
		String input = "M" + chatId + "\n" + message + "\n";
		try
		{
			writer.write(input);
			writer.flush();
		} catch (IOException e)
		{
			e.printStackTrace();
		}
	}
	
	public static void changeInGameStatus(boolean status)
	{
		try
		{
			if (status)
				writer.write("1");
			else
				writer.write("0");
			writer.flush();
		} catch (IOException e)
		{
			e.printStackTrace();
		}
	}
	
	public static void shutdownCommunicator()
	{
		try
		{
			writer.write("E");
			writer.flush();
		} catch (IOException e)
		{
			e.printStackTrace();
		}
	}
}
