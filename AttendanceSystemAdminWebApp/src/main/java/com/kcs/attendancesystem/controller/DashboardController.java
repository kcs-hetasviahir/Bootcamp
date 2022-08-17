package com.kcs.attendancesystem.controller;

import java.util.List;
import java.util.Set;

import com.kcs.attendancesystem.dto.ResponseVO;
import com.kcs.attendancesystem.dto.TeacherAttendanceCount;
import com.kcs.attendancesystem.dto.TeacherAttendanceVO;
import com.kcs.attendancesystem.dto.TeacherCountBlock;
import com.kcs.attendancesystem.dto.TeacherCountCluster;
import com.kcs.attendancesystem.dto.TeacherCountDist;
import com.kcs.attendancesystem.dto.TeacherCountSchool;
import com.kcs.attendancesystem.dto.TeacherCountTeacher;
import com.kcs.attendancesystem.dto.TeacherCountVO;
import com.kcs.attendancesystem.service.TeacherAttendanceService;
import com.kcs.attendancesystem.service.TeacherService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author Tarun.Jadav Controller for dashboard
 */

@CommonRestController
public class DashboardController {

	@Autowired
	private TeacherService teacherService;

	@Autowired
	private TeacherAttendanceService teacherAttendanceService;

	/**
	 * 
	 * @return list Returns list of All teachers
	 */
	@PostMapping("/dashboard")
	public ResponseVO<List<TeacherCountDist>> allData() {

		return teacherService.allData();

	}

	/**
	 * @param district
	 * @return list returns list of teachers according to District
	 * 
	 * 
	 */
	@PostMapping("/district")
	public ResponseVO<List<TeacherCountBlock>> findTeacherByDistrict(@RequestParam Long district) {

		return teacherService.findByDistrict(district);
	}

	/**
	 * 
	 * @param district
	 * @param blockCode
	 * @return list returns list of teachers in block according to Block and
	 *         District
	 */
	@PostMapping("/block")
	public ResponseVO<List<TeacherCountCluster>> findTeacherByBlock(@RequestParam Long district,
			@RequestParam Long blockCode) {

		return teacherService.findBySsaBlockCode(district, blockCode);
	}

	/**
	 * 
	 * @param distid
	 * @param blockid
	 * @param crcid
	 * @return list returns list of teachers in Cluster according to district,block
	 *         and cluster
	 */
	@PostMapping("/cluster")
	public ResponseVO<List<TeacherCountSchool>> findTeacherByCluster(@RequestParam Long distid,
			@RequestParam Long blockid, @RequestParam Long crcid) {

		return teacherService.findByCluster(distid, blockid, crcid);
	}

	/**
	 * 
	 * @param distid
	 * @param blockid
	 * @param crcid
	 * @param schoolcode
	 * @return list returns list of teachers in School according to
	 *         district,block,cluster and school
	 */
	@PostMapping("/school")
	public ResponseVO<List<TeacherCountTeacher>> findTeacherBySchool(@RequestParam Long distid,
			@RequestParam Long blockid, @RequestParam Long crcid, @RequestParam Long schoolcode) {

		return teacherService.findBySsaSchoolCode(distid, blockid, crcid, schoolcode);
	}

	@PostMapping("/teacherAttendance")
	public ResponseVO<List<TeacherAttendanceCount>> findTeacherByAttendance() {
		return teacherAttendanceService.TeacherCountByAttendance();
	}

	@PostMapping("/teacherRegular")
	public ResponseVO<TeacherCountVO> findTeacherByRegular() {
		return teacherAttendanceService.TeacherRegularCount();
	}

}
