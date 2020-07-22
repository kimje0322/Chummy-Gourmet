
package com.web.curation.dao.user;

import java.util.Optional;

import com.web.curation.model.user.SignupRequest;
import com.web.curation.model.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

public interface UserDao extends JpaRepository<User, String> {
    User getUserByUserEmail(String email);
    User getUserByUserNickname(String nickname);
    SignupRequest save(SignupRequest signupRequest);
    Optional<User> findUserByUserEmailAndUserPwd(String email, String password);


    // 임시비밀번호를 db에 해당 데이터에 업데이트 시켜주기
    @Transactional
    @Modifying
    @Query(value = "UPDATE user SET user_pwd = :userPwd WHERE user_name = :userName And user_email = :userEmail", nativeQuery = true)
    int setUserPwdByUserNameAndUserEmail(String userPwd,String userName,String userEmail);
    
    // 
    Optional<User> findUserNameByUserEmail(String userEmail);
}
