package com.web.curation.controller.account;


import java.util.ArrayList;
import java.util.Map;

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
import org.springframework.web.bind.annotation.RequestBody;
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

@CrossOrigin(origins = { "*" })
@RestController
public class FindPwdController {
	
	@Autowired
	FindPwdDao findpwdDao;

	// mail보내기 위함
	@Autowired
	private JavaMailSender mailSender;
	
	// 최종적으로 확인버튼 눌렀을때 이메일로 임시비밀번호를 보내기
	@PostMapping("/account/senduserpwd")
	@ApiOperation(value = "[비밀번호찾기] 이름존재검사, 이메일=이름 일치한지 검사 , 이메일로임시비밀번호보내기")
	public Object sendpwd(@RequestBody Map<String, Object> param) {
		
		final BasicResponse result = new BasicResponse();
		
		String userName = (String) param.get("userName");
	    String userEmail = (String) param.get("userEmail");

		// db에 사용자 이름이 있는지 검사
		int count = findpwdDao.countByUserName(userName);
		System.out.println(userName+"으로 검색한 결과 count = "+result);
		// 성공 or 실패 리턴 저장위함
		ResponseEntity response = null;
		// db에 사용자가 존재하지않음
		
		if(count == 0) {
			 result.status = false;
	         result.data = "isNotExistName";
	         return new ResponseEntity<>(result, HttpStatus.OK);
		}
		 
		// 해당 유저이름에 대한 유저이메일을 디비에 가서 가져온다.
		ArrayList<String>UserEmailList = findpwdDao.getUserEmailByUserName(userName);
		boolean flag = false;
		for (String userEmaillist : UserEmailList) {
			// 사용자 이름으로 검색한 이메일 리스트중 입력한 이메일과 동일한것이 존재
			if(userEmaillist.equals(userEmail)) {
				flag = true;
				System.out.println(userEmaillist);
			}
		}
		// 일치하는게 존재 하지 않는다
		if(!flag) {
			 result.status = false;
	         result.data = "isNotExistEmail";
	         return new ResponseEntity<>(result, HttpStatus.OK);
		}
		
		// 임시 비밀번호 생성 저장 변수
		String userPwd = "";
		for (int i = 0; i < 12; i++) {
			userPwd += (char) ((Math.random() * 26) + 97);
		}
		// 이미 여기 함수 이전부분에서 정확히 이름과 이메일을 입력하기 때문에 오류가 나지 않을거라고 생각함
		int num = findpwdDao.setUserPwdByUserNameAndUserEmail(userPwd, userName, userEmail);

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
			result.status = true;
	        result.data = "success";
		} catch (MessagingException e) {
			result.status = false;
	        result.data = "fail";
		}
		return new ResponseEntity<>(result, HttpStatus.OK);
	}

	
}