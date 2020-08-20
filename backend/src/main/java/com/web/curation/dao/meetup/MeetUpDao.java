package com.web.curation.dao.meetup;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.web.curation.model.meetup.Meetup;

@Repository
public interface MeetUpDao extends JpaRepository<Meetup, String> {

	// 리뷰테이블에 들어있는 밋업 아이디로 밋업 객체 가져오기
	@Query(value = "SELECT * " + "FROM meetup a " + "INNER JOIN user b ON a.meetup_master = b.user_id "
			+ "where a.meetup_id = (select meetup_id from review where review_id = :id)", nativeQuery = true)
	Optional<Meetup> selectMeetUpById(int id);

	// 밋업 아이디로 밋업 객체 가져오기
	@Query(value = "SELECT * " + "FROM meetup " + "WHERE meetup_id = :id", nativeQuery = true)
	Optional<Meetup> getMeetupByMeetupID(int id);

	// 유저 아이디로 참석한 밋업 객체 리스트 가져오기
	@Query(value = "SELECT a.* " + "FROM meetup a " + "INNER JOIN meetup_member b ON a.meetup_id = b.meetup_id "
			+ "WHERE b.meetup_member = :userId", nativeQuery = true)
	Optional<List<Meetup>> getMeetupByUserID(int userId);

	// 해당 지역(구)에 있는 밋업 리스트 가져오기
	@Query(value = "SELECT * " + "FROM meetup " + "WHERE 1 = 1 " + "AND meetup_address LIKE %:location1% "
			+ "AND meetup_address LIKE %:location2% ", nativeQuery = true)
	List<Meetup> selectMeetUpByLocation(String location1, String location2);

	// 밋업 생성
	@Query(value = "INSERT INTO meetup " + "(meetup_master, meetup_title, meetup_content, "
			+ "meetup_location, meetup_address, meetup_date, "
			+ "meetup_max_personnel, meetup_img, meetup_personality, " + "meetup_category, meetup_rest_id) " + "VALUES "
			+ "(:#{#meetup.master}, :#{#meetup.title}, :#{#meetup.content}, "
			+ ":#{#meetup.location}, :#{#meetup.address},:#{#meetup.date}, "
			+ ":#{#meetup.maxPersonnel}, :#{#meetup.img}, :#{#meetup.personalities}, "
			+ ":#{#meetup.category}, :#{#meetup.restId})", nativeQuery = true)
	Meetup save(Meetup meetup);

	// 해당 밋업에 참가한 user id 반환
	@Query(value = "select meetup_member from meetup_member where meetup_id = :data", nativeQuery = true)
	List<Integer> selectAllUserByMeetUpID(String data);

	// MEETUP ID, USER ID 에 해당하는 참석
	@Query(value = "INSERT INTO meetup_member VALUES (:meetupId, :userId) ", nativeQuery = true)
	void save(int meetupId, int userId);

	// MEETUP 현재원 증가
	@Query(value = "UPDATE meetup SET meetup_cur_personnel = meetup_cur_personnel+1 WHERE meetup_id = :meetupId", nativeQuery = true)
	void personnelUp(int meetupId);

	// MEETUP ID, USER ID 에 해당하는 참석 삭제
	@Query(value = "DELETE FROM meetup_member WHERE meetup_id = :meetupId AND meetup_member = :userId", nativeQuery = true)
	void deleteByMeetupIdAndUserId(int meetupId, int userId);

	// MEETUP 현재원 감소
	@Query(value = "UPDATE meetup SET meetup_cur_personnel = meetup_cur_personnel-1 WHERE meetup_id = :meetupId", nativeQuery = true)
	void personnelDown(int meetupId);
	
	// 밋업 제목으로 밋업 정보 전부 반환(밋업 아이디 필요해서) 밋업생성하자마자 방장을 밋업 멤버로 넣으려고
	@Query(value = "select * from meetup where meetup_title = :title", nativeQuery = true)
	Meetup findByTitle(String title);

}
