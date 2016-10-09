package net.golui.skypemod;

public class SkypeChat {
	String name;
	String id;
	SkypeUser[] users;
	
	public SkypeChat(String name, String id)
	{
		this.name = name;
		this.id = id;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public SkypeUser[] getUsers() {
		return users;
	}

	public void setUsers(SkypeUser[] users) {
		this.users = users;
	}

}
