package net.golui.skypemod;

public class SkypeUser {
	String displayName;
	String username;
	int status;
	
	public SkypeUser(String username,String displayName , int status)
	{
		this.displayName = displayName;
		this.username = username;
		this.status = status;
	}
	
	public String getDisplayName() {
		return displayName;
	}

	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}
}

