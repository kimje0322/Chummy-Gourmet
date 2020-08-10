package com.web.curation.controller.restaurant;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.web.curation.dao.review.RestaurantDao;
import com.web.curation.model.BasicResponse;
import com.web.curation.model.review.Restaurant;

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

	@PostMapping("/rest")
	@ApiOperation(value ="[식당] 식당테이블에 식당 정보 입력 후 입력된 restid 반환")
	public int addRest(@RequestBody(required = true) Restaurant data) {
		
		restDao.save(data);
		
		int restid = restDao.selectRestIdByRestLocation(data.getLocation());
		return restid;
	}
}
