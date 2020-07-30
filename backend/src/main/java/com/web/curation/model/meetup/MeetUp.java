package com.web.curation.model.meetup;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MeetUp {
	@Id
	@Column(name = "meetup_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private String id;

	@Column(name = "meetup_master")
	private String master;
	@Column(name = "meetup_member")
	private String member;
	@Column(name = "meetup_title")
	private String title;
	@Column(name = "meetup_content")
	private String content;
	@Column(name = "meetup_location")
	private String location;
	@Column(name = "meetup_date")
	private String date;

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

	public String getMember() {
		return member;
	}

	public void setMember(String member) {
		this.member = member;
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

	@Override
	public String toString() {
		return "MeetUp [id=" + id + ", master=" + master + ", member=" + member + ", title=" + title + ", content="
				+ content + ", location=" + location + ", date=" + date + "]";
	}

}
