package com.web.curation.dao.user;

import java.util.ArrayList;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import com.web.curation.model.user.User;

public interface FindPwdDao extends JpaRepository<User, String> {
	
	// 입력한 사용자이름이 db에 있는지 유효성 검사
    @Query(value = "SELECT COUNT(*) FROM user WHERE user_name = :userName", nativeQuery = true)
	int countByUserName(String userName);
	
    // 임시비밀번호를 db에 해당 데이터에 업데이트 시켜주기
    @Transactional
    @Modifying
    @Query(value = "UPDATE user SET user_pwd = :userPwd WHERE user_name = :userName And user_email = :userEmail", nativeQuery = true)
    int setUserPwdByUserNameAndUserEmail(String userPwd,String userName,String userEmail);
    
    // 유저이름으로 검색한  이메일들 가져와라
    @Query(value = "SELECT user_email From user WHERE user_name = :userName", nativeQuery = true)
    ArrayList<String> getUserEmailByUserName(String userName);
}