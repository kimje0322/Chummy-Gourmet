
package com.web.curation.dao.user;

import java.util.List;
import java.util.Optional;

import com.web.curation.model.user.SignupRequest;
import com.web.curation.model.user.User;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface UserDao extends JpaRepository<User, String> {

    SignupRequest save(SignupRequest signupRequest);

    User getUserByUserId(String userId);
	
	User getUserByUserEmail(String email);
	
	User getUserByUserNickname(String nickname);

	Optional<User> findUserByUserEmailAndUserPwd(String email, String password);
	
	Optional<User> findUserByUserEmail(String email);


	@Query(value = "UPDATE user SET user_nickname = :userNickname ,"+
	"user_pwd = :userPwd ,"+
	"user_comment = :userComment "+
	"WHERE user_id = :userId", nativeQuery = true)
	void setUserNicknameUserPwdUserCommentByUserId(String userId, String userNickname, String userPwd, String userComment); 

	//리뷰 아이디로 리뷰를 쓴 밋업 팀의 닉네임 모두 조회
//		@Query(value = "SELECT * FROM user WHERE user_id = 1 " ,nativeQuery = true) 
		@Query(value = "SELECT * FROM user WHERE user_id in " + 
				"(SELECT meetup_member FROM meetup_member WHERE meetup_id = :id)",nativeQuery = true)
		List<User> selectUserNickNameByMeetUpId(int id);
}
