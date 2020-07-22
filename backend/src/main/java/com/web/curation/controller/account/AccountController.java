package com.web.curation.controller.account;

import static org.junit.Assert.assertEquals;

import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.Optional;

import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.validation.Valid;

import com.web.curation.dao.user.UserDao;
import com.web.curation.model.BasicResponse;
import com.web.curation.model.user.SignupRequest;
import com.web.curation.model.user.User;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
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
    
    // mail보내기 위함
    @Autowired
	private JavaMailSender mailSender;

    @PostMapping("/account/sendpwd")
    @ApiOperation(value = "이메일로비밀번호보내기")
    public Object sendpwd(@RequestParam(required = true) final String userName,
    		@RequestParam(required = true) final String userEmail) {
    	// 여기는 최종적으로 확인버튼 눌렀을때 이메일로 임시비밀번호를 보내기 하는 곳임
    	
		System.out.println(userName + " " + userEmail + " ") ;
		User user = userDao.getUserPwdByUserNameAndUserEmail(userName, userEmail);
		System.out.println("비밀번호는 = "+user.getUserPwd());
		String UserPwd = user.getUserPwd();
		// 성공 or 실패 리턴
		ResponseEntity response = null;
	    
	    try {
			MimeMessage msg = mailSender.createMimeMessage();
			MimeMessageHelper messageHelper = new MimeMessageHelper(msg, true, "UTF-8");
			messageHelper.setFrom("tmdwkd2.gmail.com"); // 보내는사람의 주소(없으면 동작x)
			messageHelper.setTo(userEmail); // 받는사람의 주소
			messageHelper.setSubject(userName + "님의 비밀번호 찾기 메일입니다."); // 제목
			messageHelper.setText("패스워드는  " + UserPwd + " 입니다."); // 내용
			msg.setRecipients(MimeMessage.RecipientType.TO, InternetAddress.parse(userEmail));
			System.out.println(messageHelper);
			mailSender.send(msg);
			response = new ResponseEntity<>("success", HttpStatus.OK);	
		} catch (MessagingException e) {
			response = new ResponseEntity<>("Fail", HttpStatus.NOT_FOUND);
		}
        return response;
    }
    
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
//            String token = getToken(userOpt);
            String token = getToken(email, password);
            response = new ResponseEntity<>(token, HttpStatus.OK);
        } else {
            response = new ResponseEntity<>("Fail", HttpStatus.NOT_FOUND);
        }

        return response;
    }

    @PostMapping("/account/signup")
    @ApiOperation(value = "가입하기")

    public Object signup(@Valid @RequestBody SignupRequest request) {
        // 이메일, 닉네임 중복처리 필수
        // 회원가입단을 생성해 보세요.

        final BasicResponse result = new BasicResponse();
        result.status = true;
        result.data = "success";

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