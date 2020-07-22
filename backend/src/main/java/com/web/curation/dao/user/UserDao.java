
package com.web.curation.dao.user;

import java.util.Optional;

import com.web.curation.model.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

public interface UserDao extends JpaRepository<User, String> {
    User getUserByUserEmail(String email);

    Optional<User> findUserByUserEmailAndUserPwd(String email, String password);

    @Transactional
    @Modifying
    @Query(value = "UPDATE user SET user_pwd = :userPwd WHERE user_name = :userName And user_email = :userEmail", nativeQuery = true)
    int setUserPwdByUserNameAndUserEmail(String userPwd,String userName,String userEmail);
    
}
