package com.web.curation.controller.account;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
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

@CrossOrigin(origins = { "*" })
@RestController
public class UserPageController {

	@Autowired
	UserPageDao userPageDao;
	
	@Autowired
	UserDao userdao;
	
	// 사용자가 팔로잉 하는 유저 리스트를 가져온다.
	@GetMapping("/userpage/getfollowinglist")
	@ApiOperation(value = "[유저페이지] 내가 팔로잉하는 유저 리스트 가져옴")
	public Object getuserfollowing(@RequestParam(required = true) final String userId) {
		
		// 팔로잉유저 Id리스트
		ArrayList<String>followingIdList = userPageDao.getUserFollowingByUserId(userId);
		// 팔로잉 유저 리스트
		ArrayList<Map<String, Object>>userList = new ArrayList<>();
		
		for (String followinguserId : followingIdList) {
			User user = userdao.getUserByUserId(followinguserId);
			Map<String, Object>map = new HashMap();
			map.put("followingId", user.getUserId());
			map.put("followingName", user.getUserName());
			map.put("followingEmail", user.getUserEmail());
			map.put("followingNickname", user.getUserNickname());
			map.put("followingPhone", user.getUserPhone());
			map.put("followingComment", user.getUserComment());
			
			userList.add(map);
		}
		System.out.println("내가 팔로잉 하는 유저 리스트 =" + userList);
		
		return userList;
	}
	
	// 사용자를 팔로워 하는 유저 리스트를 가져온다.
	@GetMapping("/userpage/getfollowerlist")
	@ApiOperation(value = "[유저페이지] 나를 팔로워하는 유저 리스트 가져옴(나도 그 사람을 팔로우하거나 요청한 상태면 followerFollowing가 true 아니면 false")
	public Object getuserfollower(@RequestParam(required = true) final String userId) {
		// 팔로워유저 Id리스트
		ArrayList<String>followerIdList = userPageDao.getUserFollowerByUserId(userId);
		// user리스트
		ArrayList<Map<String, Object>>userList = new ArrayList<>();
		
		for (String followeruserId : followerIdList) {
			User user = userdao.getUserByUserId(followeruserId);
			Map<String, Object>map = new HashMap();
			map.put("followerId", user.getUserId());
			map.put("followerName", user.getUserName());
			map.put("followerEmail", user.getUserEmail());
			map.put("followerNickname", user.getUserNickname());
			map.put("followerPhone", user.getUserPhone());
			map.put("followerComment", user.getUserComment());
			int ans1 = userPageDao.getFollowingCountByUserIdByUserFollowing(userId, followeruserId);
			
			int ans2 = userPageDao.getFollowingRequestCountByUserIdByUserFollowing(userId, followeruserId);
			
			// 내가 팔로잉 중ㅇ
			if(ans1 > 0 || ans2 > 0) {
				map.put("followerFollowing", "true");
				System.out.println("나("+userId+")는 상대방인 "+user.getUserName()+" 님을 팔로우 한 상태입니다.");
			}
			else {
				map.put("followerFollowing", "false");
				System.out.println(userId+ "님은 상대방인 "+user.getUserName()+" 님을 팔로우 하지 않은 상태입니다.");
			}
			userList.add(map);
		}
		
		System.out.println("나를 팔로워 하는 유저 리스트 =" + userList);
		
		return userList;
	}
	
	// 사용자의 유저정보, 팔로잉수, 팔로워수 가져오기
	@GetMapping("/userpage/getuser")
	@ApiOperation(value = "[유저페이지] 사용자의 유저정보, 팔로잉, 팔로워 수 가져오기")
	public Object getUser(@RequestParam(required = true) final String userId) {
		User user = new User();
		user = userdao.getUserByUserId(userId);
		System.out.println(user);
		Map<String, Object>map = new HashMap();
		map.put("userId", user.getUserId());
		map.put("userName", user.getUserName());
		map.put("userEmail", user.getUserEmail());
		map.put("userPWd", user.getUserPwd());
		map.put("userNickname", user.getUserNickname());
		map.put("userPhone", user.getUserPhone());
		map.put("userComment", user.getUserComment());
		// 팔로잉 수
		int count = userPageDao.getUserFollowingCount(user.getUserId());
		map.put("followingCount", count);

		// 팔로워 수
		count = userPageDao.getUserFollowerCount(user.getUserId());
		map.put("followerCount", count);
		
		System.out.println(map);
		return map;
	}
	
