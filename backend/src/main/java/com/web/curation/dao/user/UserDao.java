
package com.web.curation.dao.user;

import java.util.Optional;

import com.web.curation.model.user.SignupRequest;
import com.web.curation.model.user.User;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface UserDao extends JpaRepository<User, String> {

    SignupRequest save(SignupRequest signupRequest);

	User getUserByUserName(String userName);
	
	User getUserByUserEmail(String email);
	
	User getUserByUserNickname(String nickname);

	Optional<User> findUserByUserEmailAndUserPwd(String email, String password);


	@Query(value = "UPDATE user SET user_nickname = :userNickname ,"+
	"user_pwd = :userPwd ,"+
	"user_comment = :userComment "+
	"WHERE user_id = :userId", nativeQuery = true)
	void setUserNicknameUserPwdUserCommentByUserId(String userId, String userNickname, String userPwd, String userComment); 



}
