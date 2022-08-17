package com.kcs.attendancesystem.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;
import org.springframework.stereotype.Repository;

import com.kcs.attendancesystem.core.District;
import com.kcs.attendancesystem.core.Teacher;

@Repository
public interface TeacherRepository extends JpaRepository<Teacher, Long> {

	List<Teacher> findByIsProcessed(Boolean isProcessed);

	@Query("SELECT t FROM Teacher t WHERE t.id IN (SELECT ta.teacher.id FROM TeacherAttendance ta WHERE ta.teacher.institution.user.id = :userId AND ta.attendanceDate BETWEEN :startDate AND :endDate)")
	Page<Teacher> findByUserIdAndDate(@Param("userId") Long userId,
			@Param("startDate") @DateTimeFormat(iso = ISO.DATE) Date startDate,
			@Param("endDate") @DateTimeFormat(iso = ISO.DATE) Date endDate, Pageable pageable);

	@Query("SELECT t FROM Teacher t WHERE t.id = :id")
	Teacher findByUserId(@Param("id") Long id);

	@Query("SELECT t FROM Teacher t WHERE t.mobileNumber = :mobileNumber")
	Teacher findByMobileNumber(@Param("mobileNumber") String mobileNumber);

	@Query("SELECT t FROM Teacher t WHERE t.teacherId = :teacherId")
	Teacher findByTeacherId(@Param("teacherId") String teacherId);

	@Query("SELECT t FROM Teacher t WHERE t.emailAddress = :emailId")
	Teacher findByEmailId(@Param("emailId") String emailId);

	@Query("SELECT t FROM Teacher t WHERE t.firstName LIKE :teacherName OR t.lastName LIKE :teacherName")
	List<Teacher> searchTeacherByName(@Param("teacherName") String teacherName);

	List<Teacher> findBySsaDistrictCode(Long districtCode);

	List<Teacher> findBySsaBlockCode(Long ssaBlockCode);

	List<Teacher> findBySsaClusterCode(Long ssaClusterCode);

	List<Teacher> findBySsaSchoolCode(Long ssaSchoolCode);

	List<Teacher> findAllBySsaDistrictCode(Long ssaDistrictCode);

	List<Teacher> findAllBySsaBlockCode(Long ssaBlockCode);

	List<Teacher> findAllBySsaClusterCode(Long ssaClusterCode);

	List<Teacher> findAllBySsaSchoolCode(Long ssaSchoolCode);

	Long countBySsaDistrictCode(Long ssaDistrictCode);

	Long countBySsaBlockCode(Long ssaBlockCode);

	Long countBySsaClusterCode(Long ssaClusterCode);

	Long countBySsaSchoolCode(Long ssaSchoolCode);
}
