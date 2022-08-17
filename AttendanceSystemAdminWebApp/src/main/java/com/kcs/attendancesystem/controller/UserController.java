package com.kcs.attendancesystem.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import com.kcs.attendancesystem.dto.ResponseVO;
import com.kcs.attendancesystem.dto.UserVO;
import com.kcs.attendancesystem.service.UserService;

@BaseRestController
public class UserController {

	@Autowired
	private UserService userService;
	
	@GetMapping("/users")
	public ResponseVO<List<UserVO>> findAll() {
		return userService.findAll();
	}

	@GetMapping("/users/findAll")
	public ResponseVO<Page<UserVO>> findUsers(@RequestParam("pageNo") int pageNo, @RequestParam("pageSize") int pageSize) {
		return userService.findUsers(pageNo, pageSize);
	}
	
	@GetMapping("/user/{id}")
	public ResponseVO<UserVO> findById(@PathVariable("id") Long id) {
		return userService.findById(id);
	}	
	
	@PostMapping("/user")
	public ResponseVO<UserVO> save(@RequestBody UserVO vo) {
		return userService.save(vo); 
	}
	
	@PutMapping("/user")
	public ResponseVO<UserVO> update(@RequestBody UserVO vo) {
		return userService.update(vo); 
	}
	
	@DeleteMapping("/user/{id}")
	public ResponseVO<Void> delete(@PathVariable("id") Long id) {
		return userService.delete(id); 
	}
}
