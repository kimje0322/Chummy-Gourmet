package com.web.curation.dao.post;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.web.curation.model.post.Post;
import com.web.curation.model.review.Restaurant;
import com.web.curation.model.user.Following;

public interface PostDao extends JpaRepository<Post, String> {
	// 입력받은 id로 user가 팔로우하는 모든 사람을 가져온다.
	@Query(value = "select user_following from following where user_id = :id", nativeQuery = true)
	List<Integer> selectFollowingByUserId(int id);

	@Query(value = "select post_id,post_userid, post_date, post_content, post_img_url,post_like,post_update_date,user_id,user_nickname,user_img "
			+ "from post a " + "inner join user b on a.post_userid = b.user_id "
			+ "where post_userid in (:list)", nativeQuery = true)
	List<Post> selectAllByUserFollowing(List<Integer> list);

	// post_id를 입력받아서 댓글의 개수를 가져온다.
	@Query(value = "select count(*) from post_comment where post_id = :post_id", nativeQuery = true)
	int selectAllCommentByPostId(String post_id);

	@Query(value = "insert into post (post_userid,post_content,post_date,post_img_url)"
			+ "values(:#{#post.postuserid},:#{#post.postcontent},now(),:#{#post.postimgurl})", nativeQuery = true)
	void insert(Post post);

	@Query(value = "update post set post_content = :#{#post.postcontent}, post_date = now(), post_img_url = :#{#post.postimgurl} "
			+ "where post_id = :#{#post.postid}", nativeQuery = true)
	void update(Post post);

	@Query(value = "delete from post where post_id = :postid", nativeQuery = true)
	void delete(String postid);

	@Query(value = "select post_id,post_userid, post_date, post_content, post_img_url,post_like,post_update_date,user_id,user_nickname,user_img "
			+ "from post a " + "inner join user b on a.post_userid = b.user_id " + "where post_userid = :userid "
			+ "order by post_date desc", nativeQuery = true)
	List<Map<String, Object>> selectAllByUserid(int userid);

	// 포스트id로 포스트 상세 페이지로 이동할수 있는 정보 출력
	@Query(value = "select post_id,post_userid, post_date, post_content, post_img_url,post_like,post_update_date,user_id,user_nickname,user_img "
			+ "from post a " + "inner join user b on a.post_userid = b.user_id "
			+ "where post_id = :postid ", nativeQuery = true)
	Map<String, Object> selectOneByPostId(int postid);

	// 좋아요 입력
	@Query(value = "insert into post_like value(:userid,:postid)", nativeQuery = true)
	void insertPostLike(String userid, String postid);

	// 좋아요 취소
	@Query(value = "delete from post_like where user_id= :userid AND post_id = :postid", nativeQuery = true)
	void deletePostLike(String userid, String postid);

	// 좋아요 증가
	@Query(value = "update post set post_like = post_like+1 where post_id = :postid", nativeQuery = true)
	void updatePostLike(String postid);

	// 좋아요 감소
	@Query(value = "update post set post_like = post_like-1 where post_id = :postid", nativeQuery = true)
	void updatePostLikeM(String postid);

	// userid로 좋아요한 게시글 id 모두 가져옴
	@Query(value = "SELECT post_id from post_like where user_id= :userid", nativeQuery = true)
	List<Integer> selectPostLikeIdByUserId(String userid);

	// 게시글 ID를 통해 해당 게시글 정보 가져오기
	@Query(value = "SELECT post_like FROM post WHERE post_id = :postid", nativeQuery = true)
	int selectByPostId(String postid);

}
