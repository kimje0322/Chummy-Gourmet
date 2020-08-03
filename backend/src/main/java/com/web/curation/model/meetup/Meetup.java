package com.web.curation.model.meetup;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.Null;

import org.springframework.lang.Nullable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Meetup {
	@Id
	@Column(name = "meetup_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private String id;

	@Column(name = "meetup_master")
	private String master;
	@Column(name = "meetup_title")
	private String title;
	@Column(name = "meetup_content")
	private String content;
	@Column(name = "meetup_location")
	private String location;
	@Column(name = "meetup_date")
	private String date;
	@Column(name = "meetup_personnel")
	private String personnel;
	@Column(name = "meetup_create_date")
	private String createDate;

	public Meetup() {
	}

	public Meetup(String id, String master, String title, String content, String location, String date,
			String personnel) {
		super();
		this.id = id;
		this.master = master;
		this.title = title;
		this.content = content;
		this.location = location;
		this.date = date;
		this.personnel = personnel;
	}




	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getMaster() {
		return master;
	}

	public void setMaster(String master) {
		this.master = master;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getPersonnel() {
		return personnel;
	}

	public void setPersonnel(String personnel) {
		this.personnel = personnel;
	}

	public String getCreateDate() {
		return createDate;
	}

	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}

	@Override
	public String toString() {
		return "MeetUp [id=" + id + ", master=" + master + ", title=" + title + ", content=" + content + ", location="
				+ location + ", date=" + date + ", personnel=" + personnel + ", createDate=" + createDate + "]";
	}

}