	// 사용자의 유저정보 변경
	@PutMapping("/userpage/updateuser")
	@ApiOperation(value = "[유저페이지] 사용자의 유저정보 변경(닉네임, 비밀번호, 한줄소개)")
	public Object updateuser(@RequestParam(required = true) final String userId,
			@RequestParam(required = true) final String userNickname,
			@RequestParam(required = true) final String userPwd,
			@RequestParam(required = true) final String userComment) {
		final BasicResponse result = new BasicResponse();
		// 계정 설정 화면에서는 닉네임, 비밀번호, 한줄 소개 변경 가능
			User user = new User();
			user = userdao.getUserByUserId(userId);
			user.setUserNickname(userNickname);
			user.setUserPwd(userPwd);
			user.setUserComment(userComment);
			try {
				userdao.setUserNicknameUserPwdUserCommentByUserId(userId, userNickname, userPwd, userComment);
				result.status = true;
				result.data = "success";
			} catch (Exception e) {
				// TODO: handle exception
				result.status = false;
				result.data = "fail";
			}
		return new ResponseEntity<>(result, HttpStatus.OK);
	}
	
	@GetMapping("/userpage/getfollowingrequest")
	@ApiOperation(value = "[유저페이지] 사용자를 팔로잉요청한 리스트 가져옴")
	public Object getfollowingrequest(@RequestParam(required = true) final String userId) {
		
		// 팔로워유저 Id리스트
		ArrayList<String>followingrequestIdList = userPageDao.getFollowingrequestUserByUserId(userId);
		// user리스트
		ArrayList<Map<String, Object>>userList = new ArrayList<>();
		
		for (String followingrequestuserId : followingrequestIdList) {
			User user = userdao.getUserByUserId(followingrequestuserId);
			Map<String, Object>map = new HashMap();
			map.put("followingRequestId", user.getUserId());
			map.put("followingRequestName", user.getUserName());
			map.put("followingRequestEmail", user.getUserEmail());
			map.put("followingRequestNickname", user.getUserNickname());
			map.put("followingRequestPhone", user.getUserPhone());
			map.put("followingRequestComment", user.getUserComment());
			
			userList.add(map);
		}
		
		System.out.println("사용자를 팔로잉 요청한 리스트 =" + userList);
		
		return userList;
	}
	
	@GetMapping("/userpage/acceptfollowingrequest")
	@ApiOperation(value = "[유저페이지] 팔로우요청한것을 수락해줌")
	public Object acceptfollowingrequest(@RequestParam(required = true) final String userId,
			@RequestParam(required = true) final String followingRequestId) {
		
		ArrayList<User>userList = new ArrayList<>();
		final BasicResponse result = new BasicResponse();
		
		try {
			// 유저의 팔로워 리스트에 들어간다.
			String result1 = userPageDao.insertFollowerUser(userId, followingRequestId);
			// 요청리스트에서 지우고
			String result2 = userPageDao.deleteFollowingrequestUser(userId, followingRequestId);
			result.status = true;
			result.data = "success";
		}
		catch (Exception e) {
			result.status = true;
			result.data = "fail";
			return result;
		}
		return result;
	}
	
	// 사용자의 유저정보 변경
	@PostMapping("/userpage/insertfollowingRequest")
	@ApiOperation(value = "[유저페이지] 상대방을 팔로잉하기 버튼누름")
	public Object insertfollowingRequest(@RequestParam(required = true) final String userId,
			@RequestParam(required = true) final String followerId) {
		final BasicResponse result = new BasicResponse();
		// 내 id가 상대방 요청 리스트로 들어감
		try {
			userPageDao.setUserIdByUserIdAndfollowerId(userId, followerId);
			result.status = true;
			result.data = "success";
		} catch (Exception e) {
			// TODO: handle exception
			result.status = false;
			result.data = "fail";
		}
		return new ResponseEntity<>(result, HttpStatus.OK);
	}
}