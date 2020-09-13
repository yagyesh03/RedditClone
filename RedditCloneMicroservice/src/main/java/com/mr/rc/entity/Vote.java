package com.mr.rc.entity;
import javax.persistence.*;


@Entity
public class Vote {
    
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
	private Long voteId;
    
	private VoteType voteType;
    
    private Long postId;

    private String userName;

	public Long getVoteId() {
		return voteId;
	}

	public void setVoteId(Long voteId) {
		this.voteId = voteId;
	}

	public VoteType getVoteType() {
		return voteType;
	}

	public void setVoteType(VoteType voteType) {
		this.voteType = voteType;
	}

	public Long getPost() {
		return postId;
	}

	public void setPost(Long post) {
		this.postId = post;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public Vote(Long voteId, VoteType voteType, Long post, String userName) {
		super();
		this.voteId = voteId;
		this.voteType = voteType;
		this.postId = post;
		this.userName = userName;
	}

	public Vote() {
		super();
	}
    
    
}