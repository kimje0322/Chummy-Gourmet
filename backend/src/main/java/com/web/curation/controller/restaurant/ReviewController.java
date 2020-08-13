package com.web.curation.controller.restaurant;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.web.curation.dao.meetup.MeetUpDao;
import com.web.curation.dao.restaurant.RestaurantDao;
import com.web.curation.dao.restaurant.ReviewCommentDao;
import com.web.curation.dao.restaurant.ReviewDao;
import com.web.curation.dao.user.UserDao;
import com.web.curation.model.BasicResponse;
import com.web.curation.model.meetup.Meetup;
import com.web.curation.model.review.Restaurant;
import com.web.curation.model.review.Review;
import com.web.curation.model.review.ReviewComment;
import com.web.curation.model.user.User;

import io.swagger.annotations.ApiOperation;
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
	@Autowired
	UserDao userDao;
	@Autowired
	ReviewDao reviewDao;
	@Autowired
	ReviewCommentDao reviewCommentDao;
	@Autowired
	MeetUpDao meetUpDao;
	
	//리뷰 추가
	@PutMapping("/review")
	@ApiOperation(value = "리뷰 작성")
	public void addReview(@RequestBody Review review) {
		System.out.println(review);
		//1. DB의 가게 테이블에 name 상호명을 가진 가게가 있는지 검색
		//2. 없을 경우 가게 테이블에 입력(review = 1), 있을경우 review++
		//3. 리뷰 테이블에 정보 입력
		
//		System.out.println(restDao.selectRestNameByName(name));
//		Restaurant rest = restDao.selectRestNameByName(name);
//		Optional<Restaurant> rest = restDao.selectRestNameByName(name);
//		System.out.println(rest);
//		if(rest == null) {
//			System.out.println("없다");
//			//가게 테이블에 정보 입력
//			restDao.insertRestaurant(name, category);
//		}
//		else {
//			System.out.println("있다.");
//			restDao.updateRestaurantReview(name);
//		}
//		
//		reviewDao.insertReview(name, category, writer, content);
//		
//		System.out.println(name+" "+category +" "+ writer+" "+ content);
	}
	
	//리뷰 검색
	@GetMapping("/review/search")
	@ApiOperation(value = "리뷰 검색")
	public Map<String, Object> searchReview(@RequestParam(required = true) final int id) {
		System.out.println(id);
		//1. 음식점 id를 가져온다
		//2. 음식점 id로 리뷰 검색
		//3. 리뷰의 밋업 id로 팀원 닉네임 검색
		
		System.out.println(id);
		List<Review> list = reviewDao.selectReviewById(id);
		
		List<String>[] nickname = new ArrayList[list.size()];
		int index = 0;
		for(Review a : list) {
			nickname[index] = new ArrayList<String>();
			List<User> temp = userDao.selectUserNickNameByMeetUpId(Integer.parseInt(a.getMeetupid()));
			for(User b : temp) {
				nickname[index].add(b.getUserNickname());
			}
			index++;
		}
		
		Map<String, Object> map = new HashMap<String, Object>();
		System.out.println("닉네임 " + nickname);
		map.put("review", list);
		map.put("member", nickname);
		
		
		return map;
	}
	
	//리뷰 상세페이지 검색
	@GetMapping("/review/searchcomment")
	@ApiOperation(value = "리뷰 상세페이지")
	public Map<String, Object> searchReviewComment(@RequestParam(required = true) int id) {
		//1. 리뷰id 입력받음
		//2. 리뷰 아이디로 리뷰 객체 가져오기 // 없어도됨
		//3. 리뷰 아이디로 밋업 객체 가져오기
		//4. 리뷰아이디로 파티원 객체 가져오기 //없어도됨
		//5. 리뷰 아이디로 파티원 코멘트 가져오기
		
		System.out.println("들어왓니?");
		Optional<Meetup> meetUp = meetUpDao.selectMeetUpById(id);
		List<ReviewComment> comment = reviewCommentDao.selectReviewCommentByReviewId(id);
		System.out.println(meetUp.get());
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("meetup", meetUp);
		map.put("comment", comment);
		return map;
	
	}
}
