package com.mr.rc.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import com.mr.rc.entity.Post;

public interface PostRepository extends JpaRepository<Post, Long> {

	List<Post> findAllBySubRedditId(Long id);
	List<Post> findAllByUserName(String userName);

}
