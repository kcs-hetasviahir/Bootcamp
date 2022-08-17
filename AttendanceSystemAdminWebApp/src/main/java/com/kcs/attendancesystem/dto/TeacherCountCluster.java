package com.kcs.attendancesystem.dto;

import lombok.Data;

@Data
public class TeacherCountCluster {

	private Long districtCode;
	private Long blockCode;
	private String clusterName;
	private Long teacherCount;
	private Long clusterCode;
}
