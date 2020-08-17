package com.web.curation.controller.restaurant;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.web.curation.dao.restaurant.RestaurantDao;
import com.web.curation.dao.restaurant.ReviewDao;
import com.web.curation.model.BasicResponse;
import com.web.curation.model.meetup.Meetup;
import com.web.curation.model.review.Restaurant;
import com.web.curation.model.review.Review;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@ApiResponses(value = { @ApiResponse(code = 401, message = "Unauthorized", response = BasicResponse.class),
		@ApiResponse(code = 403, message = "Forbidden", response = BasicResponse.class),
		@ApiResponse(code = 404, message = "Not Found", response = BasicResponse.class),
		@ApiResponse(code = 500, message = "Failure", response = BasicResponse.class) })

@CrossOrigin(origins = { "*" })
@RestController
public class RestaurantController {

	@Autowired
	RestaurantDao restDao;
	
	@Autowired
	ReviewDao reviewDao;

	@PostMapping("/rest")
	@ApiOperation(value = "[음식점] 음식점테이블에 음식점 정보 입력 후 입력된 restid 반환")
	public int addRest(@RequestBody(required = true) Restaurant data) {
		System.out.println(data);
		restDao.save(data);
		int restid = restDao.selectRestIdByRestLocation(data.getLocation());
		return restid;
	}

	@GetMapping("/rest/{restid}")
	@ApiOperation(value = "[음식점] restid 를 통해 해당 음식점 정보 가져오기")
	public Restaurant getRest(@PathVariable String restid) {
		return restDao.selectByRestId(restid);
	}

	@GetMapping("/rest/like/{userid}")
	@ApiOperation(value = "[음식점] userid 에 해당하는 유저가 좋아요한 음식점 restid 가져오기")
	public List<Integer> getLikes(@PathVariable String userid) {
		List<Integer> list = new ArrayList<Integer>();
		list = restDao.selectRestLikeIdByUserId(userid);
		return list;
	}

	@PutMapping("/rest/like")
	@ApiOperation(value = "[음식점] 음식점 좋아요 한 뒤 해당 음식점의 좋아요 갯수 리턴")
	public String insertRestLike(@RequestParam(required = true) final String userid,
			@RequestParam(required = true) final String restid) {

		//////////////////////////////
		//
		// 이미 좋아요 했으면 못하게 하는 유효성 검사
		//
		//////////////////////////////

		restDao.insertRestLike(userid, restid);
		restDao.updateRestLike(restid);
		String restLikeCount = restDao.selectByRestId(restid).getLike();
		return restLikeCount;
	}

	@DeleteMapping("/rest/like")
	@ApiOperation(value = "[음식점] 음식점 좋아요 취소한 뒤 해당 음식점의 좋아요 갯수 리턴")
	public String deleteRestLike(@RequestParam(required = true) final String userid,
			@RequestParam(required = true) final String restid) {
		restDao.deleteRestLike(userid, restid);
		restDao.updateRestLikeM(restid);
		String restLikeCount = restDao.selectByRestId(restid).getLike();
		return restLikeCount;
	}

	@GetMapping("/rest/scrap/{userid}")
	@ApiOperation(value = "[음식점] userid 에 해당하는 유저가 스크랩한 음식점 restid 가져오기")
	public List<Integer> getScrapId(@PathVariable String userid) {
		List<Integer> list = new ArrayList<Integer>();
		list = restDao.selectRestScrapIdbyUserId(userid);
		return list;
	}

	@PutMapping("/rest/scrap")
	@ApiOperation(value = "[음식점] 음식점 스크랩 한 뒤 해당 음식점의 스크랩 갯수 리턴")
	public String insertScrap(@RequestParam(required = true) final String userid,
			@RequestParam(required = true) final String restid) {
		restDao.insertRestScrap(userid, restid);
		restDao.updateRestScrap(restid);
		String restScrapCount = restDao.selectByRestId(restid).getScrap();
		return restScrapCount;
	}

	@DeleteMapping("/rest/scrap")
	@ApiOperation(value = "[음식점] 음식점 스크랩 취소한 뒤 해당 음식점의 스크랩 갯수 리턴")
	public String deleteScrap(@RequestParam(required = true) final String userid,
			@RequestParam(required = true) final String restid) {
		restDao.deleteRestScrap(userid, restid);
		restDao.updateRestScrapM(restid);
		String restScrapCount = restDao.selectByRestId(restid).getScrap();
		return restScrapCount;
	}
	
	@GetMapping("/rest/review/{restId}")
	@ApiOperation(value = "[음식점] restId에 해당하는 음식점의 리뷰 리스트 가져오기")
	public Object getReviews(@PathVariable int restId) {
		System.out.println(restId);
		final BasicResponse result = new BasicResponse();
		Optional<List<Review>> reviews = reviewDao.findAllById(restId);
		// 리뷰가 있다면
		if(reviews.isPresent()) {
			result.status = true;
			result.data = "success";
			result.object = reviews;
		}
		// 데이터가 있다면
		else {
			result.status = true;
			result.data = "NO DATA";
		}
		return new ResponseEntity<>(result, HttpStatus.OK);
	}
	
	@PutMapping("/rest/review/{restId}")
	@ApiOperation(value = "[음식점] 음식점 리뷰갯수 +1 뒤 해당 음식점의 리뷰 갯수 리턴")
	public String updateReview(@PathVariable String restId) {
		restDao.updateRestReview(restId);
		String restReviewCount = restDao.selectByRestId(restId).getReview();
		return restReviewCount;
	}
	
	@DeleteMapping("/rest/review/{restId}")
	@ApiOperation(value = "[음식점] 음식점 리뷰갯수 -1 뒤 해당 음식점의 리뷰 갯수 리턴")
	public String updateReviewM(@PathVariable String restId) {
		restDao.updateRestReviewM(restId);
		String restReviewCount = restDao.selectByRestId(restId).getReview();
		return restReviewCount;
	}
}
