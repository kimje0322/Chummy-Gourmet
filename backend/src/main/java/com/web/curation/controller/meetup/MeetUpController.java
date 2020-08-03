package com.web.curation.controller.meetup;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
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
	//신규 밋업 등록
	@PostMapping("/meetup/")
	@ApiOperation(value = "신규 밋업 등록")
	public void createMeetUp(@RequestBody Meetup meetup) {
		System.out.println(meetup);
		meetupDao.save(meetup);
//		System.out.println(mu);
		
		
//		Optional<MeetUp> data = meetupDao.selectMeetUpById(id);
//		MeetUp test= data.get();
//		System.out.println(data.get());
		
//		return data.get();
	}
}
