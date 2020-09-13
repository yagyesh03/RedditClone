package com.mr.rc.dao;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import com.mr.rc.entity.Vote;

public interface VoteRepository extends JpaRepository<Vote, Long> {
    Optional<Vote> findTopByPostIdAndUserNameOrderByVoteIdDesc(Long postId, String userName);
}