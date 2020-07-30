package com.web.curation.dao.review;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.web.curation.model.review.Review;
import com.web.curation.model.review.ReviewComment;
import com.web.curation.model.user.User;

public interface ReviewCommentDao extends JpaRepository<ReviewComment, String> {

	//리뷰 아이디로 그 리뷰에 달려있는 코멘트 모두 조회
	@Query(value = "SELECT no, review_id, a.user_id, user_nickname, review_comment " + 
			"FROM review_comment a " + 
			"INNER JOIN user b ON a.user_id = b.user_id "+
			"where review_id = :id"
			,nativeQuery = true)
	List<ReviewComment> selectReviewCommentByReviewId(int id);
}
