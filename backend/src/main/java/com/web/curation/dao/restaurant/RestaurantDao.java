package com.web.curation.dao.restaurant;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.web.curation.model.review.Restaurant;
import com.web.curation.model.review.Review;
import com.web.curation.model.user.User;

public interface RestaurantDao extends JpaRepository<Restaurant, String> {

	// name으로 음식점가 있는지 없는지 검색 - review 등록용도
	@Query(value = "SELECT * from restaurant where rest_name = :name", nativeQuery = true)
	Optional<Restaurant> selectRestNameByName(String name);

	// name으로 음식점가 있는지 없는지 검색 - review 등록용도
	@Query(value = "SELECT * from restaurant where rest_name in (:name)", nativeQuery = true)
	List<Restaurant> seletRestNameBySearchName(List<String> name);
	
	// userid로 scrap 한 음식점 데이터 모두 가져옴
	@Query(value = "SELECT * from restaurant where rest_id in (select rest_id from scrap where user_id= :userid)", nativeQuery = true)
	List<Restaurant> selectAllRestByUserId(String userid);

	//음식점 테이블에 등록
	Restaurant save(Restaurant restaurant);
	
	//음식점 주소로 음식점id를 찾아온다
	@Query(value="select rest_id from restaurant where rest_location=:location",nativeQuery = true)
	int selectRestIdByRestLocation(String location);
	
	// 음식점 ID를 통해 해당 음식점 정보 가져오기
	@Query(value = "SELECT * FROM restaurant WHERE rest_id = :restid", nativeQuery = true)
	Restaurant selectByRestId(String restid);
	
	// userid로 좋아요한 음식점 id 모두 가져옴
	@Query(value = "SELECT rest_id from restaurant_like where user_id= :userid", nativeQuery = true)
	List<Integer> selectRestLikeIdByUserId(String userid);

	// userid로 스크랩한 음식점 id 모두 가져옴
	@Query(value = "SELECT rest_id from scrap where user_id= :userid", nativeQuery = true)
	List<Integer> selectRestScrapIdbyUserId(String userid);

	// 좋아요 입력
	@Query(value = "insert into restaurant_like value(:userid,:restid)", nativeQuery = true)
	void insertRestLike(String userid, String restid);

	// 좋아요 취소
	@Query(value = "delete from restaurant_like where user_id= :userid AND rest_id = :restid", nativeQuery = true)
	void deleteRestLike(String userid, String restid);

	// 좋아요 증가
	@Query(value = "update restaurant set rest_like = rest_like+1 where rest_id = :restid", nativeQuery = true)
	void updateRestLike(String restid);

	// 좋아요 감소
	@Query(value = "update restaurant set rest_like = rest_like-1 where rest_id = :restid", nativeQuery = true)
	void updateRestLikeM(String restid);

	// 스크랩 입력
	@Query(value = "insert into scrap value(:userid,:restid)", nativeQuery = true)
	void insertRestScrap(String userid, String restid);

	// 스크랩 취소
	@Query(value = "delete from scrap where user_id= :userid AND rest_id = :restid", nativeQuery = true)
	void deleteRestScrap(String userid, String restid);

	// 스크랩 증가
	@Query(value = "update restaurant set rest_scrap = rest_scrap+1 where rest_id = :restid", nativeQuery = true)
	void updateRestScrap(String restid);

	// 스크랩 감소
	@Query(value = "update restaurant set rest_scrap = rest_scrap-1 where rest_id = :restid", nativeQuery = true)
	void updateRestScrapM(String restid);

	// 리뷰 증가
	@Query(value = "update restaurant set rest_review = rest_review+1 where rest_id = :restid", nativeQuery = true)
	void updateRestReview(String restid);

	// 감소 증가
	@Query(value = "update restaurant set rest_review = rest_review-1 where rest_id = :restid", nativeQuery = true)
	void updateRestReviewM(String restid);


	
}