package com.kcs.attendancesystem.dto;

import lombok.Data;

@Data
public class TeacherCountBlock {

	private Long districtCode;
	private String blockName;
	private Long teacherCount;
	private Long blockCode;
}
