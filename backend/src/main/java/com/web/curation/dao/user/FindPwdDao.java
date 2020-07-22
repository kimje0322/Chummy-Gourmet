package com.web.curation.dao.user;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import com.web.curation.model.user.User;

public interface FindPwdDao extends JpaRepository<User, String> {

    // 임시비밀번호를 db에 해당 데이터에 업데이트 시켜주기
    @Transactional
    @Modifying
    @Query(value = "UPDATE user SET user_pwd = :userPwd WHERE user_name = :userName And user_email = :userEmail", nativeQuery = true)
    int setUserPwdByUserNameAndUserEmail(String userPwd,String userName,String userEmail);
    
    // 유저이름으로 검색해서 유저 이메일 가져와라
    @Query(value = "SELECT user_email From user WHERE user_name = :userName", nativeQuery = true)
    String getUserEmailByUserName(String userName);
}