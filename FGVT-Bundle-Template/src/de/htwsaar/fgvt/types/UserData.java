/**
 * 
 */
package de.htwsaar.fgvt.types;

/**
 * @author mfuenfrocken
 *
 */
public class UserData {
	private String id = "";
	private String homeDirectory = "";
	private String fullName = "";
	private String mail = "";
	
	public UserData() {
		this.setHomeDirectory(System.getProperty("user.home"));
		this.setId(System.getProperty("user.name"));
	}
	
	public String getMail() {
		return mail;
	}
	public void setMail(String mail) {
		this.mail = mail;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getHomeDirectory() {
		return homeDirectory;
	}
	public void setHomeDirectory(String homeDirectory) {
		this.homeDirectory = homeDirectory;
	}
	public String getFullName() {
		return fullName;
	}
	public void setFullName(String fullName) {
		this.fullName = fullName;
	}
}
