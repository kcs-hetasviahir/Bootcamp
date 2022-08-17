package com.kcs.attendancesystem.service;

import java.util.List;

import com.kcs.attendancesystem.core.Teacher;
import com.kcs.attendancesystem.dto.ResponseVO;
import com.kcs.attendancesystem.dto.TeacherCountBlock;
import com.kcs.attendancesystem.dto.TeacherCountCluster;
import com.kcs.attendancesystem.dto.TeacherCountDist;
import com.kcs.attendancesystem.dto.TeacherCountSchool;
import com.kcs.attendancesystem.dto.TeacherCountTeacher;
import com.kcs.attendancesystem.dto.TeacherVO;

import org.springframework.data.domain.Page;

public interface TeacherService 
{

	ResponseVO<Page<TeacherVO>> findTeachers(int pageNo, int pageSize);

	ResponseVO<TeacherVO> findTeacher(Long teacherId);

	ResponseVO<List<TeacherVO>> findAll();

	ResponseVO<Teacher> updateTeacher(TeacherVO vo);

	ResponseVO<Void> deleteTeacher(Long teacherId);

	ResponseVO<Long> countAll();

	ResponseVO<List<TeacherVO>> searchTeachers(String teacherName);
	
	
	
	/**
	 * 
	 * @param districtCode
	 * @return list
	 * 			Returns List of teachers according to District
	 */
	ResponseVO<List<TeacherCountBlock>> findByDistrict(Long districtCode);
	/**
	 * 
	 * @param ssaBlockCode
	 * @param blockCode
	 * @return list
	 * 			Returns list of teachers according to Block
	 */
	ResponseVO<List<TeacherCountCluster>> findBySsaBlockCode(Long ssaBlockCode,Long blockCode);
	/**
	 * 
	 * @param distid
	 * @param blockid
	 * @param crcid
	 * @return list
	 * 			Returns list of teachers according to cluster 
	 * 						which is filtered by District,Block and Clustercode
	 */
	ResponseVO<List<TeacherCountSchool>> findByCluster(Long distid,Long blockid,Long crcid) ;
	
	/**
	 * 
	 * @param distid
	 * @param blockid
	 * @param crcid
	 * @param schoolid
	 * @return list
	 * 			Returns list of teachers according to School 
	 * 						Which is filtered by District,Block,Cluster and School
	 */
	ResponseVO<List<TeacherCountTeacher>> findBySsaSchoolCode(Long distid,Long blockid,Long crcid,Long schoolid);
	
	/**
	 * 
	 * @return ResponseVO<List<TeacherCountDist>>
	 * 		Returns all the data without any filtering
	 */
	ResponseVO<List<TeacherCountDist>> allData();
	

}
