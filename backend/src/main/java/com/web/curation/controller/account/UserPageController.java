package com.web.curation.controller.account;


import java.util.ArrayList;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.web.curation.dao.user.UserDao;
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
	
	@Autowired
	UserDao userdao;
	
	// 사용자가 팔로잉 하는 유저 리스트를 가져온다.
	@GetMapping("/userpage/getfollowinglist")
	@ApiOperation(value = "[유저페이지] 팔로잉 유저 리스트 가져옴")
	public ArrayList<String> getuserfollowing(@RequestParam(required = true) final String userEmail) {
		
		// 팔로잉유저 Id리스트
		ArrayList<String>followingIdList = userPageDao.getUserFollowingByUserEmail(userEmail);
		// Name리스트
		ArrayList<String>userNameList = new ArrayList<String>();
		
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
		
		// 팔로워유저 Id리스트
		ArrayList<String>followerIdList = userPageDao.getUserFollowerByUserEmail(userEmail);
		// Name리스트
		ArrayList<String>userNameList = new ArrayList<String>();
		
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
		
		User user = userdao.getUserByUserEmail(userEmail);
		// 팔로잉유저 Id리스트
		int count = userPageDao.getUserFollowingCount(user.getUserId());
		
		System.out.println("팔로잉 수 = " + count);
		return count;
	}
	
	// 사용자를 팔로워하는 유저 카운터 반환
	@GetMapping("/userpage/getfollowercount")
	@ApiOperation(value = "[유저페이지] 팔로워 수 계산")
	public int getfollowercount(@RequestParam(required = true) final String userEmail) {
		
		User user = userdao.getUserByUserEmail(userEmail);
		// 팔로워유저 Id리스트
		int count = userPageDao.getUserFollowerCount(user.getUserId());
		
		System.out.println("팔로워 수 = " + count);
		return count;
	}
	
	// 사용자의 한줄 소개
	@GetMapping("/userpage/getuserComment")
	@ApiOperation(value = "[유저페이지] 유저한줄소개가져오기")
	public String getuserConmment(@RequestParam(required = true) final String userEmail) {
		
		User user = userdao.getUserByUserEmail(userEmail);
		// 팔로워유저 Id리스트
		String Comment = "";
		try {
			Comment = userPageDao.getUserCommentByUserId(user.getUserId());
		}
		catch (Exception e) {
			
		}
		System.out.println(Comment);
		return Comment;
	}
}