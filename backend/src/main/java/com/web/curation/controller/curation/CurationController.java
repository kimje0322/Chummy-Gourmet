package com.web.curation.controller.curation;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.web.curation.api.KakaoApi;
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
public class CurationController {

	@Autowired RestaurantDao restDao;
	
	//장소를 중심으로 검색
	@GetMapping("/curation")
	public void curation(@RequestParam(required = true) final String location) {
		System.out.println(location);
		KakaoApi test = new KakaoApi();
		List<String> name = test.main(location);
		name.add("맛찬들");
		System.out.println(name);
		List<Restaurant> list = restDao.seletRestNameBySearchName(name);
		System.out.println(list);
	}
}
