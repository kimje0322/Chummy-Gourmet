package com.web.curation.controller.account;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.StringTokenizer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.web.curation.dao.post.PostDao;
import com.web.curation.dao.restaurant.RestaurantDao;
import com.web.curation.dao.user.UserDao;
import com.web.curation.dao.user.UserDetailDao;
import com.web.curation.dao.user.UserPageDao;
import com.web.curation.model.BasicResponse;
import com.web.curation.model.review.Restaurant;
import com.web.curation.model.user.User;
import com.web.curation.model.user.UserDetail;

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

	@Autowired
	UserDetailDao userDetailDao;

	@Autowired
	RestaurantDao restDao;

	@Autowired
	PostDao postDao;

	

	// 밋업에 대한 참가자들 가져오기
	@GetMapping("/userpage/getMeetupMember")
	@ApiOperation(value = "[유저페이지] 밋업 참가자들 데이터 가져오기")
	public Object getMeetupMember(@RequestParam(required = true) final String meetupId,
			@RequestParam(required = true) final String meetupMaster) {

		User masterUser = userdao.getUserByUserId(meetupMaster);

		ArrayList<String> List = userPageDao.getUsersByMeetupId(meetupId);

		// 밋업 유저 리스트
		ArrayList<Map<String, Object>> meeUpUserList = new ArrayList<>();
		Map<String, Object> map = new HashMap();
		map.put("masterUserName", masterUser.getUserName());
		map.put("masterUserNickname", masterUser.getUserNickname());
		meeUpUserList.add(map);

		for (String userId : List) {
			map = new HashMap();

			User member = userdao.getUserByUserId(userId);
			map.put("memberUserName", member.getUserName());
			map.put("memberUserNickname", member.getUserNickname());

			meeUpUserList.add(map);
		}
		return meeUpUserList;
	}

	// 사용자의 밋업정보리스트 가져오기
	@GetMapping("/userpage/getuserMeetup")
	@ApiOperation(value = "[유저페이지] 사용자의 밋업정보리스트 가져오기")
	public Object getuserMeetup(@RequestParam(required = true) final String userId) {

		ArrayList<String> List = userPageDao.getMeetupByUserId(userId);

		// 팔로잉 유저 리스트
		ArrayList<Map<String, Object>> meeUpList = new ArrayList<>();
		for (String str : List) {
			String[] array = str.split(",");
			Map<String, Object> map = new HashMap();
			map.put("meetupId", array[0]);
			map.put("meetupMaster", array[1]);
			map.put("meetupTitle", array[2]);
			map.put("meetupContent", array[3]);
			map.put("meetupLocation", array[4]);
			map.put("meetupDate", array[5]);
			map.put("meetupPersonnel", array[6]);
			map.put("meetupCreateDate", array[7]);
			meeUpList.add(map);
		}
		return meeUpList;
	}

	// 사용자가 팔로잉 하는 유저 리스트를 가져온다.
	@GetMapping("/userpage/getfollowinglist")
	@ApiOperation(value = "[유저페이지] 내가 팔로잉하는 유저 리스트 가져옴")
	public Object getuserfollowing(@RequestParam(required = true) final String userId,
			@RequestParam(required = true) final String searchName) {

		// 팔로잉유저 Id리스트
		ArrayList<String> followingIdList = userPageDao.getUserFollowingByUserId(userId);
		// 팔로잉 유저 리스트
		ArrayList<Map<String, Object>> userList = new ArrayList<>();

		for (String followinguserId : followingIdList) {

			if (searchName == "") {
				User user;
				user = userdao.getUserByUserId(followinguserId);
				Map<String, Object> map = new HashMap();
				map.put("followingId", user.getUserId());
				map.put("followingName", user.getUserName());
				map.put("followingEmail", user.getUserEmail());
				map.put("followingNickname", user.getUserNickname());
				map.put("followingPhone", user.getUserPhone());
				map.put("followingComment", user.getUserComment());
				map.put("followingImg", user.getUserImg());
				userList.add(map);
			}
			// 유저 검색을 한 경우
			else {
				Optional<User> user;
				user = userdao.getUserByUserId(followinguserId, searchName);
				if (user.isPresent()) {
					Map<String, Object> map = new HashMap();
					map.put("followingId", user.get().getUserId());
					map.put("followingName", user.get().getUserName());
					map.put("followingEmail", user.get().getUserEmail());
					map.put("followingNickname", user.get().getUserNickname());
					map.put("followingPhone", user.get().getUserPhone());
					map.put("followingComment", user.get().getUserComment());
					map.put("followingImg", user.get().getUserImg());

					userList.add(map);
				}
			}
		}
		return userList;
	}

	// 사용자를 팔로워 하는 유저 리스트를 가져온다.
	@GetMapping("/userpage/getfollowerlist")
	@ApiOperation(value = "[유저페이지] 나를 팔로워하는 유저 리스트 가져옴(나도 그 사람을 팔로우하거나 요청한 상태면 followerFollowing가 true 아니면 false")
	public Object getuserfollower(@RequestParam(required = true) final String userId,
			@RequestParam(required = true) final String searchName) {
		System.out.println("searchname = " + searchName);
		// 팔로워유저 Id리스트
		ArrayList<String> followerIdList = userPageDao.getUserFollowerByUserId(userId);
		// user리스트
		ArrayList<Map<String, Object>> userList = new ArrayList<>();
		Map<String, Object> map;
		for (String followeruserId : followerIdList) {
			if (searchName == "") {
				User user;
				map = new HashMap();
				user = userdao.getUserByUserId(followeruserId);
				map.put("followerId", user.getUserId());
				map.put("followerName", user.getUserName());
				map.put("followerEmail", user.getUserEmail());
				map.put("followerNickname", user.getUserNickname());
				map.put("followerPhone", user.getUserPhone());
				map.put("followerComment", user.getUserComment());
				map.put("followerImg", user.getUserImg());
				// 내가 팔로잉 중이냐
				int ans1 = userPageDao.getFollowingCountByUserIdByUserFollowing(userId, followeruserId);
				// 내가 요청중이냐
				int ans2 = userPageDao.getFollowingRequestCountByUserIdByUserFollowing(userId, followeruserId);

				// 내가 팔로잉 중임
				if (ans1 > 0) {
					map.put("followerFollowing", "true");
					System.out.println("나(" + userId + ")는 상대방인 " + user.getUserName() + " 님을 팔로우 한 상태입니다.");
				}
				// 내가 요청중임
				else if (ans2 > 0) {
					map.put("followerFollowing", "doing");
					System.out.println("나(" + userId + ")는 상대방인 " + user.getUserName() + " 님을 팔로우 요청한 상태입니다.");
				} else {
					map.put("followerFollowing", "false");
					System.out.println(userId + "님은 상대방인 " + user.getUserName() + " 님을 팔로우 하지 않은 상태입니다.");
				}
				userList.add(map);
			}
			// 유저 검색을 한 경우
			else {
				map = new HashMap();
				if (userdao.getUserByUserId(followeruserId, searchName) != null) {
					Optional<User> user;
					user = userdao.getUserByUserId(followeruserId, searchName);
					if (user.isPresent()) {
						map.put("followerId", user.get().getUserId());
						map.put("followerName", user.get().getUserName());
						map.put("followerEmail", user.get().getUserEmail());
						map.put("followerNickname", user.get().getUserNickname());
						map.put("followerPhone", user.get().getUserPhone());
						map.put("followerComment", user.get().getUserComment());
						map.put("followerImg", user.get().getUserImg());

						// 내가 팔로잉 중이냐
						int ans1 = userPageDao.getFollowingCountByUserIdByUserFollowing(userId, followeruserId);
						// 내가 요청중이냐
						int ans2 = userPageDao.getFollowingRequestCountByUserIdByUserFollowing(userId, followeruserId);

						// 내가 팔로잉 중임
						if (ans1 > 0) {
							map.put("followerFollowing", "true");
							System.out.println(
									"나(" + userId + ")는 상대방인 " + user.get().getUserName() + " 님을 팔로우 한 상태입니다.");
						}
						// 내가 요청중임
						else if (ans2 > 0) {
							map.put("followerFollowing", "doing");
							System.out.println(
									"나(" + userId + ")는 상대방인 " + user.get().getUserName() + " 님을 팔로우 요청한 상태입니다.");
						} else {
							map.put("followerFollowing", "false");
							System.out.println(userId + "님은 상대방인 " + user.get().getUserName() + " 님을 팔로우 하지 않은 상태입니다.");
						}
						userList.add(map);
					}

				}
			}
		}
		return userList;
	}

	// 사용자의 유저 추가정보 업데이트
	@PutMapping("/userpage/putuserInfo")
	@ApiOperation(value = "[유저페이지] 사용자의 유저추가정보 업데이트")
	public Object putUserInfo(@RequestBody Map<String, Object> param) {
		UserDetail userdetail = new UserDetail();
		String userId = (String) param.get("userId");
		ArrayList<String> list = (ArrayList<String>) param.get("userFavorite");
		String userFavorite = param.get("userFavorite").toString();
		String userPersonality = param.get("userPersonality").toString();
		String userInterest = param.get("userInterest").toString();
		int num = userDetailDao.setUserDetailByUserId(userId, userFavorite, userPersonality, userInterest);
		return num;
	}

	// 사용자의 유저 추가정보 가져오기
	@GetMapping("/userpage/getuserInfo")
	@ApiOperation(value = "[유저페이지] 사용자의 유저추가정보 가져오기")
	public Object getUserInfo(@RequestParam(required = true) final String userId) {
		UserDetail userdetail = new UserDetail();
		userdetail = userDetailDao.getUserDetailByUserId(userId);

		return userdetail;
	}

	// 사용자의 유저정보, 팔로잉수, 팔로워수 가져오기
	@GetMapping("/userpage/getuser")
	@ApiOperation(value = "[유저페이지] 사용자의 유저정보, 팔로잉, 팔로워 수 가져오기,팔로잉요청수까지")
	public Object getUser(@RequestParam(required = true) final String userId) {
		User user = new User();
		user = userdao.getUserByUserId(userId);
		System.out.println(user);
		Map<String, Object> map = new HashMap();
		map.put("userId", user.getUserId());
		map.put("userName", user.getUserName());
		map.put("userEmail", user.getUserEmail());
		map.put("userPWd", user.getUserPwd());
		map.put("userNickname", user.getUserNickname());
		map.put("userPhone", user.getUserPhone());
		map.put("userComment", user.getUserComment());
		map.put("userImg", user.getUserImg());
		// 팔로잉 수
		int count = userPageDao.getUserFollowingCount(user.getUserId());
		map.put("followingCount", count);

		// 팔로워 수
		count = userPageDao.getUserFollowerCount(user.getUserId());
		map.put("followerCount", count);

		count = userPageDao.getUserFolloingRequestCount(user.getUserId());
		map.put("followingRequestCount", count);

		System.out.println(map);
		return map;
	}

	// 사용자의 유저정보 변경
	@PutMapping("/userpage/updateuser")
	@ApiOperation(value = "[유저페이지] 사용자의 유저정보 변경(닉네임(중복검사실시), 비밀번호, 한줄소개)")
	public Object updateuser(@RequestParam(required = true) final String userId,
			@RequestParam(required = true) final String userNickname,
			@RequestParam(required = true) final String userPwd,
			@RequestParam(required = true) final String userComment) {

		Optional<User> originUser = null;
		final BasicResponse result = new BasicResponse();
		boolean isExistNickname = false;

		// 닉네임 중복 검사
		originUser = userdao.getUserByUserNickname(userNickname);
		System.out.println(originUser);
		// 유저가 존재하면 닉네임 중복이다
		if (originUser.isPresent()) {
			// 그러나 불러온 유저 아이디가 현재 내 아이디면 패스
			if (!originUser.get().getUserId().equals(userId))
				isExistNickname = true;
		}
		// 기존에 가입한 동일 닉네임이 있다면
		if (isExistNickname) {
			result.status = false;
			result.data = "isExistNickname";
			return new ResponseEntity<>(result, HttpStatus.OK);
		}
		// 기존 유저가 없다면
		else {
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
		}
		return new ResponseEntity<>(result, HttpStatus.OK);
	}

	@DeleteMapping("/userpage/deletefollowingRequest")
	@ApiOperation(value = "[유저페이지] 팔로잉요청 취소")
	public Object deletefollowingRequest(@RequestParam(required = true) final String userId,
			@RequestParam(required = true) final String anotherId) {

		final BasicResponse result = new BasicResponse();

		userPageDao.deleteFollowingRequest(userId, anotherId);

		result.status = true;
		result.data = "success";

		return result;
	}

	@DeleteMapping("/userpage/deletefollowing")
	@ApiOperation(value = "[유저페이지] 팔로잉 취소")
	public Object deletefollowing(@RequestParam(required = true) final String userId,
			@RequestParam(required = true) final String anotherId) {

		final BasicResponse result = new BasicResponse();

		userPageDao.deleteFollowing(userId, anotherId);
		userPageDao.deleteFollower(userId, anotherId);

		result.status = true;
		result.data = "success";

		return result;
	}

	@DeleteMapping("/userpage/deletefollowingrequest")
	@ApiOperation(value = "[유저페이지] 팔로잉 요청 거절")
	public Object deletefollowingrequest(@RequestParam(required = true) final String userId,
			@RequestParam(required = true) final String followingRequestId) {

		final BasicResponse result = new BasicResponse();

		userPageDao.deleteFollowingrequestUser(userId, followingRequestId);

		result.status = true;
		result.data = "success";

		return result;
	}

	@GetMapping("/userpage/getfollowingrequest")
	@ApiOperation(value = "[유저페이지] 사용자를 팔로잉요청한 리스트 가져옴")
	public Object getfollowingrequest(@RequestParam(required = true) final String userId) {

		// 팔로워유저 Id리스트
		ArrayList<String> followingrequestIdList = userPageDao.getFollowingrequestUserByUserId(userId);
		// user리스트
		ArrayList<Map<String, Object>> userList = new ArrayList<>();

		for (String followingrequestuserId : followingrequestIdList) {
			User user = userdao.getUserByUserId(followingrequestuserId);
			Map<String, Object> map = new HashMap();
			map.put("followingRequestId", user.getUserId());
			map.put("followingRequestName", user.getUserName());
			map.put("followingRequestEmail", user.getUserEmail());
			map.put("followingRequestNickname", user.getUserNickname());
			map.put("followingRequestPhone", user.getUserPhone());
			map.put("followingRequestComment", user.getUserComment());
			map.put("followingRequestUserImg", user.getUserImg());
			// 내가 팔로잉 중이냐
			int ans1 = userPageDao.getFollowingCountByUserIdByUserFollowing(userId, followingrequestuserId);
			// 내가 요청중이냐
			int ans2 = userPageDao.getFollowingRequestCountByUserIdByUserFollowing(userId, followingrequestuserId);
			// 내가 팔로잉 중임
			if (ans1 > 0) {
				map.put("followerFollowing", "true");
			}
			// 내가 요청중임
			else if (ans2 > 0) {
				map.put("followerFollowing", "doing");
			} else {
				map.put("followerFollowing", "false");
			}
			userList.add(map);
		}

		return userList;
	}

	@GetMapping("/userpage/acceptfollowingrequest")
	@ApiOperation(value = "[유저페이지] 팔로잉요청한것을 수락해줌")
	public Object acceptfollowingrequest(@RequestParam(required = true) final String userId,
			@RequestParam(required = true) final String followingRequestId) {

		ArrayList<User> userList = new ArrayList<>();
		final BasicResponse result = new BasicResponse();

		try {
			// 유저의 팔로워 리스트에 들어간다.
			String result1 = userPageDao.insertFollowerUser(userId, followingRequestId);
			// 요청리스트에서 지우고
			String result2 = userPageDao.deleteFollowingrequestUser(userId, followingRequestId);
			// 상대방의 팔로잉 리스트에 들어간다.
			String result3 = userPageDao.insertFollowingUser(userId, followingRequestId);
			result.status = true;
			result.data = "success";
		} catch (Exception e) {
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

	// 사용자의 프로필사진 변경
	@GetMapping("/userpage/updateimg")
	@ApiOperation(value = "[유저이미지 정보 저장]")
	public void updateImg(@RequestParam(required = true) final String user_id,
			@RequestParam(required = true) final String user_img) {
		System.out.println(user_id + " " + user_img);
		userdao.setUserImgByUserId(user_img, user_id);
	}

	@PostMapping("/userpage/img")
	@ApiOperation(value = "[유저이미지파일 저장 후 파일 이름 리턴]")
	public String test(@RequestParam("file") MultipartFile file) throws Exception {
		System.out.println("파일 이름 : " + file.getOriginalFilename());
		System.out.println("파일 크기 : " + file.getSize());
		StringTokenizer st = new StringTokenizer(file.getOriginalFilename(), ".");
		String fileName = st.nextToken();
		String extension = st.nextToken();
		System.out.println(fileName);
		System.out.println(extension);

		Date today = new Date();
		SimpleDateFormat date = new SimpleDateFormat("yyyyMMdd");
		SimpleDateFormat time = new SimpleDateFormat("hhmmss");

		int r = (int) (Math.random() * 1000000);

		String fileFullName = r + "_" + date.format(today) + time.format(today) + "." + extension;
		// 서버에서 사용할때
		FileCopyUtils.copy(file.getBytes(), new File("/home/ubuntu/deploy/img/user/" + fileFullName));
		// 로컬에서 테스트할때
//	    FileCopyUtils.copy(file.getBytes(), new File("C:/"+fileFullName));
		return fileFullName;
	}

	@GetMapping("/userpage/getuserpost")
	@ApiOperation(value = "현재 사용자의 게시글 가져오기")
	public Map<String, Object> getuserpost(@RequestParam(required = true) final String userId) {
		List<Map<String, Object>> post = new ArrayList<>();
		post = postDao.selectAllByUserid(Integer.parseInt(userId));
		System.out.println(post);
		List<Integer> commentcount = new ArrayList<Integer>();
		for (Map<String, Object> p : post) {
			commentcount.add(postDao.selectAllCommentByPostId(p.get("post_id").toString()));
		}

		Map<String, Object> data = new HashMap<String, Object>();

		data.put("data", post);
		data.put("comment", commentcount);

		return data;
	}
}
