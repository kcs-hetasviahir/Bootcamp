package com.kcs.attendancesystem.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ValidateMobileResponseVO {

	private String otp;
	
	private Long userId;
	
	private String employeeId;
	
	private String registrationId;
	
}
