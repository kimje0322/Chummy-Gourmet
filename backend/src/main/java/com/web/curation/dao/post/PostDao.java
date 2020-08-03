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
	
	@Query(value = "select * from post where post_userid in (:list)", nativeQuery = true)
	List<Post> selectAllByUserFollowing(List<Integer> list);
}
