package com.web.curation.controller.post;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.web.curation.dao.post.PostCommentDao;
import com.web.curation.model.BasicResponse;
import com.web.curation.model.post.PostComment;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@ApiResponses(value = { @ApiResponse(code = 401, message = "Unauthorized", response = BasicResponse.class),
	      @ApiResponse(code = 403, message = "Forbidden", response = BasicResponse.class),
	      @ApiResponse(code = 404, message = "Not Found", response = BasicResponse.class),
	      @ApiResponse(code = 500, message = "Failure", response = BasicResponse.class) })

	@CrossOrigin(origins = { "*" })
	@RestController
public class PostCommentController {
	
	@Autowired
	PostCommentDao postCommentDao;
	
	
	@GetMapping("/post/comment")
	@ApiOperation(value = "게시글 id로 댓글 모두반환")
	public Map<String, Object> view(final String commentid){
		
		List<PostComment> comment = postCommentDao.selectAllByPostId(Integer.parseInt(commentid));
		
		for(PostComment p : comment) {
			System.out.println(p);
		}
		
		Map<String, Object> data = new HashMap<String, Object>();
		
		data.put("data", comment);
		
		
		return data;
	}
	
	@PostMapping("/post/comment")
	@ApiOperation(value = "댓글 등록")
	public void insert(@RequestBody PostComment postcomment) {
		postCommentDao.insert(postcomment);
	}
	
	@PutMapping("/post/comment")
	@ApiOperation(value = "댓글 수정")
	public void update(@RequestBody PostComment postcomment) {
		postCommentDao.update(postcomment);
	}
	
	@DeleteMapping("/post/comment")
	@ApiOperation(value = "댓글 삭제")
	public void delete(@RequestBody PostComment postcomment) {
		postCommentDao.delete(postcomment);
	}
	
	
	
}
