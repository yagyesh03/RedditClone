package com.mr.rc.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mr.rc.dao.*;
import com.mr.rc.model.*;
import com.mr.rc.entity.*;

import java.util.ArrayList;
import java.util.List;

import static java.util.stream.Collectors.toList;

import java.time.Instant;

@Service
public class CommentService {
    
    @Autowired
    PostRepository postRepository;

    @Autowired
    CommentRepository commentRepository;

    public CommentModel mapTO(Comment from) {
    	CommentModel to = new CommentModel();
    	
    	to.setCreatedDate(Instant.now());
    	to.setId(from.getId());
    	to.setPost(postRepository.findById(from.getId()).orElse(null));
    	to.setText(from.getText());
    	to.setUserName(from.getUserName());

    	return to;
    }
    
    public Comment mapTO(CommentModel from, String userName) {
    	Comment to = new Comment();
    	
    	to.setCreatedDate(Instant.now());
    	to.setId(from.getId());
    	to.setPost(from.getPost().getPostId());
    	to.setText(from.getText());
    	to.setUserName(userName);
    	
    	return to;
    }
    
    
    public void save(CommentModel commentsModel, String userName) {
        Comment comment = mapTO(commentsModel, userName);
        commentRepository.save(comment);

    }


    public List<CommentModel> getAllCommentsForPost(Long postId) {
        return commentRepository.findByPostId(postId)
                .stream()
                .map(i -> mapTO(i)).collect(toList());
    }


	public List<CommentModel> getAllCommentsForUser(String userName) {
		List<CommentModel> commentModelList = new ArrayList<>();
		for( Comment i: commentRepository.findAllByUserName(userName)) {
			commentModelList.add(mapTO(i));
		}
         
        return commentModelList;
	}

	
}