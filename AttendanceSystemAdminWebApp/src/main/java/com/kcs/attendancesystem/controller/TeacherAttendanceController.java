package com.kcs.attendancesystem.controller;

import com.kcs.attendancesystem.dto.ResponseVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.kcs.attendancesystem.dto.AttendanceRequestVO;
import com.kcs.attendancesystem.dto.TeacherAttendanceVO;
import com.kcs.attendancesystem.service.TeacherAttendanceService;

@CommonRestController
public class TeacherAttendanceController {

	@Autowired
	private TeacherAttendanceService teacherAttendanceService;
	
	@PostMapping("/teacher-attendance")
	public Page<TeacherAttendanceVO> teacherAttendanceReport(@RequestBody AttendanceRequestVO reqVO) {
		return teacherAttendanceService.generateTeacherAttendanceReport(reqVO);
	}

	@PostMapping("/teacher-attendance-mis")
	public ResponseVO<Page<TeacherAttendanceVO>> teacherAttendanceReportMis(@RequestBody AttendanceRequestVO reqVO) {
		return teacherAttendanceService.generateTeacherAttendanceReportMis(reqVO);
	}
}
