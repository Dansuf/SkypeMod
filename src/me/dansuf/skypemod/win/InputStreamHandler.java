package net.golui.skypemod.win;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;

import net.golui.skypemod.Helper;
import net.golui.skypemod.SkypeChat;
import net.golui.skypemod.SkypeCommunicator;
import net.golui.skypemod.SkypeDatabase;
import net.golui.skypemod.SkypeUser;
import net.golui.skypemod.gui.GuiModifiedChat;
import net.minecraft.client.Minecraft;


class InputStreamHandler extends Thread {
  InputStream is;
 
 
  public void run() {
    try 
    {
		this.is = SkypeCommunicator.getCommunicator().p.getInputStream();
    	
		int c;
		while ((c = is.read()) != -1)
			//os.print((char)c);
			switch((char)c)
			{
			case 'U':
          		newUser();
          		break;
          	case 'C':
          		newChat();
          		break;
          	case 'M':
          		newMessage();
          		break;
          	case 'W':
          		deleteUser();
          		break;
          	case 'E':
          		handleException();
          		break;
			}
    } catch (IOException e) {
    	e.printStackTrace();
    } catch (SkypeCommunicatorException e) {
		e.printStackTrace();
		if(Minecraft.getMinecraft().thePlayer != null)
			Minecraft.getMinecraft().thePlayer.sendChatToPlayer("\u00a7cSkypeCommunicator has got a problem. Please send the ForgeModLoader-client-0.log file to the modder.");
	}
  }
  
  public void newUser()
  {
	  try {
		String[] params = new String[]{"","",""};
		int c;
		for(int i = 0; i < params.length; i++)
		{
			while((c = is.read()) != '\n' && c != '\r')
			{
				params[i] += (char) c;
			}
			is.read();
		}
		//System.out.println("Loaded user " + params[0] + " with DisplayName " + params[1] + " and status " + params[2]);
		SkypeDatabase.getInstance().addFriend(new SkypeUser(params[0], params[1], Integer.parseInt(params[2]) ));
		
	} catch (IOException e) {
		e.printStackTrace();
	}
  }
  
  public void deleteUser()
  {
	  try {
		String name = "";
		int c;
		while((c = is.read()) != '\n' && c != '\r')
		{
			name += (char) c;
		}
		is.read();
		//System.out.println("Deleted user " + name);
		SkypeDatabase.getInstance().removeFriend(name);
		
	} catch (IOException e) {
		e.printStackTrace();
	}
  }
  
  public void newChat()
  {
	  try {
		String[] params = new String[]{"",""};
		int c;
		for(int i = 0; i < params.length; i++)
		{
			while((c = is.read()) != '\n' && c != '\r')
			{
				params[i] += (char) c;
			}
			is.read();
		}
		//System.out.println("Loaded chat " + params[0] + " with DisplayName " + params[1]);
		SkypeDatabase.getInstance().addChat(new SkypeChat(params[1], params[0]));
		
	} catch (IOException e) {
		e.printStackTrace();
	}
  }
  
  public void newMessage()
  {
	  try {
		String[] params = new String[]{"",""};
		int c;
		for(int i = 0; i < params.length; i++)
		{
			while((c = is.read()) != '\n' && c != '\r')
			{
				params[i] += (char) c;
			}
			is.read();
		}
		//System.out.println("Loaded message " + params[1] + " from chat " + params[0]);
		
		GuiModifiedChat chat = (GuiModifiedChat) Minecraft.getMinecraft().ingameGUI.getChatGUI();
		SkypeChat newChat =  SkypeDatabase.getInstance().getChat(params[0]);
		if(newChat == null)
			newChat = new SkypeChat(params[0], params[0]);
		
		int tabId = chat.getTabId(newChat);
		if(tabId == -1)
		{
			tabId = chat.addTab(newChat);
		}
		chat.addMessage(tabId, params[1]);
		
	} catch (IOException e) {
		e.printStackTrace();
	}
  }
  
  public void handleException() throws SkypeCommunicatorException
  {
	  Writer writer = new StringWriter();

	  char[] buffer = new char[1024];
	  try {
		  Reader reader = new BufferedReader(
				  new InputStreamReader(is, "UTF-8"));
		  int n;
		  while ((n = reader.read(buffer)) != -1) {
			  writer.write(buffer, 0, n);
		  }            
	  } catch (UnsupportedEncodingException e) {
		e.printStackTrace();
	} catch (IOException e) {
		e.printStackTrace();
	}
	  String exception = writer.toString();
	  throw new SkypeCommunicatorException(exception);
  }
}
