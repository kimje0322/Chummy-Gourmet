
package com.web.curation.dao.user;

import java.util.Optional;

import com.web.curation.model.user.SignupRequest;
import com.web.curation.model.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserDao extends JpaRepository<User, String> {
    User getUserByUserEmail(String email);
    User getUserByUserNickname(String nickname);
    SignupRequest save(SignupRequest signupRequest);
    Optional<User> findUserByUserEmailAndUserPwd(String email, String password);

}
