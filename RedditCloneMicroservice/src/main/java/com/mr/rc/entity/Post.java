package com.mr.rc.entity;

import java.time.Instant;

import javax.persistence.*;
import javax.validation.constraints.*;

import org.springframework.lang.Nullable;


@Entity
public class Post {
    
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long postId;
    
	@NotBlank(message = "Post Name cannot be empty or Null")
    private String postName;
    
	@Nullable
    private String url;
    
	@Nullable
    private String description;
    
	private Integer voteCount = 0;
    
    private String userName;
    
    private Instant createdDate;
    
    private Long subRedditId;

	public Long getPostId() {
		return postId;
	}

	public void setPostId(Long postId) {
		this.postId = postId;
	}

	public String getPostName() {
		return postName;
	}

	public void setPostName(String postName) {
		this.postName = postName;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Integer getVoteCount() {
		return voteCount;
	}

	public void setVoteCount(Integer voteCount) {
		this.voteCount = voteCount;
	}

	public String getUser() {
		return userName;
	}

	public void setUser(String user) {
		this.userName = user;
	}

	public Instant getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Instant createdDate) {
		this.createdDate = createdDate;
	}

	public Long getSubreddit() {
		return subRedditId;
	}

	public void setSubreddit(Long subRedditId) {
		this.subRedditId = subRedditId;
	}

	public Post(@NotBlank(message = "Post Name cannot be empty or Null") String postName, String url,
			String description, Integer voteCount, String userId, Instant createdDate, Long subRedditId) {
		this.postName = postName;
		this.url = url;
		this.description = description;
		this.voteCount = voteCount;
		this.userName = userId;
		this.createdDate = createdDate;
		this.subRedditId = subRedditId;
	}

	public Post() {
		super();
	}
    
    
}
