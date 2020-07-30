package com.web.curation.model.review;

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
public class ReviewComment {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "no")
	private String no;

	@Column(name = "review_id")
	private String reviewid;
	@Column(name = "user_id")
	private String userid;
	@Column(name = "review_comment")
	private String comment;
	@Column(name = "user_nickname")
	private String nickname;

	public String getReviewid() {
		return reviewid;
	}

	public void setReviewid(String reviewid) {
		this.reviewid = reviewid;
	}

	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	@Override
	public String toString() {
		return "ReviewComment [reviewid=" + reviewid + ", userid=" + userid + ", comment=" + comment + "]";
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
//
//	@Override
//	public String toString() {
//		return "ReviewComment [reviewid=" + reviewid + ", userid=" + userid + ", comment=" + comment + ", nickname="
//				+ nickname + "]";
//	}

}
