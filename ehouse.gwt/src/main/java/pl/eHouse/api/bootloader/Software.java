package pl.eHouse.api.bootloader;

import java.util.Date;

import pl.eHouse.web.common.server.utils.Timestamp;

public class Software {

	private String id;
	private String type;
	private String name;
	private String description;
	private String date;

	public Software(String type, String name, String description) {
		Date time = Timestamp.getTime();
		this.id = Timestamp.getTextTimestamp(time);
		this.type = type;
		this.name = name;
		this.description = description;
		this.date = Timestamp.getTextTime(time);
	}

	public Software(String id, String type, String name, String description,
			String date) {
		super();
		this.id = id;
		this.type = type;
		this.name = name;
		this.description = description;
		this.date = date;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getFileName() {
		return id + "." + type;
	}

	@Override
	public String toString() {
		return "Software [id=" + id + ", type=" + type + ", name=" + name
				+ ", description=" + description + ", date=" + date + "]";
	}

	

}
