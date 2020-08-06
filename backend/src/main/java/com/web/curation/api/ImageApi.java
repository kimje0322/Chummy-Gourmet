package com.web.curation.api;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.io.IOUtils;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import io.swagger.annotations.ApiOperation;

@Controller
public class ImageApi {

	@GetMapping(value = "/img/post", produces = MediaType.IMAGE_PNG_VALUE)
	public @ResponseBody byte[] getImage(final String imgname) throws IOException {
		System.out.println("입력");
		InputStream in = new FileInputStream("/home/ubuntu/deploy/img/newsfeed/"+imgname);
		System.out.println(in);
		return IOUtils.toByteArray(in);
	}
	
	@GetMapping(value = "/img/user", produces = MediaType.IMAGE_PNG_VALUE)
	@ApiOperation(value = "[유저 이미지 가져오기]")
	public @ResponseBody byte[] getUserImage(final String imgname) throws IOException {
		System.out.println("입력");
		InputStream in = new FileInputStream("/home/ubuntu/deploy/img/user/"+imgname);
//		InputStream in = new FileInputStream("C:/"+imgname);
		System.out.println(in);
		return IOUtils.toByteArray(in);
	}
}
