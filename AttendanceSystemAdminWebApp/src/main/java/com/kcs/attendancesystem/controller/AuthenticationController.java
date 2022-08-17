package com.kcs.attendancesystem.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.kcs.attendancesystem.dto.ResponseVO;
import com.kcs.attendancesystem.dto.UserVO;
import com.kcs.attendancesystem.service.AuthenticationService;

@CommonRestController
public class AuthenticationController {

	@Autowired
	private AuthenticationService authenticationService;

	@PostMapping("/login")
	public ResponseVO<Map<String, String>> authenticate(@RequestBody UserVO userVO) {
		return authenticationService.authenticateAndGenerateToken(userVO.getUsername(), userVO.getPassword());
	}
}
