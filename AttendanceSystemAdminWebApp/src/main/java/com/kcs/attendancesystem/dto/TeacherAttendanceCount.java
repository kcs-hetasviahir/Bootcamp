package com.kcs.attendancesystem.dto;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

import lombok.Data;

@Data
public class TeacherAttendanceCount {

	private Long TeacherId;
	private String teacherName;
	private Long count;

	private LocalDate date;

	private List<String> time;

}
