package com.mr.rc.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mr.rc.dao.*;
import com.mr.rc.entity.*;
import com.mr.rc.exception.PostNotFoundException;
import com.mr.rc.exception.SpringRedditException;
import com.mr.rc.model.*;

import static com.mr.rc.entity.VoteType.UPVOTE;

import java.util.Optional;



@Service
public class VoteService {

	@Autowired
    VoteRepository voteRepository;
	
	@Autowired
    PostRepository postRepository;

    @Transactional
    public void vote(VoteModel voteDto) {
        Post post = new Post();
    	
    	try {
    		post = postRepository.findById(voteDto.getPostId())
    				.orElseThrow(() -> new PostNotFoundException("Post Not Found with ID - " + voteDto.getPostId()));
        }
        catch(PostNotFoundException ex) {
        	System.out.println("PNFE Handled");
        }
    	
    	Optional<Vote> voteByPostAndUser = voteRepository.findTopByPostIdAndUserNameOrderByVoteIdDesc(voteDto.getPostId(), voteDto.getUserName());
        if (voteByPostAndUser.isPresent() &&
                voteByPostAndUser.get().getVoteType()
                        .equals(voteDto.getVoteType())) {
            throw new SpringRedditException("You have already "
                    + voteDto.getVoteType() + "'d for this post");
        }
        
        try {
        	if (UPVOTE.equals(voteDto.getVoteType())) {
                post.setVoteCount(post.getVoteCount() + 1);
            } else {
                post.setVoteCount(post.getVoteCount() - 1);
            }
        }
        catch(NullPointerException ex) {
        	System.out.println("NPE Handled");
        }
        voteRepository.save(mapTo(voteDto));
        postRepository.save(post);
    }
    
    
    public Vote mapTo(VoteModel from) {
    	Vote to = new Vote();
    	
    	to.setPost(from.getPostId());
    	to.setUserName(from.getUserName());
    	to.setVoteType(from.getVoteType());
   
    	return to;
    }
}
