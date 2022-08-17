package com.kcs.attendancesystem.dto;

import lombok.Data;

@Data
public class TeacherAttendanceCountBlock 
{
	private Long districtCode;
	private String blockName;
	private Long blockCode;
	private Long regular;
	private Long notRegular;


}
