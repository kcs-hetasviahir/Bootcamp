package com.kcs.attendancesystem.repository;

import java.util.Date;
import java.util.List;

import com.kcs.attendancesystem.core.TeacherAttendance;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;
import org.springframework.stereotype.Repository;

@Repository
public interface TeacherAttendanceRepository extends JpaRepository<TeacherAttendance, Long> {
	
	@Query("SELECT ta FROM TeacherAttendance ta WHERE ta.teacher.institution.user.id = :userId AND ta.attendanceDate BETWEEN :startDate AND :endDate ORDER BY ta.teacher.firstName, ta.attendanceDate")
	List<TeacherAttendance> findByUserIdAndDate(@Param("userId") Long userId, @Param("startDate") @DateTimeFormat(iso = ISO.DATE) Date startDate, @Param("endDate") @DateTimeFormat(iso = ISO.DATE) Date endDate);
	
	@Query("SELECT ta FROM TeacherAttendance ta WHERE ta.attendanceDate = :attDate")
	List<TeacherAttendance> findByAtDate(@Param("attDate") @DateTimeFormat(iso = ISO.DATE) Date attDate);
	
	List<TeacherAttendance> findByIsProcessed(Boolean isprocessed);
	
	@Query("SELECT ta FROM TeacherAttendance ta WHERE ta.teacher.id IN (:teacherIds) AND ta.attendanceDate BETWEEN :startDate AND :endDate ORDER BY ta.teacher.firstName, ta.attendanceDate")
	List<TeacherAttendance> findByTeacherIdsAndDate(@Param("teacherIds") List<Long> teacherIds, @Param("startDate") @DateTimeFormat(iso = ISO.DATE) Date startDate, @Param("endDate") @DateTimeFormat(iso = ISO.DATE) Date endDate);
	
	@Query("SELECT ta FROM TeacherAttendance ta WHERE DATE(ta.attendanceDate) >= :fromDate AND DATE(ta.attendanceDate) <= :toDate ORDER BY id DESC")
	Page<TeacherAttendance> findByAtDateBetween(@Param("fromDate") @DateTimeFormat(iso = ISO.DATE) Date fromDate, @Param("toDate") @DateTimeFormat(iso = ISO.DATE) Date toDate, Pageable pageable);

	@Query("SELECT ta FROM TeacherAttendance ta WHERE ta.teacher.ssaDistrictCode = :districtId AND ta.attendanceDate BETWEEN :startDate AND :endDate ORDER BY ta.teacher.firstName, ta.attendanceDate")
	Page<TeacherAttendance> findByTeacherSSADistrictAndDate(@Param("districtId") Long districtId, @Param("startDate") @DateTimeFormat(iso = ISO.DATE) Date startDate, @Param("endDate") @DateTimeFormat(iso = ISO.DATE) Date endDate, Pageable pageable);

	@Query("SELECT ta FROM TeacherAttendance ta WHERE ta.teacher.ssaBlockCode = :blockId AND ta.attendanceDate BETWEEN :startDate AND :endDate ORDER BY ta.teacher.firstName, ta.attendanceDate")
	Page<TeacherAttendance> findByTeacherSSABlockAndDate(@Param("blockId") Long blockId, @Param("startDate") @DateTimeFormat(iso = ISO.DATE) Date startDate, @Param("endDate") @DateTimeFormat(iso = ISO.DATE) Date endDate, Pageable pageable);

	@Query("SELECT ta FROM TeacherAttendance ta WHERE ta.teacher.ssaClusterCode = :clusterId AND ta.attendanceDate BETWEEN :startDate AND :endDate ORDER BY ta.teacher.firstName, ta.attendanceDate")
	Page<TeacherAttendance> findByTeacherSSAClusterAndDate(@Param("clusterId") Long clusterId, @Param("startDate") @DateTimeFormat(iso = ISO.DATE) Date startDate, @Param("endDate") @DateTimeFormat(iso = ISO.DATE) Date endDate, Pageable pageable);

	@Query("SELECT ta FROM TeacherAttendance ta WHERE ta.teacher.ssaSchoolCode = :schoolId AND ta.attendanceDate BETWEEN :startDate AND :endDate ORDER BY ta.teacher.firstName, ta.attendanceDate")
	Page<TeacherAttendance> findByTeacherSSASchoolAndDate(@Param("schoolId") Long schoolId, @Param("startDate") @DateTimeFormat(iso = ISO.DATE) Date startDate, @Param("endDate") @DateTimeFormat(iso = ISO.DATE) Date endDate, Pageable pageable);

