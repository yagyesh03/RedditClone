package com.mr.rc.service;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mr.rc.dao.PostRepository;
import com.mr.rc.dao.SubRedditRepository;
import com.mr.rc.entity.Post;
import com.mr.rc.entity.SubReddit;
import com.mr.rc.model.SubRedditModel;

@Service
public class SubRedditService {

	@Autowired
	SubRedditRepository subRedditRepo;
	
	@Autowired
	PostRepository postRepo;
		
	@Transactional
	public SubRedditModel save(SubRedditModel subRedditDto) {
		
		SubReddit save = subRedditRepo.save(mapTo(subRedditDto));
		subRedditDto.setId(save.getId());
		return subRedditDto;
	}
	
	public SubReddit mapTo(SubRedditModel from) {
		
		SubReddit to = new SubReddit();
		to.setDescription(from.getDescription());
		to.setName(from.getSubRedditName());
		ArrayList<Long> posts = new ArrayList<>();
		
		try {

			for( Post i: from.getPosts()) {
				posts.add(i.getPostId());
			}
			
		}
		catch(NullPointerException ex) {}
		to.setPosts(posts);
		to.setUser(from.getUserId());
		to.setCreatedDate(Instant.now());
		return to;	
	}

	public SubRedditModel mapTo(SubReddit from) {
		
		SubRedditModel to = new SubRedditModel();
		to.setId(from.getId());
		try {
			to.setNumberOfPosts(from.getPosts().size());
		}
		catch(NullPointerException ex) {
			System.out.println("NullPointerError Handled");
		}
		to.setDescription(from.getDescription());
		to.setSubRedditName(from.getName());
		ArrayList<Post> posts = new ArrayList<>();
		try {
			for(Long i: from.getPosts()) {
				posts.add(postRepo.findById(i).orElse(null));
			}
		}
		catch(NullPointerException ex) {
			System.out.println("NullPointerError Handled");
		}
		to.setPosts(posts);
		to.setCreateDate(from.getCreatedDate());
		to.setUserId(from.getUser());
		return to;	
	}
	
	@Transactional(readOnly = true)
	public List<SubRedditModel> getAll(){
		List<SubRedditModel> subRedditModelList = new ArrayList<>();
		for(SubReddit i: subRedditRepo.findAll()) {
			subRedditModelList.add(mapTo(i));
		}
		return subRedditModelList;
	}

	public SubRedditModel findById(Long id) {
		return mapTo(subRedditRepo.findById(id).orElse(null));
	}
}