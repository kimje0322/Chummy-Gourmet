package com.web.curation.dao.meetup;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.web.curation.model.meetup.MeetUp;
import com.web.curation.model.review.Restaurant;

public interface MeetUpDao extends JpaRepository<MeetUp, String> {

	//리뷰테이블에 들어있는 밋업 아이디로 밋업 객체 가져오기
	@Query(value = "SELECT meetup_id,user_name AS meetup_master, meetup_title, meetup_content,meetup_location,meetup_date " + 
			"FROM meetup a " + 
			"INNER JOIN user b ON a.meetup_master = b.user_id " + 
"where a.meetup_id = :id", nativeQuery = true)
	Optional<MeetUp> selectMeetUpById(int id);
}
