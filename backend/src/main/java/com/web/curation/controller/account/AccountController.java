package com.web.curation.controller.account;

import static org.junit.Assert.assertEquals;
import java.util.ArrayList;
import java.util.List;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.Optional;

import javax.validation.Valid;

import com.web.curation.dao.user.UserDao;
import com.web.curation.dao.user.UserDetailDao;
import com.web.curation.model.BasicResponse;
import com.web.curation.model.user.SignupRequest;
import com.web.curation.model.user.User;
import com.web.curation.model.user.UserDetail;

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

@CrossOrigin(origins = { "*" })
@RestController
public class AccountController {

   @Autowired
   UserDetailDao userDetailDao;
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
//            String token = getToken(userOpt);
         String token = getToken(email, password);
         response = new ResponseEntity<>(token, HttpStatus.OK);
      } else {
         response = new ResponseEntity<>("Fail", HttpStatus.NOT_FOUND);
      }

      return response;
   }

   @GetMapping("/account/signup/valid")
   @ApiOperation(value = "닉네임, 이메일 중복체크")
   public Object nicknameAndEmailVaildCheck(String nickname, String email) {
      User originUser = null;
      final BasicResponse result = new BasicResponse();
      boolean isExistNickname = false;
      boolean isExistEmail = false;

      // 이메일 중복 검사
      originUser = userDao.getUserByUserEmail(email);
      if (originUser != null)
         isExistEmail = true;

      // 닉네임 중복 검사
      originUser = userDao.getUserByUserNickname(nickname);
      if (originUser != null)
         isExistNickname = true;

      // 기존에 가입한 동일 닉네임이 있다면
      if (isExistNickname) {
         result.status = false;
         result.data = "isExistNickname";
      }

      // 기존에 가입한 동일 이메일이 있다면
      else if (isExistEmail) {
         result.status = false;
         result.data = "isExistEmail";
      }

      // 기존 유저가 없다면
      else {
         result.status = true;
         result.data = "success";
      }
      return new ResponseEntity<>(result, HttpStatus.OK);
   }

   @PostMapping("/account/signup")
   @ApiOperation(value = "가입하기")
   public Object signup(@Valid @RequestBody SignupRequest request) {
      User originUser = null;
      final BasicResponse result = new BasicResponse();

      // user entity db 저장
      User newUser = new User(request.getUserPwd(), request.getUserEmail(), request.getUserName(),
            request.getUserNickname(), request.getUserPhone(), request.getUserComment());
      userDao.save(newUser);
      // user detail entity db 저장
      UserDetail newUserDetail = new UserDetail(newUser.getUserId(), request.getUserGender(), request.getUserAge(),
            request.getUserFavorite().toString(), request.getUserPersonality().toString(),
            request.getUserInterest().toString());
      System.out.println(newUserDetail);
      userDetailDao.save(newUserDetail);

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