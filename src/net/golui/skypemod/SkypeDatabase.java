package net.golui.skypemod;

import java.util.ArrayList;
import java.util.List;

public class SkypeDatabase {
	public static SkypeDatabase instance;
	List<SkypeChat> chats;
	List<SkypeUser> friends;
	List<SkypeChat> activeChats;
	
	public List<SkypeChat> getActiveChats() {
		return activeChats;
	}

	public void setActiveChats(List<SkypeChat> activeChats) {
		this.activeChats = activeChats;
	}

	public SkypeDatabase()
	{
		activeChats = new ArrayList<SkypeChat>();
		activeChats.add(new SkypeChat("MC", ""));
		friends = new ArrayList<SkypeUser>();
		chats = new ArrayList<SkypeChat>();
	}
	
	public void addChat(SkypeChat chat)
	{
		this.chats.add(chat);
	}
	
	public List<SkypeChat> getChats()
	{
		return chats;
	}
	
	public SkypeChat getChat(String name)
	{
		for(SkypeChat chat: chats)
		{
			if(chat.id.contains(name) || chat.name.contains(name))
				return chat;
		}
		return null;
	}
	
	public List<SkypeUser> getFriends()
	{
		return friends;
	}
	
	public SkypeUser getFriend(String name)
	{
		for(SkypeUser friend : friends)
		{
			if(friend.username.equals(name) || friend.displayName.equals(name))
			{
				System.out.println("friend " + name + " found.");
				return friend;
			}
		}
		return null;
	}
	
	public void addFriend(SkypeUser friend)
	{
		this.friends.add(friend);
	}
	
	public void removeFriend(String name)
	{
		this.friends.remove(getFriend(name));
	}
	
	public static SkypeDatabase newInstance()
	{
		return instance = new SkypeDatabase();
	}
	
	public static SkypeDatabase getInstance()
	{
		return instance;
	}
}
