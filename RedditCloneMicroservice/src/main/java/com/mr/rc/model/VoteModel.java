package com.mr.rc.model;

import com.mr.rc.entity.VoteType;

public class VoteModel {
    
	private VoteType voteType;
    
	private Long postId;
	
	private String userName;
	
    public VoteModel(VoteType voteType, Long postId, String userName) {
		super();
		this.voteType = voteType;
		this.postId = postId;
		this.userName = userName;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public VoteType getVoteType() {
		return voteType;
	}
	public void setVoteType(VoteType voteType) {
		this.voteType = voteType;
	}
	public Long getPostId() {
		return postId;
	}
	public void setPostId(Long postId) {
		this.postId = postId;
	}
	
	public VoteModel(VoteType voteType, Long postId) {
		super();
		this.voteType = voteType;
		this.postId = postId;
	}
	public VoteModel() {
		super();
	}
}