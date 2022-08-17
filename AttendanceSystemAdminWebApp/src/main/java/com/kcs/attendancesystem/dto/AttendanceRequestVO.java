package com.kcs.attendancesystem.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AttendanceRequestVO {
	
	private Integer pageNo;
	
	private Integer pageSize;
	
	private String fromDate;
	
	private String toDate;

	private String employeeId;

	private String ssaDistrict;

	private String ssaBlock;

	private String ssaCluster;

	private String ssaSchool;

	private String designation;

	private String attendanceStatus;

	private String userId;
}
