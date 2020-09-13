package com.mr.rc.service;

import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mr.rc.dao.PostRepository;
import com.mr.rc.dao.SubRedditRepository;
import com.mr.rc.entity.Post;
import com.mr.rc.entity.SubReddit;
import com.mr.rc.model.PostRequest;
import com.mr.rc.model.PostResponse;


@Service
public class PostService {
	
	@Autowired
	SubRedditRepository subRedditRepository;
	
	@Autowired
	PostRepository postRepository;
	
	public Post mapTo(PostRequest postRequest, String userName) {
		
		Post post = new Post();
		post.setCreatedDate(Instant.now());
		post.setDescription(postRequest.getDescription());
		post.setPostName(postRequest.getPostName());
		try {
			post.setSubreddit(subRedditRepository.findByName(postRequest.getSubredditName()).getId());
		}
		catch(NullPointerException ex) {}
		post.setUrl(postRequest.getUrl());
		post.setUser(userName);
		post.setVoteCount(0);
		return post;
	}
	
	public PostResponse mapTo(Post post) {
		PostResponse response = new PostResponse();
//		response.setCommentCount( FIND COMMENT COUNT FROM COMMENT REPO  );
//		response.setDownVote(" FIND VOTE DESCRIPTION FROM VOTE REPO ");
//		response.setUpVote(" FIND VOTE DESCRIPTION FROM VOTE REPO ");
		response.setId(post.getPostId());
		response.setDescription(post.getDescription());		
		response.setPostName(post.getPostName());
		SubReddit subReddit = subRedditRepository.findById(post.getSubreddit()).orElse(null);
		try {
			response.setSubredditName(subReddit.getName());
		}
		catch(NullPointerException ex) {}
		response.setUrl(post.getUrl());
		response.setUserName(post.getUser());
		response.setVoteCount(post.getVoteCount());
		return response;
	}

	public void save(PostRequest postRequest, String userName) {
        Post tempPost = mapTo(postRequest, userName);
        System.out.println("testing post in save method:" +tempPost.getPostId()+tempPost.getPostName());
		postRepository.save(tempPost);
    }
	
	@Transactional(readOnly = true)
    public PostResponse getPost(Long id) {
        Post post = postRepository.findById(id).orElse(null);
        return mapTo(post);   		
	}
	
	@Transactional(readOnly = true)
    public List<PostResponse> getAllPosts() {
        return postRepository.findAll()
                .stream()
                .map(i -> mapTo(i))
                .collect(Collectors.toList());
    }
	
	@Transactional(readOnly = true)
    public List<PostResponse> getPostsBySubreddit(Long subredditId) {
        SubReddit subreddit = subRedditRepository.findById(subredditId).orElse(null);
        List<Post> posts = postRepository.findAllBySubRedditId(subreddit.getId());
        return posts.stream().map(i -> mapTo(i)).collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<PostResponse> getPostsByUsername(String username) {
        
    	return postRepository.findAllByUserName(username)
                .stream().map(i -> mapTo(i)).collect(Collectors.toList());
    }
	
}