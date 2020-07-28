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
	@PostMapping("/userpage/getfollowinglist")
	@ApiOperation(value = "[유저페이지] 팔로잉 유저 리스트 가져옴")
	public Object getuserfollowing(@RequestParam(required = true) final String userEmail) {
		
		// 팔로잉유저 Id리스트
		ArrayList<String>followingIdList = userPageDao.getUserFollowingByUserEmail(userEmail);
		// 팔로잉 유저 리스트
		ArrayList<User>userList = new ArrayList<>();
		
		for (String userId : followingIdList) {
			userList.add(userdao.getUserByUserId(userId));
		}
		System.out.println("사용자가 팔로잉 하는 유저 리스트 =" + userList);
		
		return userList;
	}
	
	// 사용자를 팔로워 하는 유저 리스트를 가져온다.
	@PostMapping("/userpage/getfollowerlist")
	@ApiOperation(value = "[유저페이지] 팔로워 유저 리스트 가져옴")
	public Object getuserfollower(@RequestParam(required = true) final String userEmail) {
		
		// 팔로워유저 Id리스트
		ArrayList<String>followerIdList = userPageDao.getUserFollowerByUserEmail(userEmail);
		// user리스트
		ArrayList<User>userList = new ArrayList<>();
		
		for (String userId : followerIdList) {
			userList.add(userdao.getUserByUserId(userId));
		}
		
		System.out.println("사용자를 팔로워 하는 유저 리스트 =" + userList);
		
		return userList;
	}
	
	// 사용자의 유저정보, 팔로잉수, 팔로워수 가져오기
	@GetMapping("/userpage/getuser")
	@ApiOperation(value = "[유저페이지] 사용자의 유저정보, 팔로잉, 팔로워 수 가져오기")
	public Object getUserByUserEmail(@RequestParam(required = true) final String userEmail) {
		User user = new User();
		user = userdao.getUserByUserEmail(userEmail);
		System.out.println(user);
		Map<String, Object>map = new HashMap();
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
	public Object updateUserByUserEmail(@RequestParam(required = true) final String userEmail,
			@RequestParam(required = true) final String userNickname,
			@RequestParam(required = true) final String userPwd,
			@RequestParam(required = true) final String userComment) {
		final BasicResponse result = new BasicResponse();
		// 계정 설정 화면에서는 닉네임, 비밀번호, 한줄 소개 변경 가능
			User user = new User();
			user = userdao.getUserByUserEmail(userEmail);
			user.setUserNickname(userNickname);
			user.setUserPwd(userPwd);
			user.setUserComment(userComment);
			try {
				userdao.setUserNicknameUserPwdUserCommentByUserId(user.getUserId(), userNickname, userPwd, userComment);
				result.status = true;
				result.data = "success";
			} catch (Exception e) {
				// TODO: handle exception
				result.status = false;
				result.data = "fail";
			}
		return new ResponseEntity<>(result, HttpStatus.OK);
	}
	
	// 이 유저를 내가 팔로우 했냐 안했냐
	@GetMapping("/userpage/checkfollow")
	@ApiOperation(value = "[유저페이지] 상대방을 내가 팔로우 했는지 안했는지 확인(팔로우 요청버튼 노출)")
	public String checkFollowByUserName(@RequestParam(required = true) final String userEmail,
			@RequestParam(required = true) final String opponentEmail) {
		User user = new User();
		user = userdao.getUserByUserEmail(userEmail);
		String userId = user.getUserId();
		
		user = userdao.getUserByUserEmail(opponentEmail);
		String opponentId = user.getUserId();
		
		int ans = 0;
		ans = userPageDao.getFollowingByUserIdByUserFollowing(userId, opponentId);
		String result = "false";
		if(ans > 0) {
			result = "true"; 
			System.out.println("나("+userEmail+")는 상대방인 "+user.getUserName()+" 님을 팔로우 한 상태입니다.");
		}
		else {
			System.out.println(userEmail+ "님은 상대방인 "+user.getUserName()+" 님을 팔로우 하지 않은 상태입니다.");
		}
		
		return result;
	}
	
	@PostMapping("/userpage/getfollowingrequestlist")
	@ApiOperation(value = "[유저페이지] 사용자를 팔로잉요청한 리스트 가져옴")
	public Object getfollowingrequestlist(@RequestParam(required = true) final String userEmail) {
		
		// 팔로워유저 Id리스트
		ArrayList<String>followingrequestIdList = userPageDao.getFollowingrequestUserByUserEmail(userEmail);
		// user리스트
		ArrayList<User>userList = new ArrayList<>();
		
		for (String userId : followingrequestIdList) {
			userList.add(userdao.getUserByUserId(userId));
		}
		
		System.out.println("사용자를 팔로잉 요청한 리스트 =" + userList);
		
		return userList;
	}
	
	@GetMapping("/userpage/acceptfollowing")
	@ApiOperation(value = "[유저페이지] 팔로우요청한것을 수락해줌")
	public Object acceptfollowing(@RequestParam(required = true) final String userEmail,
			@RequestParam(required = true) final String opponentEmail) {
		
		ArrayList<User>userList = new ArrayList<>();
		final BasicResponse result = new BasicResponse();
		
		User user = new User();
		user = userdao.getUserByUserEmail(userEmail);
		String userId = user.getUserId();
		
		user = userdao.getUserByUserEmail(opponentEmail);
		String opponentId = user.getUserId();
		try {
			// 유저의 팔로워 리스트에 들어간다.
			String result1 = userPageDao.insertFollowerUser(userId, opponentId);
			// 요청리스트에서 지우고
			String result2 = userPageDao.deleteFollowingrequestUser(userId, opponentId);
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
}