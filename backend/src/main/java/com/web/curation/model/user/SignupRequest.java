package com.web.curation.model.user;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

import javax.persistence.Column;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Valid
@Data
public class SignupRequest {
	@ApiModelProperty(required = true)
	@NotNull
	String userEmail;
	@ApiModelProperty(required = true)
	@NotNull
	@Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d$@$!%*#?&]{8,}$")
	String userPwd;
	@ApiModelProperty(required = true)
	@NotNull
	String userNickname;
	@ApiModelProperty(required = true)
	@NotNull
	String userName;
	@ApiModelProperty(required = true)
	@NotNull
	String userPhone;
	@ApiModelProperty(required = true)
//	@NotNull
	String userGender;
	@ApiModelProperty(required = true)
//	@NotNull
	String userAge;
	@ApiModelProperty(required = true)
//	@NotNull
	List<Integer> userFavorite;
	@ApiModelProperty(required = true)
//	@NotNull
	List<Integer> userPersonality;
	@ApiModelProperty(required = true)
//	@NotNull
	List<Integer> userInterest;

	public String getUserEmail() {
		return userEmail;
	}

	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}

	public String getUserPwd() {
		return userPwd;
	}

	public void setUserPwd(String userPwd) {
		this.userPwd = userPwd;
	}

	public String getUserNickname() {
		return userNickname;
	}

	public void setUserNickname(String userNickname) {
		this.userNickname = userNickname;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserPhone() {
		return userPhone;
	}

	public void setUserPhone(String userPhone) {
		this.userPhone = userPhone;
	}

	public String getUserGender() {
		return userGender;
	}

	public void setUserGender(String userGender) {
		this.userGender = userGender;
	}

	public String getUserAge() {
		return userAge;
	}

	public void setUserAge(String userAge) {
		this.userAge = userAge;
	}

//	public SignupRequest(@NotNull String userEmail,
//			@NotNull @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d$@$!%*#?&]{8,}$") String userPwd,
//			@NotNull String userNickname, @NotNull String userName, @NotNull String userPhone) {
//		super();
//		this.userEmail = userEmail;
//		this.userPwd = userPwd;
//		this.userNickname = userNickname;
//		this.userName = userName;
//		this.userPhone = userPhone;
//	}

	public List<Integer> getUserFavorite() {
		return userFavorite;
	}

	public void setUserFavorite(List<Integer> userFavorite) {
		this.userFavorite = userFavorite;
	}

	public List<Integer> getUserPersonality() {
		return userPersonality;
	}

	public void setUserPersonality(List<Integer> userPersonality) {
		this.userPersonality = userPersonality;
	}

	public List<Integer> getUserInterest() {
		return userInterest;
	}

	public void setUserInterest(List<Integer> userInterest) {
		this.userInterest = userInterest;
	}

	public SignupRequest(@NotNull String userEmail,
			@NotNull @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d$@$!%*#?&]{8,}$") String userPwd,
			@NotNull String userNickname, @NotNull String userName, @NotNull String userPhone, String userGender,
			String userAge, List<Integer> userFavorite, List<Integer> userPersonality, List<Integer> userInterest) {
		super();
		this.userEmail = userEmail;
		this.userPwd = userPwd;
		this.userNickname = userNickname;
		this.userName = userName;
		this.userPhone = userPhone;
		this.userGender = userGender;
		this.userAge = userAge;
		this.userFavorite = userFavorite;
		this.userPersonality = userPersonality;
		this.userInterest = userInterest;
	}

	@Override
	public String toString() {
		return "SignupRequest [userEmail=" + userEmail + ", userPwd=" + userPwd + ", userNickname=" + userNickname
				+ ", userName=" + userName + ", userPhone=" + userPhone + "]";
	}

}
