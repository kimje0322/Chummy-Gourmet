package com.web.curation.dao.meetup;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.web.curation.model.meetup.Meetup;

@Repository
public interface MeetUpDao extends JpaRepository<Meetup, String> {

	//리뷰테이블에 들어있는 밋업 아이디로 밋업 객체 가져오기
	@Query(value = "SELECT * " + 
			"FROM meetup a " + 
			"INNER JOIN user b ON a.meetup_master = b.user_id " + 
            "where a.meetup_id = (select meetup_id from review where review_id = :id)", nativeQuery = true)
	Optional<Meetup> selectMeetUpById(int id);
	
	// 밋업 아이디로 밋업 객체 가져오기
	@Query(value = "SELECT * " + 
			"FROM meetup " +
			"WHERE meetup_id = :id", nativeQuery = true)
	Optional<Meetup> getMeetupByMeetupID(int id);
	
	// 유저 아이디로 참석한 밋업 객체 리스트 가져오기
	@Query(value = "SELECT a.* " + "FROM meetup a " + "INNER JOIN meetup_member b ON a.meetup_id = b.meetup_id "
			+ "WHERE b.meetup_member = :userId", nativeQuery = true)
	Optional<List<Meetup>> getMeetupByUserID(int userId);
	
	// 해당 지역(구)에 있는 밋업 리스트 가져오기
	@Query(value =
				"SELECT * " + 
				"FROM meetup " + 
				"WHERE 1 = 1 " +
				"AND meetup_address LIKE %:location1% " +
				"AND meetup_address LIKE %:location2% ", nativeQuery = true)
	List<Meetup> selectMeetUpByLocation(String location1, String location2);
	
	
	
	// 밋업 생성
	@Query(value = "INSERT INTO meetup "
			+ "(meetup_master, meetup_title, meetup_content, "
			+ "meetup_location, meetup_address, meetup_date, "
			+ "meetup_max_personnel, meetup_img, meetup_personality, meetup_category) "
			+ "VALUES "
			+ "(:#{#meetup.master}, :#{#meetup.title}, :#{#meetup.content}, :#{#meetup.location}, :#{#meetup.address},:#{#meetup.date}, :#{#meetup.maxPersonnel}, :#{#meetup.img}, :#{#meetup.personalities}, :#{#meetup.category})", nativeQuery = true)
	Meetup save(Meetup meetup);

	@Query(value = "select meetup_member from meetup_member where meetup_id = :data", nativeQuery = true)
	List<Integer> selectAllUserByMeetUpID(String data);





}
