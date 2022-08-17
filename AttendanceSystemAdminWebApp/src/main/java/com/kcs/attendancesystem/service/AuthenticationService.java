package com.kcs.attendancesystem.service;

import java.util.Map;

import com.kcs.attendancesystem.dto.ResponseVO;

public interface AuthenticationService {

	ResponseVO<Map<String,String>> authenticateAndGenerateToken(String username, String password);

}
