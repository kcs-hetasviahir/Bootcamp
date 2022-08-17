package com.kcs.attendancesystem.dto;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReportVO {

	private TeacherVO teacher;
	
	private List<AttendanceVO> attendance;
	
}
