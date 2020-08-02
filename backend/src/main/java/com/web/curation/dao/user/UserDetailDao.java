
package com.web.curation.dao.user;


import com.web.curation.model.user.UserDetail;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

public interface UserDetailDao extends JpaRepository<UserDetail, String> {
	
	UserDetail getUserDetailByUserId(String userId);
	
	// 사용자 추가정보 업데이트 시켜주기
	@Transactional
    @Modifying
    @Query(value = "UPDATE user_detail SET user_favorite = :userFavorite, user_personality = :userPersonality, user_interest = :userInterest WHERE user_id = :userId", nativeQuery = true)
	int setUserDetailByUserId(String userId,String userFavorite,String userPersonality,String userInterest);
    
}