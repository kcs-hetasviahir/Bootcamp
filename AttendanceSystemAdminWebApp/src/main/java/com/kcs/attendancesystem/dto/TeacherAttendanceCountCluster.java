package com.kcs.attendancesystem.dto;

import lombok.Data;

@Data
public class TeacherAttendanceCountCluster 
{
	private Long districtCode;
	private Long blockCode;
	private String clusterName;
	private Long clusterCode;
	private Long regular;
	private Long notRegular;

}
