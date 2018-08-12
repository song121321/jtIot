package song.honey.bean;

import java.io.Serializable;

public class ConsumeBean implements Serializable {

	private static final long serialVersionUID = 1L;
	private String id;
	private String name;
	private String otype;
	private String person;

	private String money;
	private String otime;
	private String other;
	private String imgurl;

	public String getImgurl() {
		return imgurl;
	}

	public void setImgurl(String imgurl) {
		this.imgurl = imgurl;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getPerson() {
		return person;
	}

	public void setPerson(String person) {
		this.person = person;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getOtype() {
		return otype;
	}

	public void setOtype(String otype) {
		this.otype = otype;
	}

	public String getMoney() {
		return money;
	}

	public void setMoney(String money) {
		this.money = money;
	}

	public String getOtime() {
		return otime;
	}

	public void setOtime(String otime) {
		this.otime = otime;
	}

	public String getOther() {
		return other;
	}

	public void setOther(String other) {
		this.other = other;
	}

	public ConsumeBean(String id, String name, String otype, String person,
			String money, String otime, String other) {
		super();
		this.id = id;
		this.name = name;
		this.otype = otype;
		this.money = money;
		this.otime = otime;
		this.other = other;
		this.person = person;
		this.imgurl = "";
	}

}
