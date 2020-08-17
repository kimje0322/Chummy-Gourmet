package com.web.curation.dao.meetup;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.web.curation.model.meetup.Meetup;
import com.web.curation.model.meetup.MeetupRequest;

@Repository
public interface MeetUpRequsetDao extends JpaRepository<MeetupRequest, String> {
	
	// 마스터 아이디로 밋업 요청 데이터 가져오기
	@Query(value = "SELECT * " + 
			"FROM meetup_request " +
			"WHERE host_id = :userId", nativeQuery = true)
	Optional<List<MeetupRequest>> getMeetupRequestByUserId(String userId);
	

}
