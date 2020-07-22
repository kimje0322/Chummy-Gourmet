package com.web.curation.controller.account;


import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.web.curation.dao.user.UserDao;
import com.web.curation.model.BasicResponse;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@ApiResponses(value = { @ApiResponse(code = 401, message = "Unauthorized", response = BasicResponse.class),
        @ApiResponse(code = 403, message = "Forbidden", response = BasicResponse.class),
        @ApiResponse(code = 404, message = "Not Found", response = BasicResponse.class),
        @ApiResponse(code = 500, message = "Failure", response = BasicResponse.class) })

@CrossOrigin(origins = { "http://localhost:3000" })
@RestController
public class FindPwdController {
	@Autowired
	UserDao userDao;

	// mail보내기 위함
	@Autowired
	private JavaMailSender mailSender;

	// 최종적으로 확인버튼 눌렀을때 이메일로 임시비밀번호를 보내기
	@PostMapping("/account/sendpwd")
	@ApiOperation(value = "이메일로임시비밀번호보내기")
	public Object sendpwd(@RequestParam(required = true) final String userName,
			@RequestParam(required = true) final String userEmail) {

		// 임시 비밀번호 저장 변수
		String userPwd = "";
		for (int i = 0; i < 12; i++) {
			userPwd += (char) ((Math.random() * 26) + 97);
		}
		// 
		int num = userDao.setUserPwdByUserNameAndUserEmail(userPwd, userName, userEmail);

		// 성공 or 실패 리턴 저장위함
		ResponseEntity response = null;
		
		try {
			MimeMessage msg = mailSender.createMimeMessage();
			MimeMessageHelper messageHelper = new MimeMessageHelper(msg, true, "UTF-8");
			messageHelper.setFrom("tmdwkd2.gmail.com"); // 보내는사람의 주소(없으면 동작x)
			messageHelper.setTo(userEmail); // 받는사람의 주소
			messageHelper.setSubject(userName + "님의 비밀번호 찾기 메일입니다."); // 제목
			messageHelper.setText("임시 패스워드는  " + userPwd + " 입니다."); // 내용
			msg.setRecipients(MimeMessage.RecipientType.TO, InternetAddress.parse(userEmail));
			System.out.println(messageHelper);
			mailSender.send(msg);
			response = new ResponseEntity<>("success", HttpStatus.OK);
		} catch (MessagingException e) {
			response = new ResponseEntity<>("Fail", HttpStatus.NOT_FOUND);
		}
		return response;
	}

	
}