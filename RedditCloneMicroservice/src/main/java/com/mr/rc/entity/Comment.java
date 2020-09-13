package com.mr.rc.entity;


import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.time.Instant;



@Entity
@Table(name="reddit_comments")
public class Comment {
    
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    
    @NotEmpty
    private String text;
    
    private Long postId;

    private Instant createdDate;

    private String userName;

	public Comment(Long id, @NotEmpty String text, Long postId, Instant createdDate, String userName) {
		super();
		this.id = id;
		this.text = text;
		this.postId = postId;
		this.createdDate = createdDate;
		this.userName = userName;
	}

	public Comment() {
		super();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public Long getPost() {
		return postId;
	}

	public void setPost(Long post) {
		this.postId = post;
	}

	public Instant getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Instant createdDate) {
		this.createdDate = createdDate;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}
    
    
}