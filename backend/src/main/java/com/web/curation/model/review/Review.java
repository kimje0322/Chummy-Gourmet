package com.web.curation.model.review;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Review {

	@Id
	@Column(name = "review_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private String id;

	@Column(name = "review_title")
	private String title;
	@Column(name = "review_category")
	private String category;
	@Column(name = "review_writer")
	private String writer;
	@Column(name = "review_content")
	private String content;
	@Column(name = "review_date")
	private String date;
	@Column(name = "rest_id")
	private String restid;
	@Column(name = "meetup_id")
	private String meetupid;

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getMeetupid() {
		return meetupid;
	}

	public void setMeetupid(String meetupid) {
		this.meetupid = meetupid;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getRestid() {
		return restid;
	}

	public void setRestid(String restid) {
		this.restid = restid;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getWriter() {
		return writer;
	}

	public void setWriter(String writer) {
		this.writer = writer;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	@Override
	public String toString() {
		return "Review [id=" + id + ", title=" + title + ", category=" + category + ", writer=" + writer + ", content="
				+ content + ", date=" + date + ", restid=" + restid + ", meetupid=" + meetupid + "]";
	}

}
