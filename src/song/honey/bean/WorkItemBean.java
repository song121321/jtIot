package song.honey.bean;

import java.io.Serializable;

public class WorkItemBean implements Serializable {
	private static final long serialVersionUID = 1L;
	private String id;
	private String name;
	private String singleprice;
	private String address;
	private String person;
	private String worktime;
	private String addtime;
	private String badtime;
	private String status;
	private String content;
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
	public String getSingleprice() {
		return singleprice;
	}
	public void setSingleprice(String singleprice) {
		this.singleprice = singleprice;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getPerson() {
		return person;
	}
	public void setPerson(String person) {
		this.person = person;
	}
	public String getWorktime() {
		return worktime;
	}
	public void setWorktime(String worktime) {
		this.worktime = worktime;
	}
	public String getAddtime() {
		return addtime;
	}
	public void setAddtime(String addtime) {
		this.addtime = addtime;
	}
	public String getBadtime() {
		return badtime;
	}
	public void setBadtime(String badtime) {
		this.badtime = badtime;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public WorkItemBean(String id, String name, String singleprice,
			String address, String person, String worktime, String addtime,
			String badtime, String status, String content) {
		super();
		this.id = id;
		this.name = name;
		this.singleprice = singleprice;
		this.address = address;
		this.person = person;
		this.worktime = worktime;
		this.addtime = addtime;
		this.badtime = badtime;
		this.status = status;
		this.content = content;
	}
	
	
	
}
