package com.web.curation.controller.post;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.web.curation.dao.post.PostDao;
import com.web.curation.model.BasicResponse;
import com.web.curation.model.post.Post;

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

	@PostMapping("/post/img")
	public String test(@RequestParam("file") MultipartFile file) throws Exception {
		System.out.println("파일 이름 : " + file.getOriginalFilename());
	    System.out.println("파일 크기 : " + file.getSize());
	    StringTokenizer st = new StringTokenizer(file.getOriginalFilename(),".");
	    String fileName = st.nextToken();
	    String extension =st.nextToken();
	    System.out.println(fileName);
	    System.out.println(extension);
	    
	    Date today = new Date();
	    SimpleDateFormat date = new SimpleDateFormat("yyyyMMdd");
	    SimpleDateFormat time = new SimpleDateFormat("hhmmss");
	    
	    int r = (int)(Math.random()*1000000);
	     
	    String fileFullName = r+"_"+date.format(today)+time.format(today)+"."+extension;
	    //서버에서 사용할때
	    FileCopyUtils.copy(file.getBytes(), new File("/home/ubuntu/deploy/img/newsfeed/"+fileFullName));
	    //로컬에서 테스트할때
//	    FileCopyUtils.copy(file.getBytes(), new File("C:/"+fileFullName));
	    return fileFullName;
	}
	
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
		for(int i = 0 ;i<following.size();i++) {
			list.add(following.get(i));
		}
		
		List<Post> post = new ArrayList<Post>();
		post = postDao.selectAllByUserFollowing(list);
		
		Map<String, Object> data = new HashMap<String, Object>();
		
		data.put("data", post);
		
		
		return data;
	}
	
	@PostMapping("/post")
	@ApiOperation(value = "게시글 등록")
	public void insert(@Valid @RequestBody Post post) {
		postDao.insert(post);
	}
	
	@PutMapping("/post")
	@ApiOperation(value = "게시글 수정")
	public void update(@RequestBody Post post) {
		postDao.update(post);
	}
	
	@DeleteMapping("/post")
	@ApiOperation(value = "게시글 삭제")
	public void delete(@RequestBody Post post) {
		postDao.delete(post);
	}
	
	
	
}
