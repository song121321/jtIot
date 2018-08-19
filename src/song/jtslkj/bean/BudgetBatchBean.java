package song.jtslkj.bean;

import java.io.Serializable;

public class BudgetBatchBean implements Serializable {

	private static final long serialVersionUID = 1L;
	private String id;
	private boolean ischeck;
	private String name;
	private String money;
	private boolean isusual;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public boolean isIscheck() {
		return ischeck;
	}

	public void setIscheck(boolean ischeck) {
		this.ischeck = ischeck;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getMoney() {
		return money;
	}

	public void setMoney(String money) {
		this.money = money;
	}

	public boolean isIsusual() {
		return isusual;
	}

	public void setIsusual(boolean isusual) {
		this.isusual = isusual;
	}

	public BudgetBatchBean(boolean ischeck, String name, String money,
			boolean isusual) {
		super();
		this.id = "";
		this.ischeck = ischeck;
		this.name = name;
		this.money = money;
		this.isusual = isusual;
	}

	public BudgetBatchBean(String id, String ischeck, String name,
			String money, String isusual) {
		super();
		this.id = id;
		if (ischeck.trim().equals("1")) {
			this.ischeck = true;
		} else {
			this.ischeck = false;
		}
		this.name = name;
		this.money = money;
		if (isusual.trim().equals("1")) {
			this.isusual = true;
		} else {
			this.isusual = false;
		}
	}

}
