package com.web.curation.controller.account;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import com.web.curation.dao.user.UserDao;
import com.web.curation.model.BasicResponse;
import com.web.curation.model.user.SignupRequest;
import com.web.curation.model.user.User;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.beans.factory.annotation.Autowired;

import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@ApiResponses(value = { @ApiResponse(code = 401, message = "Unauthorized", response = BasicResponse.class),
		@ApiResponse(code = 403, message = "Forbidden", response = BasicResponse.class),
		@ApiResponse(code = 404, message = "Not Found", response = BasicResponse.class),
		@ApiResponse(code = 500, message = "Failure", response = BasicResponse.class) })

@CrossOrigin(origins = { "http://localhost:3000" })
@RestController
public class AccountController {

	@Autowired
	UserDao userDao;

	@GetMapping("/account/login")
	@ApiOperation(value = "로그인")
	public Object login(@RequestParam(required = true) final String email,
			@RequestParam(required = true) final String password) {

		Optional<User> userOpt = userDao.findUserByUserEmailAndUserPwd(email, password);

		ResponseEntity response = null;

		if (userOpt.isPresent()) {
			final BasicResponse result = new BasicResponse();
			result.status = true;
			result.data = "success";
			response = new ResponseEntity<>(result, HttpStatus.OK);
		} else {
			response = new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
		}

		return response;
	}

	@PostMapping("/account/signup")
	@ApiOperation(value = "가입하기")
	public Object signup(@Valid @RequestBody SignupRequest request) {
		List<User> originUsers = new ArrayList<>();
		User originUser = null;
		originUser = userDao.getUserByUserEmail(request.getUserEmail());
		if (originUser != null)
			originUsers.add(originUser);
		originUser = userDao.getUserByUserNickname(request.getUserNickname());
		if (originUser != null)
			originUsers.add(originUser);
		final BasicResponse result = new BasicResponse();
		// 기존에 가입한 유저가 없다면
		if (originUsers.isEmpty()) {
			userDao.save(new User(request.getUserPwd(), request.getUserEmail(), request.getUserName(),
					request.getUserNickname(), request.getUserPhone()));
			result.status = true;
			result.data = "success";
		}
		// 기존에 가입한 유저가 있다면
		else {
			result.status = false;
			result.data = "fail";
		}
		return new ResponseEntity<>(result, HttpStatus.OK);
	}

}