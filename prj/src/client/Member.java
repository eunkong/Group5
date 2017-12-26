package client;

public class Member {
	private String itd;
	private String pwd;
	public Member(String itd, String pwd) {
		super();
		this.itd = itd;
		this.pwd = pwd;
	}
	public String getItd() {
		return itd;
	}
	public void setItd(String itd) {
		this.itd = itd;
	}
	public String getPwd() {
		return pwd;
	}
	public void setPwd(String pwd) {
		this.pwd = pwd;
	}
	
}
