package com.mr.rc.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import com.mr.rc.entity.SubReddit;

public interface SubRedditRepository extends JpaRepository<SubReddit, Long> {

	SubReddit findByName(String subredditName);

}
