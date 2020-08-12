package com.web.curation.dao.restaurant;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.web.curation.model.review.Restaurant;
import com.web.curation.model.review.Review;

public interface ReviewDao extends JpaRepository<Review, String>{
	
	//리뷰 등록
	@Query(value = "Insert into review (review_name,review_category,review_writer,review_content)"
			+ "values (:name,:category,:writer,:content)", nativeQuery = true)
	void insertReview(String name, String category,String writer,String content); 

	//리뷰 조회
	@Query(value = "Select * from review where rest_id = :id", nativeQuery = true)
	List<Review> selectReviewById(int id);
	
	
}
