package song.jtslkj.bean;

public class LogBean {
	private String id, person, incident, type, time, client, ip,module;

	public LogBean(String id, String person, String incident, String type,
			String time, String client, String ip,String module) {
		super();
		this.id = id;
		this.person = person;
		this.incident = incident;
		this.type = type;
		this.time = time;
		this.client = client;
		this.ip = ip;
		this.module = module;
	}

	
	public String getModule() {
		return module;
	}


	public void setModule(String module) {
		this.module = module;
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

	public String getIncident() {
		return incident;
	}

	public void setIncident(String incident) {
		this.incident = incident;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public String getClient() {
		return client;
	}

	public void setClient(String client) {
		this.client = client;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

}
