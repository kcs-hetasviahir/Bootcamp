package com.kcs.attendancesystem.service.impl;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.kcs.attendancesystem.core.Teacher;
import com.kcs.attendancesystem.core.TeacherAttendance;
import com.kcs.attendancesystem.dto.AttendanceVO;
import com.kcs.attendancesystem.dto.JwtUser;
import com.kcs.attendancesystem.dto.MisReportVO;
import com.kcs.attendancesystem.dto.ReportRequestVO;
import com.kcs.attendancesystem.dto.ReportVO;
import com.kcs.attendancesystem.dto.ResponseVO;
import com.kcs.attendancesystem.dto.TeacherVO;
import com.kcs.attendancesystem.enums.ResponseCode;
import com.kcs.attendancesystem.repository.TeacherAttendanceRepository;
import com.kcs.attendancesystem.repository.TeacherRepository;
import com.kcs.attendancesystem.service.MisReportService;
import com.kcs.attendancesystem.utils.Utils;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class MisReportServiceImpl implements MisReportService {

	@Autowired
	private TeacherAttendanceRepository teacherAttendanceRepository;
	
	@Autowired
	private TeacherRepository teacherRepository;
	
	@Override
	public ResponseVO<MisReportVO> generateReport1(ReportRequestVO reqVO) {
		List<ReportVO> reportVOs = new ArrayList<ReportVO>();
		MisReportVO misReportVO = new MisReportVO();

		try {
			LocalDate currentDate = LocalDate.now();
			
			LocalDate date = LocalDate.now().withMonth(reqVO.getMonth()).withYear(reqVO.getYear());

			LocalDate startDate = date.withDayOfMonth(1);
			LocalDate endDate = date.withDayOfMonth(date.lengthOfMonth()); 
			
			if (currentDate.getMonthValue() == reqVO.getMonth()) {
				endDate = currentDate;
			} 			

			Date stDt = Date.from(startDate.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
			Date edDt = Date.from(endDate.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());

			JwtUser jwtUser = Utils.getLoggedUserFromToken();
						
			Page<Teacher> teacherPage = teacherRepository.findByUserIdAndDate(jwtUser!=null ? jwtUser.getId() : 1,
					stDt, edDt, PageRequest.of(reqVO.getPageNo(), reqVO.getPageSize()));
			
			misReportVO.setPageNo(teacherPage.getPageable().getPageNumber());
			misReportVO.setPageSize(teacherPage.getPageable().getPageSize());
			misReportVO.setTotalElements(teacherPage.getTotalElements());
			
			List<Long> teacherIdList = teacherPage.stream().map(Teacher::getId).collect(Collectors.toList());
			List<TeacherAttendance> teacherAttendanceList = new ArrayList<TeacherAttendance>();
			
			if (!teacherIdList.isEmpty()) {
				teacherAttendanceList = teacherAttendanceRepository.findByTeacherIdsAndDate(teacherIdList,
						stDt, edDt);
			}

			Map<Teacher, List<TeacherAttendance>> teacherAttendanceMap = teacherAttendanceList.stream()
					.collect(Collectors.groupingBy(TeacherAttendance::getTeacher));

			for (Entry<Teacher, List<TeacherAttendance>> entry : teacherAttendanceMap.entrySet()) {
				TeacherVO teacherVO = convertToVO(entry.getKey());
				
				List<AttendanceVO> attendanceVOs = generateAttendanceReport(startDate, endDate, entry.getValue());

				ReportVO reportVO = new ReportVO();
				reportVO.setTeacher(teacherVO);
				reportVO.setAttendance(attendanceVOs);

				reportVOs.add(reportVO);
			}
			
			misReportVO.setData(reportVOs);
			
			return ResponseVO.create(ResponseCode.SUCCESSFUL.getId(), ResponseCode.SUCCESSFUL.getName(),
					ResponseCode.SUCCESSFUL.getName(), misReportVO);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			return ResponseVO.create(ResponseCode.FAIL.getId(), ResponseCode.FAIL.getName(),
					ResponseCode.FAIL.getName(), misReportVO);
		}
		
	}

	@Override
	public ResponseVO<List<ReportVO>> generateReport(ReportRequestVO reqVO) {
		List<ReportVO> reportVOs = new ArrayList<ReportVO>();

		try {
			LocalDate currentDate = LocalDate.now();
			
			LocalDate date = LocalDate.now().withMonth(reqVO.getMonth()).withYear(reqVO.getYear());

			LocalDate startDate = date.withDayOfMonth(1);
			LocalDate endDate = date.withDayOfMonth(date.lengthOfMonth()); 
			
			if (currentDate.getMonthValue() == reqVO.getMonth()) {
				endDate = currentDate;
			} 			

			Date stDt = Date.from(startDate.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
			Date edDt = Date.from(endDate.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());

			JwtUser jwtUser = Utils.getLoggedUserFromToken();

			List<TeacherAttendance> teacherAttendanceList = teacherAttendanceRepository.findByUserIdAndDate(jwtUser.getId(),
					stDt, edDt);

			Map<Teacher, List<TeacherAttendance>> teacherAttendanceMap = teacherAttendanceList.stream()
					.collect(Collectors.groupingBy(TeacherAttendance::getTeacher));

			for (Entry<Teacher, List<TeacherAttendance>> entry : teacherAttendanceMap.entrySet()) {
				TeacherVO teacherVO = convertToVO(entry.getKey());
				
				List<AttendanceVO> attendanceVOs = generateAttendanceReport(startDate, endDate, entry.getValue());

				ReportVO reportVO = new ReportVO();
				reportVO.setTeacher(teacherVO);
				reportVO.setAttendance(attendanceVOs);

				reportVOs.add(reportVO);
			}
			return ResponseVO.create(ResponseCode.SUCCESSFUL.getId(), ResponseCode.SUCCESSFUL.getName(),
					ResponseCode.SUCCESSFUL.getName(), reportVOs);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			return ResponseVO.create(ResponseCode.FAIL.getId(), ResponseCode.FAIL.getName(),
					ResponseCode.FAIL.getName(), reportVOs);
		}
		
	}

	private TeacherVO convertToVO(Teacher teacher) {
		TeacherVO teacherVO = new TeacherVO();
		teacherVO.setEmployeeId(teacher.getTeacherId());
		teacherVO.setFullName(generateFullName(teacher));
		teacherVO.setDateOfBirth(Utils.formatDate(teacher.getDateOfBirth()));
		teacherVO.setDateOfJoining(Utils.formatDate(teacher.getDateOfJoining()));
		teacherVO.setFullAddress(generateFullAddress(teacher));
		teacherVO.setMobile(teacher.getMobileNumber());
		teacherVO.setEmail(teacher.getEmailAddress());
		teacherVO.setDesignation(teacher.getDesignation());
		teacherVO.setPostingLocation(teacher.getPostingLocation().getName());
		teacherVO.setInstitutionName(teacher.getInstitution().getName());
		//teacherVO.setSuperivorName(teacher.getSupervisor().getSupervisorName());
		teacherVO.setRegistrationId(teacher.getRegistrationId());
		return teacherVO;
	}

	private String generateFullAddress(Teacher teacher) {
		String fullAddress = teacher.getHouseName();
		
		if (StringUtils.isNotBlank(teacher.getAddress1())) {
			fullAddress = StringUtils.join(fullAddress, ", ", teacher.getAddress1());
		}
		
		if (StringUtils.isNotBlank(teacher.getAddress2())) {
			fullAddress = StringUtils.join(fullAddress, ", ", teacher.getAddress2());
		}
		
		if (StringUtils.isNotBlank(teacher.getLandmark())) {
			fullAddress = StringUtils.join(fullAddress, ", ", teacher.getLandmark());
		}
		
		if (Objects.nonNull(teacher.getCity())) {
			fullAddress = StringUtils.join(fullAddress, ", ", teacher.getCity().getName());
		}
		
		if (Objects.nonNull(teacher.getState())) {
			fullAddress = StringUtils.join(fullAddress, ", ", teacher.getState().getName());
		}
		
		if (StringUtils.isNotBlank(teacher.getPincode())) {
			fullAddress = StringUtils.join(fullAddress, " - ", teacher.getPincode());
		}
		
		return fullAddress;
	}
	
	private String generateFullName(Teacher teacher) {
		return StringUtils.join(StringUtils.defaultIfBlank(teacher.getFirstName(), ""), " ",
				StringUtils.defaultIfBlank(teacher.getMiddleName(), ""), " ",
				StringUtils.defaultIfBlank(teacher.getLastName(), ""));
	}

	private List<AttendanceVO> generateAttendanceReport(LocalDate startDate, LocalDate endDate,
			List<TeacherAttendance> teacherAttendance) {
		List<AttendanceVO> attendanceVOs = new ArrayList<AttendanceVO>();
		
		Map<String, TeacherAttendance> teacherAttMap = teacherAttendance.stream().collect(Collectors.toMap(obj -> Utils.formatDateMonth(obj.getAttendanceDate()), Function.identity(), (a1, a2) -> a1));
		
		LocalDate d = startDate;

		while (d.isBefore(endDate) || d.equals(endDate)) {
			String fmDt = d.format(DateTimeFormatter.ofPattern("dd-MMM-yyyy"));
			AttendanceVO attendance = new AttendanceVO();
			attendance.setDate(fmDt);

			if (d.getDayOfWeek() == DayOfWeek.SUNDAY) {
				attendance.setStatus("-");
				attendance.setIsHoliday(true);
			} else {
				attendance.setIsHoliday(false);
				if (teacherAttMap.containsKey(fmDt)) {
					TeacherAttendance teacherAtt = teacherAttMap.get(fmDt);
					
					if (Objects.nonNull(teacherAtt.getStatus())) {
						attendance.setStatus(teacherAtt.getStatus().name());
					} else {
						attendance.setStatus("-");
					}
				} else {
					attendance.setStatus("-");
				}
			}
			
			attendanceVOs.add(attendance);
			
			d = d.plusDays(1);
		}
		
		return attendanceVOs;
	}
}
