package com.web.curation.controller.meetup;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.web.curation.dao.meetup.MeetUpDao;
import com.web.curation.dao.meetup.MeetUpRequsetDao;
import com.web.curation.dao.user.UserDao;
import com.web.curation.model.BasicResponse;
import com.web.curation.model.meetup.Meetup;
import com.web.curation.model.meetup.MeetupRequest;
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
public class MeetUpController {

	@Autowired
	MeetUpDao meetupDao;
	
	@Autowired
	UserDao userDao;
	
	@Autowired
	MeetUpRequsetDao meetupRequestDao;
	
	//밋업 정보 조회
	@GetMapping("/meetup/search")
	@ApiOperation(value = "밋업 정보 조회")
	public Meetup searchMeetUp(@RequestParam(required = true) final int id) {
		
		Optional<Meetup> data = meetupDao.selectMeetUpById(id);
		Meetup test= data.get();
		System.out.println(data.get());
		
		return data.get();
	}
	
	//밋업 아이디로 밋업 정보 조회
	@GetMapping("/meetup/searchByMeetupID/{id}")
	@ApiOperation(value = "밋업 정보 조회")
	public Meetup searchByMeetupID(@PathVariable int id) {
		
		Optional<Meetup> data = meetupDao.getMeetupByMeetupID(id);
		Meetup meetup = data.get();
		System.out.println(data.get());
		
		return data.get();
	}
	
	//유저 아이디로 밋업 리스트 조회
	@GetMapping("/meetup/list/{userId}")
	@ApiOperation(value = "userId로 밋업 리스트 조회 | success | fail")
	public Object searchByUserID(@PathVariable int userId) {
		final BasicResponse result = new BasicResponse();
		Optional<List<Meetup>> data = meetupDao.getMeetupByUserID(userId);
		if(data.isPresent()) {
			result.status = true;
	        result.data = "success";
	        result.object = data.get();;
		}else {
			result.status = true;
	        result.data = "fail";
		}
		return result;
	}
	
	// 밋업 아이디로 밋업 멤버 리스트 조회
	@GetMapping("/meetup/members/{meetupId}")
	@ApiOperation(value = "meetupId로 해당 밋업의 멤버 리스트 조회 | success | fail")
	public Object findAllByMeetupId(@PathVariable int meetupId) {
		final BasicResponse result = new BasicResponse();
		Optional<List<User>> data = userDao.findMembersByMeetupId(meetupId);
		if(data.isPresent()) {
			result.status = true;
	        result.data = "success";
	        result.object = data.get();;
		}else {
			result.status = true;
	        result.data = "fail";
		}
		return result;
	}
	
	//해당 지역의 밋업 정보 조회
	@GetMapping("/meetup/search/location/{location}")
	@ApiOperation(value = "해당 지역의 밋업 정보 조회 | success | fail")
	public Object searchMeetUp(@PathVariable String location) {
		final BasicResponse result = new BasicResponse();
		List<Meetup> data = meetupDao.selectMeetUpByLocation(location.split(" ")[0],location.split(" ")[1]);
		
		// 데이터가 없다면
		if(data.isEmpty()) {
	        result.status = true;
	        result.data = "fail";
		}
		
		// 데이터가 있다면
		else {
	        result.status = true;
	        result.data = "success";
	        result.object = data;
		}
		return new ResponseEntity<>(result, HttpStatus.OK);
	}
	
	//신규 밋업 등록
	@PostMapping("/meetup")
	@ApiOperation(value = "신규 밋업 등록")
	public Object createMeetUp(@RequestBody Meetup meetup) {
		final BasicResponse result = new BasicResponse();
		System.out.println(meetup);
		meetupDao.save(meetup);
		// ==================
		// 유효성 체크할 부분
		// ===================
        result.status = true;
        result.data = "success";
        return new ResponseEntity<>(result, HttpStatus.OK);
	}
	
	@GetMapping("/meetup/request")
	@ApiOperation(value = "사용자가 방장인 밋업의 참가요청 리스트 가져오기")
	public Object getMeetuprequest(@RequestParam(required = true) final String userId) {
		final BasicResponse result = new BasicResponse();
		// 밋업요청 정보와 요청한 사람 정보 한번에 들고오자
		Optional<List<MeetupRequest>> data = meetupRequestDao.getMeetupRequestByUserId(userId);
		// 데이터 존재
		if(data.isPresent()) {
			System.out.println(data.toString());
			result.status = true;
	        result.data = "success";
	        result.object = data.get();;
		}else {
			result.status = true;
	        result.data = "fail";
		}
		return new ResponseEntity<>(result, HttpStatus.OK);
	}
	
	@GetMapping("/meetup/acceptMeetupRequest")
	@ApiOperation(value = "밋업요청한것을 수락해줌")
	public Object acceptMeetupRequest(@RequestParam(required = true) final String meetupId,
			@RequestParam(required = true) final String guestId) {

		ArrayList<User> userList = new ArrayList<>();
		final BasicResponse result = new BasicResponse();
		try {
			// 유저의 팔로워 리스트에 들어간다.
//			String result1 = userPageDao.insertFollowerUser(meetupId, guestId);
			// 밋업요청리스트에서 지우고
			String result2 = meetupRequestDao.deleteMeetupRequestByMeetupId(meetupId);
			// 밋업 멤버 리스트에 들어간다.
//			String result3 = meetupRequestDao.insertFollowingUser(meetupId, guestId);
			result.status = true;
			result.data = "success";
		} catch (Exception e) {
			result.status = true;
			result.data = "fail";
			return result;
		}
		return result;
	}
}
