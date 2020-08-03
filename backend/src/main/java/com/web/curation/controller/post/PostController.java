package com.web.curation.controller.post;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

import com.web.curation.dao.post.PostDao;
import com.web.curation.model.BasicResponse;
import com.web.curation.model.post.Post;
import com.web.curation.model.user.Following;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@ApiResponses(value = { @ApiResponse(code = 401, message = "Unauthorized", response = BasicResponse.class),
	      @ApiResponse(code = 403, message = "Forbidden", response = BasicResponse.class),
	      @ApiResponse(code = 404, message = "Not Found", response = BasicResponse.class),
	      @ApiResponse(code = 500, message = "Failure", response = BasicResponse.class) })

	@CrossOrigin(origins = { "*" })
	@RestController
public class PostController {
	
	@Autowired
	PostDao postDao;

	@GetMapping("/post")
	@ApiOperation(value = "게시글 반환")
	public Map<String, Object> view(final String userid){
		//자신과 팔로잉하는 유저의 게시글을 보여준다.
		//1. userid를 받아옴
		//select * from following where user_id = 받아온id
		//2. userid를 바탕으로 내 팔로잉 목록을 가져온다
		//select * from post where postuserid in 나와 내가팔로우하는 유저
		//3. userid의 게시글과 userid가 팔로우하는 유저들의 게시글을 가져온다.
		//4. 반환
		
		//한번에 모두 반홨했을경우 너무 많은 데이터를 불러와야 할 수 있음
		//추후 10개씩 가져오는 기능 추가
		List<Integer> list = new ArrayList<Integer>();
		list.add(Integer.parseInt(userid));
		
		List<Integer> following= postDao.selectFollowingByUserId(Integer.parseInt(userid));
		System.out.println(1);
		for(int i = 0 ;i<following.size();i++) {
			list.add(following.get(i));
		}
		
		System.out.println(2);
		List<Post> post = new ArrayList<Post>();
		System.out.println(3);
		post = postDao.selectAllByUserFollowing(list);
		
		System.out.println(4);
		Map<String, Object> data = new HashMap<String, Object>();
		
		data.put("data", post);
		
		
		return data;
	}
	
	@PostMapping("/post")
	@ApiOperation(value = "게시글 등록")
	public int insert() {
		
		return 0;
	}
	
	@PutMapping("/post")
	@ApiOperation(value = "게시글 수정")
	public int update() {
		return 0;
	}
	
	@DeleteMapping("/post")
	@ApiOperation(value = "게시글 삭제")
	public int delete() {
		return 0;
	}
}