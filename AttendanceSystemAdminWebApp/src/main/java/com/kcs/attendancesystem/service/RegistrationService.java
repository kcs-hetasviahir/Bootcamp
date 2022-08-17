package com.kcs.attendancesystem.service;

import com.kcs.attendancesystem.dto.AttendanceVO;
import com.kcs.attendancesystem.dto.ResponseVO;
import com.kcs.attendancesystem.dto.TeacherVO;

public interface RegistrationService {

	ResponseVO<String> addRegistration(TeacherVO teacherVO);

	ResponseVO<String> addAttendance(AttendanceVO attendanceVO);
}
