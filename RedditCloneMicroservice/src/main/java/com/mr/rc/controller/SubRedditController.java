package com.mr.rc.controller;



import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.mr.rc.model.SubRedditModel;
import com.mr.rc.service.SubRedditService;


@RestController
@CrossOrigin
@RequestMapping("/subReddit")
public class SubRedditController {

	@Autowired
	SubRedditService subRedditService;
	
	@PostMapping("/addSubReddit")
	public ResponseEntity<SubRedditModel> createSubReddit(@RequestBody SubRedditModel subRedditDto) {
		return new ResponseEntity<>(subRedditService.save(subRedditDto),HttpStatus.OK);	
	}
	
	@GetMapping("/getAllSubReddit")
	public ResponseEntity<List<SubRedditModel>> findAllSubReddit(){
		return new ResponseEntity<>(subRedditService.getAll(),HttpStatus.OK);
	}
	
	@GetMapping("/getSubRedditById/{id}")
	public ResponseEntity<SubRedditModel> findById(@PathVariable Long id){
		return new ResponseEntity<>(subRedditService.findById(id),HttpStatus.OK);
	}
	
}
