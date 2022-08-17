package com.kcs.attendancesystem.service.impl;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import com.kcs.attendancesystem.config.JwtTokenUtil;
import com.kcs.attendancesystem.dto.ResponseVO;
import com.kcs.attendancesystem.enums.ResponseCode;
import com.kcs.attendancesystem.service.AuthenticationService;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {

	private static final Logger LOGGER = LoggerFactory.getLogger(AuthenticationServiceImpl.class);

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private JwtTokenUtil jwtTokenUtil;

	@Autowired
	private UserDetailsService userDetailsService;

	@Override
	public ResponseVO<Map<String, String>> authenticateAndGenerateToken(String username, String password) {
		try {
			authenticate(username, password);

			UserDetails userDetails = userDetailsService.loadUserByUsername(username);

			String token = jwtTokenUtil.generateToken(userDetails);

			Map<String, String> loginMap = new HashMap<>();
			loginMap.put("token", token);
			loginMap.put("username", username);
			return ResponseVO.create(200, ResponseCode.SUCCESSFUL.getName(), "User logged successfully", loginMap);
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);

			return ResponseVO.create(401, ResponseCode.FAIL.getName(),
					"Invalid username or password.", Collections.emptyMap());
		}
	}

	private void authenticate(String username, String password) throws Exception {
		Objects.requireNonNull(username);
		Objects.requireNonNull(password);

		try {
			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
		} catch (DisabledException e) {
			throw new Exception("USER_DISABLED", e);
		} catch (BadCredentialsException e) {
			throw new Exception("INVALID_CREDENTIALS", e);
		}
	}
}
