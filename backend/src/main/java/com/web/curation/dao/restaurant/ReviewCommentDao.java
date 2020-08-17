package com.web.curation.dao.restaurant;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.web.curation.model.review.ReviewComment;

@Repository
public interface ReviewCommentDao extends JpaRepository<ReviewComment, String> {

	//리뷰 아이디로 그 리뷰에 달려있는 코멘트 모두 조회
	@Query(value="SELECT * FROM review_comment WHERE review_id = :reviewId", nativeQuery = true)
	Optional<List<ReviewComment>> findAllByReviewId(String reviewId);
	
	@Query(value="INSERT INTO review_comment "
			+ "(comment_writer, comment_content, review_id) "
			+ "VALUES "
			+ "(:#{#comment.writer}, :#{#comment.content}, :#{#comment.reviewId})", nativeQuery = true)
	ReviewComment save(ReviewComment comment);
}
