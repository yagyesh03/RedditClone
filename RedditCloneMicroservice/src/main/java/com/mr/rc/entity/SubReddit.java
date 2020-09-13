package com.mr.rc.entity;

import java.time.Instant;
import java.util.ArrayList;

import javax.persistence.*;
import javax.validation.constraints.*;


/**
 * @author Mr.Robot
 *
 */
@Entity
@Table(name = "subReddit", 
		uniqueConstraints = { 
		@UniqueConstraint(columnNames = "id"),
		@UniqueConstraint(columnNames = "name") 
	})
public class SubReddit {

	@Id
	@Column(name="id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
	
	@Column(name="name")
    @NotBlank(message = "Community name is required")
    private String name;
    
    @NotBlank(message = "Description is required")
    private String description;
    
   
    private ArrayList<Long> postIds;
    
    private Instant createdDate;
    
    private long userId;

	public SubReddit(Long id, @NotBlank(message = "Community name is required") String name,
			@NotBlank(message = "Description is required") String description, ArrayList<Long> postIds,
			Instant createdDate, long user) {
		super();
		this.id = id;
		this.name = name;
		this.description = description;
		this.postIds = postIds;
		this.createdDate = createdDate;
		this.userId = user;
	}

	public SubReddit() {
		super();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public ArrayList<Long> getPosts() {
		return postIds;
	}

	public void setPosts(ArrayList<Long> posts) {
		this.postIds = posts;
	}

	public Instant getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Instant createdDate) {
		this.createdDate = createdDate;
	}

	public long getUser() {
		return userId;
	}

	public void setUser(long user) {
		this.userId = user;
	}
	
}