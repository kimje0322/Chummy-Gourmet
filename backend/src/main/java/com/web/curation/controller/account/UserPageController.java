package com.web.curation.controller.account;


import java.util.ArrayList;

import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpClientErrorException.Gone;

import com.web.curation.dao.user.FindPwdDao;
import com.web.curation.dao.user.UserPageDao;
import com.web.curation.model.BasicResponse;
import com.web.curation.model.user.User;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@ApiResponses(value = { @ApiResponse(code = 401, message = "Unauthorized", response = BasicResponse.class),
        @ApiResponse(code = 403, message = "Forbidden", response = BasicResponse.class),
        @ApiResponse(code = 404, message = "Not Found", response = BasicResponse.class),
        @ApiResponse(code = 500, message = "Failure", response = BasicResponse.class) })

@CrossOrigin(origins = { "http://localhost:3000" })
@RestController
public class UserPageController {

	@Autowired
	UserPageDao userPageDao;

	// 사용자가 팔로잉 하는 유저 리스트를 가져온다.
	@GetMapping("/userpage/getuserfollowing")
	@ApiOperation(value = " 사용자가 팔로잉 하는 유저 리스트를 가져옴")
	public ArrayList<String> getuserfollowing(@RequestParam(required = true) final String userEmail) {
		
		ArrayList<String>userIdList = new ArrayList<String>();
		userIdList = userPageDao.getUserFollowingByUserEmail(userEmail);
		
		ArrayList<String>userNameList = new ArrayList<String>();
		for (String userId : userIdList) {
			userNameList.add(userPageDao.getUserNameByUserId(userId));
		}
		System.out.println("사용자가 팔로잉 하는 유저 리스트 =" + userNameList);
		
		return userNameList;
	}
	
	// 사용자를 팔로워 하는 유저 리스트를 가져온다.
	@GetMapping("/userpage/getuserfollower")
	@ApiOperation(value = " 사용자를 팔로워 하는 유저 리스트를 가져옴")
	public ArrayList<String> getuserfollower(@RequestParam(required = true) final String userEmail) {
		
		ArrayList<String>userIdList = new ArrayList<String>();
		userIdList = userPageDao.getUserFollowerByUserEmail(userEmail);
		
		ArrayList<String>userNameList = new ArrayList<String>();
		
		for (String userId : userIdList) {
			userNameList.add(userPageDao.getUserNameByUserId(userId));
		}
		
		System.out.println("사용자를 팔로워 하는 유저 리스트 =" + userNameList);
		
		return userNameList;
	}
	
}