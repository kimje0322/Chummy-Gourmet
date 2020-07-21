// 하단 DB 설정 부분은 Sub PJT II에서 데이터베이스를 구성한 이후에 주석을 해제하여 사용.

package com.web.curation.model.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class User {
	@Id
	@Column(name = "user_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private String userId;

	@JsonIgnore
	@Column(name = "user_pwd")
	private String userPwd;
	@Column(name = "user_email")
	private String userEmail;

	@Column(name = "user_joindate", insertable = false, updatable = false)
	private String userJoindate;
//    private LocalDateTime createDate;

	@Column(name = "user_name")
	private String userName;
	@Column(name = "user_nickname")
	private String userNickname;
	@Column(name = "user_phone")
	private String userPhone;

}