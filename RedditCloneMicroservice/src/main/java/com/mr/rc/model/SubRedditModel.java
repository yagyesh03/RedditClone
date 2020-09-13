package com.mr.rc.model;

import java.time.Instant;
import java.util.ArrayList;

import com.mr.rc.entity.Post;

public class SubRedditModel {

	private Long id;
	private String subRedditName;
	private String description;
	private int numberOfPosts;
	private ArrayList<Post> posts;
	private Instant createDate;
	private Long userId;
	
	public SubRedditModel(String subRedditName, String description, int numberOfPosts) {
		super();
		this.subRedditName = subRedditName;
		this.description = description;
		this.numberOfPosts = numberOfPosts;
	}
	
	public SubRedditModel(Long id, String subRedditName, String description, int numberOfPosts) {
		super();
		this.id = id;
		this.subRedditName = subRedditName;
		this.description = description;
		this.numberOfPosts = numberOfPosts;
	}

	public SubRedditModel() {
		super();
	}

	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getSubRedditName() {
		return subRedditName;
	}
	public void setSubRedditName(String subRedditName) {
		this.subRedditName = subRedditName;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public int getNumberOfPosts() {
		return numberOfPosts;
	}
	public void setNumberOfPosts(int numberOfPosts) {
		this.numberOfPosts = numberOfPosts;
	}

	public ArrayList<Post> getPosts() {
		return posts;
	}

	public void setPosts(ArrayList<Post> posts) {
		this.posts = posts;
	}

	public Instant getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Instant createDate) {
		this.createDate = createDate;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}
	
}