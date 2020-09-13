package com.mr.rc.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mr.rc.model.VoteModel;
import com.mr.rc.service.VoteService;

@RestController
@RequestMapping("/votes")
public class VoteController {

	@Autowired
	VoteService voteService;

    @PostMapping("/vote")
    public ResponseEntity<Void> vote(@RequestBody VoteModel voteDto) {
        voteService.vote(voteDto);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}