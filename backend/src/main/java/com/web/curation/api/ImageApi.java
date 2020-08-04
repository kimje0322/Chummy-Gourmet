package com.web.curation.api;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.io.IOUtils;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class ImageApi {

	@GetMapping(value = "/post/img", produces = MediaType.IMAGE_PNG_VALUE)
	public @ResponseBody byte[] getImage(final String imgname) throws IOException {
		System.out.println("입력");
		InputStream in = new FileInputStream("/home/ubuntu/deploy/img/test/"+imgname);
		System.out.println(in);
		return IOUtils.toByteArray(in);
	}
}
