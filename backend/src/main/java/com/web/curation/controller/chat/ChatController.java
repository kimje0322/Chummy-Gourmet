package com.web.curation.controller.chat;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.web.curation.dao.user.UserDao;
import com.web.curation.model.BasicResponse;

import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@ApiResponses(value = { @ApiResponse(code = 401, message = "Unauthorized", response = BasicResponse.class),
		@ApiResponse(code = 403, message = "Forbidden", response = BasicResponse.class),
		@ApiResponse(code = 404, message = "Not Found", response = BasicResponse.class),
		@ApiResponse(code = 500, message = "Failure", response = BasicResponse.class) })

@CrossOrigin(origins = { "*" })
@RestController
public class ChatController {

	@Autowired
	UserDao userDao;
	
	@PostMapping("/chat/nickname")
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
}
