package com.web.curation.dao.review;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.web.curation.model.review.Restaurant;
import com.web.curation.model.user.User;

public interface RestaurantDao extends JpaRepository<Restaurant, String> {
	
	//name으로 가게가 있는지 없는지 검색 - review 등록용도
	@Query(value = "SELECT * from restaurant where rest_name = :name", nativeQuery = true)
			Optional<Restaurant> selectRestNameByName(String name); 
	
	//가게가 없을경우 가게 등록
	@Query(value = "Insert into restaurant (rest_name,rest_category,rest_review)"
			+ "values (:name,:category,1)", nativeQuery = true)
	void insertRestaurant(String name, String category); 

	//가게가 있을경우 리뷰++
	@Query(value = "UPDATE restaurant set rest_review = rest_review+1 where rest_name = :name", nativeQuery = true)
	void updateRestaurantReview(String name); 
	
	//name으로 가게가 있는지 없는지 검색 - review 등록용도'맛찬들'
		@Query(value = "SELECT * from restaurant where rest_name in (:name)", nativeQuery = true)
				List<Restaurant> seletRestNameBySearchName(List<String> name); 
}