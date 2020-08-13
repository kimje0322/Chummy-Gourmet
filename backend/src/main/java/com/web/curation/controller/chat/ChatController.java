package com.web.curation.controller.chat;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.web.curation.dao.meetup.MeetUpDao;
import com.web.curation.dao.user.UserDao;
import com.web.curation.model.BasicResponse;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import net.bytebuddy.implementation.bind.annotation.Pipe;

@ApiResponses(value = { @ApiResponse(code = 401, message = "Unauthorized", response = BasicResponse.class),
		@ApiResponse(code = 403, message = "Forbidden", response = BasicResponse.class),
		@ApiResponse(code = 404, message = "Not Found", response = BasicResponse.class),
		@ApiResponse(code = 500, message = "Failure", response = BasicResponse.class) })

@CrossOrigin(origins = { "*" })
@RestController
public class ChatController {

	@Autowired
	UserDao userDao;
	
	@Autowired
	MeetUpDao meetUpDao;
	
	@PostMapping("/chat/nickname")
	@ApiOperation(value = "[채팅] 유저 아이디 배열을 입력받아 유저 닉네임 배열로 변환해줌")
	public List<Object> nickname(@RequestBody(required = true) List<Object> data){
		
		System.out.println(data);
		System.out.println(data.size());
		
		List<Object> nickname= new ArrayList<Object>();
		
		for(int i = 0 ; i<data.size();i++) {
			nickname.add(userDao.selectUserNickNameByUserId(data.get(i)));
		}
		
		System.out.println(nickname);
		
		return nickname;
		
	}
	
	@PostMapping("/chat/meetup")
	@ApiOperation(value = "[채팅] 유저 아이디 배열을 입력받아 유저 닉네임 배열로 변환해줌")
	public List<Integer> meetup(@RequestBody(required = true) String data){
		
		List<Integer> id= new ArrayList<Integer>();
		
		id = meetUpDao.selectAllUserByMeetUpID(data);
		Collections.sort(id);
		return id;
		
	}
}
