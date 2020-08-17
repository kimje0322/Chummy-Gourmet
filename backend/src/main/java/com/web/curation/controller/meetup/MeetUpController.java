package com.web.curation.controller.meetup;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
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
	MeetUpRequsetDao meetupRequestDao;
	
	@Autowired
	UserDao userDao;
	
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
	@GetMapping("/meetup/search/{id}")
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
	
	// 밋업 참석
	@GetMapping("/meetup")
	@ApiOperation(value = "밋업 참석")
	public Object appliMeetupRequest(@RequestParam int meetupId, @RequestParam int userId) {
		final BasicResponse result = new BasicResponse();
		meetupDao.save(meetupId, userId);
		meetupDao.personnelUp(meetupId);
		result.status = true;
		result.data = "success";
		
        return new ResponseEntity<>(result, HttpStatus.OK);
	}
	
	// 밋업 참석 취소
	@DeleteMapping("/meetup")
	@ApiOperation(value = "밋업 참석 취소")
	public Object deleteMeetup(@RequestParam int meetupId, @RequestParam int userId) {
		final BasicResponse result = new BasicResponse();
		meetupDao.deleteByMeetupIdAndUserId(meetupId, userId);
		meetupDao.personnelDown(meetupId);
		result.status = true;
		result.data = "success";
		
        return new ResponseEntity<>(result, HttpStatus.OK);
	}
	
	// 밋업 참석 요청
	@PostMapping("/meetup/request")
	@ApiOperation(value = "밋업 참석 요청")
	public Object createMeetupRequest(@RequestBody MeetupRequest meetupRequest) {
		final BasicResponse result = new BasicResponse();
		System.out.println(meetupRequest);
		meetupRequestDao.save(meetupRequest);
		// ==================
		// 유효성 체크할 부분
		// ===================
        result.status = true;
        result.data = "success";
        return new ResponseEntity<>(result, HttpStatus.OK);
	}
	
	
	// 밋업 참석 요청 취소
	@DeleteMapping("/meetup/request/{requestId}")
	@ApiOperation(value = "밋업 참석 요청 취소")
	public Object deleteMeetupRequest(@PathVariable String requestId) {
		final BasicResponse result = new BasicResponse();
		meetupRequestDao.deleteById(requestId);
		result.status = true;
		result.data = "success";

        return new ResponseEntity<>(result, HttpStatus.OK);
	}
	
	// 해당 밋업에 신청중인 유저 아이디 리스트 조회
	@GetMapping("/meetup/request/{meetupId}")
	@ApiOperation(value = "해당 밋업에 참석 요청중인 유저 리스트 조회")
	public Object getMeetupRequest(@PathVariable int meetupId) {
		final BasicResponse result = new BasicResponse();
		Optional<List<MeetupRequest>> users = meetupRequestDao.findAllRequestByMeetupId(meetupId);
		if(users.isPresent()) {
			result.status = true;
			result.data = "success";
			result.object = users;
		}
		else {
			result.status = true;
			result.data = "fail";
		}
        return new ResponseEntity<>(result, HttpStatus.OK);
	}
}
