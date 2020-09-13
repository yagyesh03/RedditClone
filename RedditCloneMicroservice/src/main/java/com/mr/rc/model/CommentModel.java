package com.mr.rc.model;

import java.time.Instant;

import com.mr.rc.entity.Post;

public class CommentModel {

	public CommentModel(Long id, Post post,  String text) {
		super();
		this.id = id;
		this.post = post;
		this.text = text;
	}
    
	public CommentModel() {
		super();
	}

	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Post getPost() {
		return post;
	}
	public void setPost(Post post) {
		this.post = post;
	}
	public Instant getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(Instant createdDate) {
		this.createdDate = createdDate;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	
	private Long id;
	private Post post;
    private Instant createdDate;
    private String text;
    private String userName;
	
}
