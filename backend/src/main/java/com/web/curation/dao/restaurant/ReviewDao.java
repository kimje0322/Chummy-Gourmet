package com.web.curation.dao.restaurant;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.web.curation.model.review.Restaurant;
import com.web.curation.model.review.Review;

public interface ReviewDao extends JpaRepository<Review, String>{
	
	//리뷰 등록
	@Query(value = "INSERT INTO review "
			+ "(review_title, review_content, review_writer, "
			+ "review_category, rest_id, meetup_id) "
			+ "VALUES "
			+ "(:#{#review.title}, :#{#review.content}, :#{#review.writer}, "
			+ ":#{#review.category}, :#{#review.restId}, :#{#review.meetupId})", nativeQuery = true)
	Review save(Review review); 

	//리뷰 조회
	@Query(value = "Select * from review where rest_id = :id", nativeQuery = true)
	List<Review> selectReviewById(int id);
	
	
}
