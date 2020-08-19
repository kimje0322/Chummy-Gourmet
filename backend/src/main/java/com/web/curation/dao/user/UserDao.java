
package com.web.curation.dao.user;

import java.util.List;
import java.util.Optional;

import com.web.curation.model.user.SignupRequest;
import com.web.curation.model.user.User;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface UserDao extends JpaRepository<User, String> {
	
	// 유저아이디로 검색해서 유저를 팔로워하는 유저아이디 가져옴
    @Query(value = "SELECT * "+ 
    		"FROM user " + 
    		"WHERE user_Id = :userId " +
    		"AND (user_name like %:searchName% OR user_nickname like %:searchName%)", nativeQuery = true)
	Optional<User> getUserByUserId(String userId, String searchName);

    SignupRequest save(SignupRequest signupRequest);

    User getUserByUserId(String userId);
	
	User getUserByUserEmail(String email);
	
	Optional<User> getUserByUserNickname(String nickname);

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
		
	@Query(value ="update user set user_img = :user_img where user_id = :user_id",nativeQuery = true)
	void setUserImgByUserId(String user_img,String user_id);
	
	//userid로 해당 유저 닉네임 리턴
	@Query(value = "select user_nickname from user where user_id = :object",nativeQuery = true)
	String selectUserNickNameByUserId(Object object);
	
	//userid로 해당 유저 img 리턴
	@Query(value = "select user_img from user where user_id = :object",nativeQuery = true)
	String selectUserImgByUserId(Object object);
	
	// MEETUP ID로 해당 밋업에 참가한 유저 리스트 반환
	@Query(value = "SELECT * FROM user WHERE user_id IN (SELECT meetup_member FROM meetup_member WHERE meetup_id = :meetupId)" ,nativeQuery = true)
	Optional<List<User>> findMembersByMeetupId(int meetupId);

	
	// MEETUP ID로 해당 밋업에 참가 신청 중인 유저 리스트 반환
	@Query(value = "SELECT * FROM user WHERE user_id IN (SELECT guest_id FROM meetup_request WHERE meetup_id = :meetupId)" ,nativeQuery = true)
	Optional<List<User>> findAllByMeetupId(int meetupId);

	
}


