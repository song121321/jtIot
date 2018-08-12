package song.honey.bean;

import java.io.Serializable;
/**
 * 工作豆子
 * @author Ryan
 *
 */
public class WorkBean implements Serializable {
	private static final long serialVersionUID = 1L;
	private String id;
	private String name;
	private String singleprice;
	private String number;
	private String person;
	private String changge;
	private String address;
	private String worktime;
	private String paytime;
	private String status;
	private String content;
	private String zhongjia;
	private String workitemid;

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

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public String getPerson() {
		return person;
	}

	public void setPerson(String person) {
		this.person = person;
	}

	public String getChangge() {
		return changge;
	}

	public void setChangge(String changge) {
		this.changge = changge;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getWorktime() {
		return worktime;
	}

	public void setWorktime(String worktime) {
		this.worktime = worktime;
	}

	public String getPaytime() {
		return paytime;
	}

	public void setPaytime(String paytime) {
		this.paytime = paytime;
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

	public String getZhongjia() {
		return zhongjia;
	}

	public void setZhongjia(String zhongjia) {
		this.zhongjia = zhongjia;
	}

	public String getWorkitemid() {
		return workitemid;
	}

	public void setWorkitemid(String workitemid) {
		this.workitemid = workitemid;
	}

	public WorkBean(String id, String name, String singleprice, String number,
			String person, String changge, String address, String worktime,
			String paytime, String status, String content, String zhongjia,
			String workitemid) {
		super();
		this.id = id;
		this.name = name;
		this.singleprice = singleprice;
		this.number = number;
		this.person = person;
		this.changge = changge;
		this.address = address;
		this.worktime = worktime;
		this.paytime = paytime;
		this.status = status;
		this.content = content;
		this.zhongjia = zhongjia;
		this.workitemid = workitemid;
	}

	public WorkBean(String name, String singleprice, String number,
			String person, String changge, String address, String worktime,
			String paytime, String status, String content, String zhongjia,
			String workitemid) {
		super();
		this.id = "99999";
		this.name = name;
		this.singleprice = singleprice;
		this.number = number;
		this.person = person;
		this.changge = changge;
		this.address = address;
		this.worktime = worktime;
		this.paytime = paytime;
		this.status = status;
		this.content = content;
		this.zhongjia = zhongjia;
		this.workitemid = workitemid;
	}

}