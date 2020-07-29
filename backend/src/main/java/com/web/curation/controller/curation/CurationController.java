package com.web.curation.controller.curation;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.web.curation.api.KakaoApi;
import com.web.curation.model.BasicResponse;

import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@ApiResponses(value = { @ApiResponse(code = 401, message = "Unauthorized", response = BasicResponse.class),
	      @ApiResponse(code = 403, message = "Forbidden", response = BasicResponse.class),
	      @ApiResponse(code = 404, message = "Not Found", response = BasicResponse.class),
	      @ApiResponse(code = 500, message = "Failure", response = BasicResponse.class) })

	@CrossOrigin(origins = { "*" })
	@RestController
public class CurationController {

	
	//장소를 중심으로 검색
	@GetMapping("/curation")
	public void curation(@RequestParam(required = true) final String location) {
		System.out.println(location);
		KakaoApi test = new KakaoApi();
		test.main(location);
	}
}
