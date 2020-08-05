package com.web.curation.dao.post;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.web.curation.model.post.Post;
import com.web.curation.model.post.PostComment;
import com.web.curation.model.review.Restaurant;
import com.web.curation.model.user.Following;

public interface PostCommentDao extends JpaRepository<PostComment, String> {
	//입력받은 게시글id로 모든 댓글을 가져온다
	//댓글내용, 유저닉네임,유저이메일 반환
	@Query(value = "SELECT comment_id,post_id,a.post_comment, b.user_nickname,b.user_img, comment_userid, comment_date " + 
			"FROM post_comment a " + 
			"INNER JOIN user b ON a.comment_userid = b.user_id "
			+ "where post_id = :id", nativeQuery = true)
	List<PostComment> selectAllByPostId(int id);
	
	@Query(value = "insert into post_comment (post_id,post_comment,comment_userid,comment_date)"
			+ "values(:#{#postcomment.postid},:#{#postcomment.postcomment},:#{#postcomment.commentuserid},now())",nativeQuery=true)
	void insert(PostComment postcomment);

	@Query(value = "update post_comment set post_comment = :#{#postcomment.postcomment} "
			+ "where comment_id = :#{#postcomment.commentid}",nativeQuery=true)
	void update(PostComment postcomment);
	
	@Query(value = "delete from post_comment where comment_id = :#{#postcomment.commentid}",nativeQuery=true)
	void delete(PostComment postcomment);
}
