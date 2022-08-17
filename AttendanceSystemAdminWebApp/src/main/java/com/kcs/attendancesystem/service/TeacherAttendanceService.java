package com.kcs.attendancesystem.service;

import java.util.List;
import java.util.Set;

import com.kcs.attendancesystem.dto.AttendanceRequestVO;
import com.kcs.attendancesystem.dto.ResponseVO;
import com.kcs.attendancesystem.dto.TeacherAttendanceCount;
import com.kcs.attendancesystem.dto.TeacherAttendanceCountBlock;
import com.kcs.attendancesystem.dto.TeacherAttendanceCountCluster;
import com.kcs.attendancesystem.dto.TeacherAttendanceCountSchool;
import com.kcs.attendancesystem.dto.TeacherAttendanceVO;
import com.kcs.attendancesystem.dto.TeacherCountBlock;
import com.kcs.attendancesystem.dto.TeacherCountCluster;
import com.kcs.attendancesystem.dto.TeacherCountSchool;
import com.kcs.attendancesystem.dto.TeacherCountTeacher;
import com.kcs.attendancesystem.dto.TeacherCountVO;

import org.springframework.data.domain.Page;

public interface TeacherAttendanceService {

	/**
	 * 
	 * @param pageRequestVO
	 * @return ResponseVO<Page<TeacherAttendanceVO>> -Generates a MIS report
	 */
	ResponseVO<Page<TeacherAttendanceVO>> generateTeacherAttendanceReportMis(AttendanceRequestVO pageRequestVO);

	Page<TeacherAttendanceVO> generateTeacherAttendanceReport(AttendanceRequestVO pageRequestVO);

	ResponseVO<List<TeacherAttendanceCount>> TeacherCountByAttendance();

	ResponseVO<TeacherCountVO> TeacherRegularCount();

	ResponseVO<List<TeacherAttendanceCountBlock>> findByDistrict(Long districtCode);

	ResponseVO<List<TeacherAttendanceCountCluster>> findBySsaBlockCode(Long ssaBlockCode, Long blockCode);

	ResponseVO<List<TeacherAttendanceCountSchool>> findByCluster(Long distid,Long blockid,Long crcid) ;

	//ResponseVO<List<TeacherAttendanceCountTeacher>> findBySsaSchoolCode(Long distid, Long blockid, Long crcid, Long schoolid);

}
