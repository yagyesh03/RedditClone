package com.mr.rc.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mr.rc.entity.Comment;

public interface CommentRepository extends JpaRepository<Comment, Long> {

	List<Comment> findByPostId(Long postId);

	List<Comment> findAllByUserName(String userName);
	
}
