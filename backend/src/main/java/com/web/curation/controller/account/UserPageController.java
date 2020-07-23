package com.web.curation.controller.account;


import java.util.ArrayList;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.web.curation.dao.user.UserPageDao;
import com.web.curation.model.BasicResponse;
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
	
	// 팔로잉유저 Id리스트
	ArrayList<String>followingIdList = new ArrayList<String>();
	// 팔로워유저 Id리스트
	ArrayList<String>followerIdList = new ArrayList<String>();
	// Id리스트를 Name리스트로 변경
	ArrayList<String>userNameList;

	// 사용자가 팔로잉 하는 유저 리스트를 가져온다.
	@GetMapping("/userpage/getfollowinglist")
	@ApiOperation(value = "[유저페이지] 팔로잉 유저 리스트 가져옴")
	public ArrayList<String> getuserfollowing(@RequestParam(required = true) final String userEmail) {
		
		userNameList = new ArrayList<String>();
		
		for (String userId : followingIdList) {
			userNameList.add(userPageDao.getUserNameByUserId(userId));
		}
		System.out.println("사용자가 팔로잉 하는 유저 리스트 =" + userNameList);
		
		return userNameList;
	}
	
	// 사용자를 팔로워 하는 유저 리스트를 가져온다.
	@GetMapping("/userpage/getfollowerlist")
	@ApiOperation(value = "[유저페이지] 팔로워 유저 리스트 가져옴")
	public ArrayList<String> getuserfollower(@RequestParam(required = true) final String userEmail) {
		
		userNameList = new ArrayList<String>();
		
		for (String userId : followerIdList) {
			userNameList.add(userPageDao.getUserNameByUserId(userId));
		}
		
		System.out.println("사용자를 팔로워 하는 유저 리스트 =" + userNameList);
		
		return userNameList;
	}
	
	// 사용자가 팔로잉하는 유저 카운터 반환
	@GetMapping("/userpage/getfollowingcount")
	@ApiOperation(value = "[유저페이지] 팔로잉 수 계산")
	public int getfollowingcount(@RequestParam(required = true) final String userEmail) {
		int count = 0;
		
		followingIdList = userPageDao.getUserFollowingByUserEmail(userEmail);
		count += followingIdList.size();
		
		System.out.println("팔로잉 수 = " + count);
		return count;
	}
	
	// 사용자를 팔로워하는 유저 카운터 반환
	@GetMapping("/userpage/getfollowercount")
	@ApiOperation(value = "[유저페이지] 팔로워 수 계산")
	public int getfollowercount(@RequestParam(required = true) final String userEmail) {
		int count = 0;
		
		followerIdList = userPageDao.getUserFollowerByUserEmail(userEmail);
		count += followerIdList.size();
		
		System.out.println("팔로워 수 = " + count);
		return count;
	}
}