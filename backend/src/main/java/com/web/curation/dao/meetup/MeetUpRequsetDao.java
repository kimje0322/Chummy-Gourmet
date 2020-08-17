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
	
	// 밋업 요청 등록
	@Query(value = "INSERT INTO meetup_request (meetup_id, host_id, guest_id, request_message)" + 
			"VALUES (:#{#meetupRequest.meetupId}, :#{#meetupRequest.hostId}, :#{#meetupRequest.guestId}, :#{#meetupRequest.requestMessage})"
			, nativeQuery = true)
	MeetupRequest save(MeetupRequest meetupRequest);
	
	// MEETUP ID로 해당 밋업에 참가중인 Request들 반환
	@Query(value="SELECT * FROM meetup_request WHERE meetup_id = :meetupId", nativeQuery = true)
	Optional<List<MeetupRequest>> findAllRequestByMeetupId(int meetupId);


}
