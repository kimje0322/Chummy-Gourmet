package com.web.curation.dao.meetup;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.web.curation.model.meetup.MeetUp;
import com.web.curation.model.review.Restaurant;

public interface MeetUpDao extends JpaRepository<MeetUp, String> {

	//리뷰 아이디로 밋업 객체 가져오기
	@Query(value = "SELECT * from meetup where meetup_id = :id", nativeQuery = true)
//	MeetUp selectMeetUpById(int id);
	Optional<MeetUp> selectMeetUpById(int id);
}
