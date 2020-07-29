package com.web.curation.controller.review;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.web.curation.dao.review.RestaurantDao;
import com.web.curation.model.BasicResponse;
import com.web.curation.model.review.Restaurant;

import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@ApiResponses(value = { @ApiResponse(code = 401, message = "Unauthorized", response = BasicResponse.class),
		@ApiResponse(code = 403, message = "Forbidden", response = BasicResponse.class),
		@ApiResponse(code = 404, message = "Not Found", response = BasicResponse.class),
		@ApiResponse(code = 500, message = "Failure", response = BasicResponse.class) })

@CrossOrigin(origins = { "*" })
@RestController
public class ReviewController {

	@Autowired
	RestaurantDao restDao;
	
	@GetMapping("/review/add")
	public void addReview(@RequestParam(required = true) final String name,
			@RequestParam(required = true) final String category,
			@RequestParam(required = true) final String writer,
			@RequestParam(required = true) final String content) {
		
		//1. DB의 가게 테이블에 name 상호명을 가진 가게가 있는지 검색
		//2. 없을 경우 가게 테이블에 입력(review = 1), 있을경우 review++
		//3. 리뷰 테이블에 정보 입력
		System.out.println(restDao.seletRestNameByName(name));
		Restaurant rest = restDao.seletRestNameByName(name);
		System.out.println(rest);
		if(rest == null) {
			System.out.println("없다");
			//가게 테이블에 정보 입력
			restDao.insertRestaurant(name, category);
		}
		else {
			System.out.println("있다.");
			restDao.updateRestaurantReview(name);
		}
		System.out.println(name+" "+category +" "+ writer+" "+ content);
	}
}
