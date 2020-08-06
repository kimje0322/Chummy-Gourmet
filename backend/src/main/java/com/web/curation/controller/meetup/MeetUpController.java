package com.web.curation.controller.meetup;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
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
import com.web.curation.model.BasicResponse;
import com.web.curation.model.meetup.Meetup;

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
	
	//밋업 정보 조회
	@GetMapping("/meetup/search")
	@ApiOperation(value = "밋업 정보 조회")
	public Meetup searchMeetUp(@RequestParam(required = true) final int id) {
		
		Optional<Meetup> data = meetupDao.selectMeetUpById(id);
		Meetup test= data.get();
		System.out.println(data.get());
		
		return data.get();
	}
	
	//해당 지역의 밋업 정보 조회
	@GetMapping("/meetup/search/{location}")
	@ApiOperation(value = "해당 지역의 밋업 정보 조회")
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
	@PostMapping("/meetup/")
	@ApiOperation(value = "신규 밋업 등록")
	public Object createMeetUp(@RequestBody Meetup meetup) {
		final BasicResponse result = new BasicResponse();
		meetupDao.save(meetup);
		// ==================
		// 유효성 체크할 부분
		// ===================
        result.status = true;
        result.data = "success";
        return new ResponseEntity<>(result, HttpStatus.OK);
	}
}
