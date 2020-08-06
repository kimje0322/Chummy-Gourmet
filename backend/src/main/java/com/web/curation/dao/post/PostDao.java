package com.web.curation.dao.post;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.web.curation.model.post.Post;
import com.web.curation.model.review.Restaurant;
import com.web.curation.model.user.Following;

public interface PostDao extends JpaRepository<Post, String> {
	//입력받은 id로 user가 팔로우하는 모든 사람을 가져온다.
	@Query(value = "select user_following from following where user_id = :id", nativeQuery = true)
	List<Integer> selectFollowingByUserId(int id);
	
	@Query(value = "select post_id,post_userid, post_date, post_content, post_img_url,post_like,post_update_date,user_nickname,user_img "
			+ "from post a "
			+ "inner join user b on a.post_userid = b.user_id "
			+ "where post_userid in (:list)", nativeQuery = true)
	List<Post> selectAllByUserFollowing(List<Integer> list);
	
	//post_id를 입력받아서 댓글의 개수를 가져온다.
	@Query(value = "select count(*) from post_comment where post_id = :post_id",nativeQuery=true)
	int selectAllCommentByPostId(String post_id);
	
	@Query(value = "insert into post (post_userid,post_content,post_date,post_img_url)"
			+ "values(:#{#post.postuserid},:#{#post.postcontent},now(),:#{#post.postimgurl})",nativeQuery=true)
	void insert(Post post);

	@Query(value = "update post set post_content = :#{#post.postcontent}, post_date = now() "
			+ "where post_id = :#{#post.postid}",nativeQuery=true)
	void update(Post post);
	
	@Query(value = "delete from post where post_id = :#{#post.postid}",nativeQuery=true)
	void delete(Post post);
}
