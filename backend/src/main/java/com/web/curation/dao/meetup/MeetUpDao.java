package com.web.curation.dao.meetup;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.web.curation.model.meetup.MeetUp;
import com.web.curation.model.review.Restaurant;

public interface MeetUpDao extends JpaRepository<MeetUp, String> {
	// name으로 가게가 있는지 없는지 검색 - review 등록용도
	@Query(value = "SELECT * from meetup where meetup_id = :id", nativeQuery = true)
//	MeetUp selectMeetUpById(int id);
	Optional<MeetUp> selectMeetUpById(int id);
}
