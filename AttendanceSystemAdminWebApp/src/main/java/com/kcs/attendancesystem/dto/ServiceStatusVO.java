package com.kcs.attendancesystem.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ServiceStatusVO {

	private String startTime;
	
	private String endTime;
	
	private String status;
	
	private String description;
}
