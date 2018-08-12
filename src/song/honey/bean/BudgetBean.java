package song.honey.bean;

import java.io.Serializable;

public class BudgetBean implements Serializable {

	private static final long serialVersionUID = 1L;
	private String id;
	private String name;
	private String omoneyuse;
	private String detail;
	private String money;
	private String time;

	public BudgetBean(String id, String name, String omoneyuse, String detail,
			String money, String time) {
		super();
		this.id = id;
		this.name = name;
		this.omoneyuse = omoneyuse;
		this.detail = detail;
		this.money = money;
		this.time = time;
	}

	public BudgetBean(String name, String money, String detail) {
		super();
		this.id = "1";
		this.name = name;
		this.omoneyuse = "0";
		this.detail = detail;
		this.money = money;
		this.time = "";
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getOmoneyuse() {
		return omoneyuse;
	}

	public void setOmoneyuse(String omoneyuse) {
		this.omoneyuse = omoneyuse;
	}

	public String getDetail() {
		return detail;
	}

	public void setDetail(String detail) {
		this.detail = detail;
	}

	public String getMoney() {
		return money;
	}

	public void setMoney(String money) {
		this.money = money;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

}
