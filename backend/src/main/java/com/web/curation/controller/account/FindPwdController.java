package com.web.curation.controller.account;


import java.util.ArrayList;

import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.web.curation.dao.user.FindPwdDao;
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
	FindPwdDao findpwdDao;

	// mail보내기 위함
	@Autowired
	private JavaMailSender mailSender;
	
	// 사용자 이름 입력시 디비에 있는지 검사
	@GetMapping("/account/checkusername")
	@ApiOperation(value = "[비밀번호찾기] 사용자 이름 입력시 디비에 있는지 검사(동일한이름으로 존재 가능)")
	public Object checkusername(@RequestParam(required = true) final String userName) {
		
		// db에 사용자 이름이 있는지 검사
		int result = findpwdDao.countByUserName(userName);
		System.out.println(userName+"으로 검색한 결과 count = "+result);
		// 성공 or 실패 리턴 저장위함
		ResponseEntity response = null;
		// 카운트가 1보다 크면 사용자가 db에 존재함()
		if(result >= 1) {
			response = new ResponseEntity<>("success", HttpStatus.OK);
		}
		// db에 사용자가 존재하지않음
		else {
			response = new ResponseEntity<>("fail", HttpStatus.NOT_FOUND);
		}
		return response;
	}
		
	// 이메일입력 할때 이름과 이메일이 디비에 같은 것인지 아닌지 ajax통신을 위함
	@GetMapping("/account/checkuseremail")
	@ApiOperation(value = "[비밀번호찾기] 입력한 이메일과  db에 등록된 (이름,이메일)과 일치하는지검사")
	public Object emailequalsusername(@RequestParam(required = true) final String userName,
			@RequestParam(required = true) final String userEmail) {
		
		// 해당 유저이름에 대한 유저이메일을 디비에 가서 가져온다.
		ArrayList<String>UserEmailList = findpwdDao.getUserEmailByUserName(userName);
		
		// 성공 or 실패 리턴 저장위함
		ResponseEntity response = null;
				
		for (String userEmaillist : UserEmailList) {
			// 사용자 이름으로 검색한 이메일 리스트중 입력한 이메일과 동일한것이 존재
			if(userEmaillist.equals(userEmail)) {
				System.out.println(userEmaillist);
				return response = new ResponseEntity<>("success", HttpStatus.OK);
			}
		}
		// 불일치하다
		return response = new ResponseEntity<>("fail", HttpStatus.NOT_FOUND);
	}
	
	// 최종적으로 확인버튼 눌렀을때 이메일로 임시비밀번호를 보내기
	@PostMapping("/account/senduserpwd")
	@ApiOperation(value = "[비밀번호찾기] 이메일로임시비밀번호보내기")
	public Object sendpwd(@RequestParam(required = true) final String userName,
			@RequestParam(required = true) final String userEmail) {

		// 임시 비밀번호 생성 저장 변수
		String userPwd = "";
		for (int i = 0; i < 12; i++) {
			userPwd += (char) ((Math.random() * 26) + 97);
		}
		// 이미 여기 함수 이전부분에서 정확히 이름과 이메일을 입력하기 때문에 오류가 나지 않을거라고 생각함
		int num = findpwdDao.setUserPwdByUserNameAndUserEmail(userPwd, userName, userEmail);

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