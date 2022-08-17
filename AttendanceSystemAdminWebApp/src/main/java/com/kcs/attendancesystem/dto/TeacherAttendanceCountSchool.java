package com.kcs.attendancesystem.dto;

import lombok.Data;

@Data
public class TeacherAttendanceCountSchool {
	private String schoolName;
	private Long regular;
	private Long notRegular;
	private Long schoolCode;

}