	@Query("SELECT ta FROM TeacherAttendance ta WHERE ta.teacher.designation = :designation AND ta.attendanceDate BETWEEN :startDate AND :endDate ORDER BY ta.teacher.firstName, ta.attendanceDate")
	Page<TeacherAttendance> findByDesignationAndDate(@Param("designation") String designation, @Param("startDate") @DateTimeFormat(iso = ISO.DATE) Date startDate, @Param("endDate") @DateTimeFormat(iso = ISO.DATE) Date endDate, Pageable pageable);

	@Query("SELECT ta FROM TeacherAttendance ta WHERE ta.teacher.id = :teacherId AND ta.attendanceDate BETWEEN :startDate AND :endDate ORDER BY id DESC")
	Page<TeacherAttendance> findByTeacherIdAndDate(@Param("teacherId") Long teacherId, @Param("startDate") @DateTimeFormat(iso = ISO.DATE) Date startDate, @Param("endDate") @DateTimeFormat(iso = ISO.DATE) Date endDate, Pageable pageable);

	@Query("SELECT ta FROM TeacherAttendance ta WHERE ta.teacher.id IN (SELECT t.id FROM Teacher t where t.teacherId = :teacherId) ORDER BY id DESC")
	Page<TeacherAttendance> findByTeacherId(@Param("teacherId") String teacherId, Pageable pageable);

	@Query("SELECT ta FROM TeacherAttendance ta WHERE ta.teacher.ssaDistrictCode = :districtId ORDER BY id DESC")
	Page<TeacherAttendance> findBySSADistrict(@Param("districtId") Long districtId, Pageable pageable);

	@Query("SELECT ta FROM TeacherAttendance ta WHERE ta.teacher.ssaBlockCode = :blockId ORDER BY id DESC")
	Page<TeacherAttendance> findBySSABlock(@Param("blockId") Long blockId, Pageable pageable);

	@Query("SELECT ta FROM TeacherAttendance ta WHERE ta.teacher.ssaClusterCode = :clusterId ORDER BY id DESC")
	Page<TeacherAttendance> findBySSACluster(@Param("clusterId") Long clusterId, Pageable pageable);

	@Query("SELECT ta FROM TeacherAttendance ta WHERE ta.teacher.ssaSchoolCode = :schoolId ORDER BY id DESC")
	Page<TeacherAttendance> findBySSASchool(@Param("schoolId") Long schoolId, Pageable pageable);

	@Query("SELECT ta FROM TeacherAttendance ta WHERE ta.teacher.designation = :designation ORDER BY id DESC")
	Page<TeacherAttendance> findByDesignation(@Param("designation") String designation, Pageable pageable);

	@Query("SELECT ta FROM TeacherAttendance ta WHERE ta.teacher.id IN (SELECT t.id FROM Teacher t where t.supervisorId = :userId) OR ta.teacher.id IN (select t.id from Teacher t where t.id = :userId)")
	Page<TeacherAttendance> findByUserId(@Param("userId") Long userId, Pageable pageable);

	@Query("SELECT ta FROM TeacherAttendance ta WHERE ta.teacher.id = :teacherId")
	List<TeacherAttendance> findByTeacher(@Param("teacherId") Long teacherId);

	
	@Query("SELECT ta FROM TeacherAttendance ta WHERE DATE(ta.attendanceDate) >= :fromDate AND DATE(ta.attendanceDate) <= :toDate ORDER BY id DESC")
	List<TeacherAttendance> findByAtDateBetween(@Param("fromDate") @DateTimeFormat(iso = ISO.DATE_TIME) Date fromDate, @Param("toDate") @DateTimeFormat(iso = ISO.DATE_TIME) Date toDate);
	
	@Query("SELECT ta FROM TeacherAttendance ta WHERE ta.teacher.ssaBlockCode = :blockId ORDER BY id DESC")
	List<TeacherAttendance> findBySSABlock(@Param("blockId") Long blockId);
	
	@Query("SELECT ta FROM TeacherAttendance ta WHERE ta.teacher.ssaDistrictCode = :districtId ORDER BY id DESC")
	List<TeacherAttendance> findBySSADistrict(@Param("districtId") Long districtId);

	@Query("SELECT ta FROM TeacherAttendance ta WHERE ta.teacher.ssaClusterCode = :clusterId ORDER BY id DESC")
	List<TeacherAttendance> findBySSACluster(@Param("clusterId") Long clusterId);

	@Query("SELECT ta FROM TeacherAttendance ta WHERE ta.teacher.ssaSchoolCode = :schoolId ORDER BY id DESC")
	List<TeacherAttendance> findBySSASchool(@Param("schoolId") Long schoolId);
	
	List<TeacherAttendance> findByAttendanceDateAndTeacherId(@DateTimeFormat(iso = ISO.DATE) Date startDate,Long TeacherId);
	
}
