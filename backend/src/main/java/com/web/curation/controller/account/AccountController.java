package com.web.curation.controller.account;

import static org.junit.Assert.assertEquals;
import java.util.ArrayList;
import java.util.List;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
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
import io.fusionauth.jwt.Signer;
import io.fusionauth.jwt.Verifier;
import io.fusionauth.jwt.domain.JWT;
import io.fusionauth.jwt.hmac.HMACSigner;
import io.fusionauth.jwt.hmac.HMACVerifier;
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
			String token = getToken(email, password);
			response = new ResponseEntity<>(token, HttpStatus.OK);
		} else {
			response = new ResponseEntity<>("Fail", HttpStatus.NOT_FOUND);
		}

		return response;
	}
	
	//토큰이 유호한지 확인
	@PostMapping("/account/checktoken")
	public boolean checkToken(@RequestParam(required = true) final String token) {
		if(cmpToken(token))return true;
		return false;
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

	// 암호화 하는 방법임 : 내이름 넣음
	static Signer signer = HMACSigner.newSHA256Signer("park se hun");

	static public String getToken(String email, String password) {
		// Userid로 토큰을 만든다.
		// plusMinutes 는 토큰을 등록하는 시간임 지금은 1분
		JWT jwt = new JWT().setIssuer(email).setIssuedAt(ZonedDateTime.now(ZoneOffset.UTC))
				.setSubject("f1e33ab3-027f-47c5-bb07-8dd8ab37a2d3")
				.setExpiration(ZonedDateTime.now(ZoneOffset.UTC).plusMinutes(1));
		// Sign and encode the JWT to a JSON string representation
		String token = JWT.getEncoder().encode(jwt, signer);
		System.out.println(cmpToken(token));
		return token;
	}

	// 복호화 하는 방법 : 내이름 넣음
	// 토큰이 필요한 API 정보에 대해서 유효성을 체크해주면된다
	static Verifier verifier = HMACVerifier.newVerifier("park se hun");

	static public boolean cmpToken(String token) {
		try {
			// Build an HMC verifier using the same secret that was used to sign the JWT
			JWT jwt = JWT.getDecoder().decode(token, verifier);
			assertEquals(jwt.subject, "f1e33ab3-027f-47c5-bb07-8dd8ab37a2d3");
		} catch (Exception e) {
			return false;
		}
		return true;
	}
}