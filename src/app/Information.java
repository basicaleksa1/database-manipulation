package app;

public class Information {

	private String ip;
	private String baza;
	private String username;
	private String pass;
	
	public Information(String ip, String baza, String username, String pass) {
		super();
		this.ip = ip;
		this.baza = baza;
		this.username = username;
		this.pass = pass;
	}

	public String getBaza() {
		return baza;
	}

	public void setBaza(String baza) {
		this.baza = baza;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPass() {
		return pass;
	}

	public void setPass(String pass) {
		this.pass = pass;
	}
	
	
}
