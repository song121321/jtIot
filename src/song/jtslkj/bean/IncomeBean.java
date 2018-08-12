package song.jtslkj.bean;

public class IncomeBean {

	private String id;
	private String title;
	private String name;
	private String money;
	private String time;
	private String detail;
	private String label;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
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
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public String getDetail() {
		return detail;
	}
	public void setDetail(String detail) {
		this.detail = detail;
	}
	public String getLabel() {
		return label;
	}
	public void setLabel(String label) {
		this.label = label;
	}
	public IncomeBean(String id, String title, String name, String money,
			String time, String detail, String label) {
		super();
		this.id = id;
		this.title = title;
		this.name = name;
		this.money = money;
		this.time = time;
		this.detail = detail;
		this.label = label;
	}
	
}
