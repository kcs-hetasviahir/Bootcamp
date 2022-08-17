package com.kcs.attendancesystem.service.impl;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import com.kcs.attendancesystem.core.Teacher;
import com.kcs.attendancesystem.core.TeacherAttendance;
import com.kcs.attendancesystem.dto.AttendanceRequestVO;
import com.kcs.attendancesystem.dto.ResponseVO;
import com.kcs.attendancesystem.dto.TeacherAttendanceCount;
import com.kcs.attendancesystem.dto.TeacherAttendanceVO;
import com.kcs.attendancesystem.dto.TeacherCountVO;
import com.kcs.attendancesystem.enums.AttendanceStatus;
import com.kcs.attendancesystem.enums.ResponseCode;
import com.kcs.attendancesystem.repository.TeacherAttendanceRepository;
import com.kcs.attendancesystem.repository.TeacherRepository;
import com.kcs.attendancesystem.service.TeacherAttendanceService;
import com.kcs.attendancesystem.utils.Utils;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class TeacherAttendanceServiceImpl implements TeacherAttendanceService {

	@Autowired
	private TeacherAttendanceRepository teacherAttendanceRepository;

	@Autowired
	private TeacherRepository teacherRepository;

	public void addAbsentEntry() {

		Date date = new Date();

		List<Teacher> teachers = teacherRepository.findAll();

		List<TeacherAttendance> teacherAttendance = teacherAttendanceRepository.findByAtDate(date);

		Map<Teacher, List<TeacherAttendance>> teacherAttMap = teacherAttendance.stream()
				.collect(Collectors.groupingBy(TeacherAttendance::getTeacher));

		for (Teacher teacher : teachers) {
			if (!teacherAttMap.containsKey(teacher)) {
				TeacherAttendance teacherAtt = new TeacherAttendance();
				teacherAtt.setTeacher(teacher);
				teacherAtt.setAttendanceDate(date);
				teacherAtt.setStatus(AttendanceStatus.ABSENT);
				teacherAtt.setIsProcessed(true);
				teacherAttendanceRepository.save(teacherAtt);
			}
		}
	}

	@Override
	public ResponseVO<Page<TeacherAttendanceVO>> generateTeacherAttendanceReportMis(AttendanceRequestVO pageRequestVO) {
		PageRequest pageRequest = PageRequest.of(pageRequestVO.getPageNo(), pageRequestVO.getPageSize(),
				Sort.by(Sort.Direction.DESC, "id"));

		Page<TeacherAttendance> teacherAttPage = null;
		List<TeacherAttendance> teacherAttendances = new ArrayList<>();
		List<TeacherAttendance> list = new ArrayList<>();

		// -----------------------------------------------------------------------------------------------------------------------------
		// District Sorting Starts
		if (StringUtils.isNotBlank(pageRequestVO.getSsaDistrict())) { // Block Sorting Starts
			if (StringUtils.isNotBlank(pageRequestVO.getSsaBlock())) { // Cluster Sorting Starts
				if (StringUtils.isNotBlank(pageRequestVO.getSsaCluster())) { // School Sorting
					if (StringUtils.isNotBlank(pageRequestVO.getSsaSchool())) {
						if (StringUtils.isNotBlank(pageRequestVO.getFromDate()) && StringUtils.isNotBlank(pageRequestVO
								.getToDate())) { /*
													 * First Checks Whether District code matches or not After that
													 * Block Code After that Cluster Code At last School code and at
													 * last adds it to the list (This works only when date is Given)
													 */
							teacherAttendances = teacherAttendanceRepository.findByAtDateBetween(
									Utils.parseDate(pageRequestVO.getFromDate()),
									Utils.parseDate(pageRequestVO.getToDate()));
							for (TeacherAttendance teacherAttendance : teacherAttendances) {
								if (teacherAttendance.getTeacher().getSsaDistrictCode()
										.equals(Long.valueOf(pageRequestVO.getSsaDistrict()))) {
									if (teacherAttendance.getTeacher().getSsaBlockCode()
											.equals(Long.valueOf(pageRequestVO.getSsaBlock()))) {
										if (teacherAttendance.getTeacher().getSsaClusterCode()
												.equals(Long.valueOf(pageRequestVO.getSsaCluster()))) {
											if (teacherAttendance.getTeacher().getSsaSchoolCode()
													.equals(Long.valueOf(pageRequestVO.getSsaSchool()))) {
												list.add(teacherAttendance);
											}
										}
									}

								}

							}
							teacherAttPage = new PageImpl<>(list, pageRequest, list.size());

						}

						if (!StringUtils.isNotBlank(pageRequestVO.getFromDate()) && !StringUtils.isNotBlank(
								pageRequestVO.getToDate())) { /*
																 * First Checks Whether District code matches or not
																 * After that Block Code After that Cluster Code At last
																 * School code and at last adds it to the list (This
																 * works only when date is not Given)
																 */
							teacherAttendances = teacherAttendanceRepository
									.findBySSACluster(Long.valueOf(pageRequestVO.getSsaCluster()));
							for (TeacherAttendance teacherAttendance : teacherAttendances) {

								if (teacherAttendance.getTeacher().getSsaDistrictCode()
										.equals(Long.valueOf(pageRequestVO.getSsaDistrict()))) {
									if (teacherAttendance.getTeacher().getSsaBlockCode()
											.equals(Long.valueOf(pageRequestVO.getSsaBlock()))) {
										if (teacherAttendance.getTeacher().getSsaClusterCode()
												.equals(Long.valueOf(pageRequestVO.getSsaCluster()))) {
											if (teacherAttendance.getTeacher().getSsaSchoolCode()
													.equals(Long.valueOf(pageRequestVO.getSsaSchool()))) {
												list.add(teacherAttendance);
											}
										}
									}

								}
							}
							teacherAttPage = new PageImpl<>(list, pageRequest, list.size());
						}
					} else {
						if (StringUtils.isNotBlank(pageRequestVO.getFromDate())
								&& StringUtils.isNotBlank(pageRequestVO.getToDate())) {
							teacherAttendances = teacherAttendanceRepository.findByAtDateBetween(
									Utils.parseDate(pageRequestVO.getFromDate()),
									Utils.parseDate(pageRequestVO.getToDate()));
							for (TeacherAttendance teacherAttendance : teacherAttendances) {
								if (teacherAttendance.getTeacher().getSsaDistrictCode()
										.equals(Long.valueOf(pageRequestVO.getSsaDistrict()))) {
									if (teacherAttendance.getTeacher().getSsaBlockCode()
											.equals(Long.valueOf(pageRequestVO.getSsaBlock()))) {
										if (teacherAttendance.getTeacher().getSsaClusterCode()
												.equals(Long.valueOf(pageRequestVO.getSsaCluster()))) {
											list.add(teacherAttendance);
										}
									}

								}

							}
							teacherAttPage = new PageImpl<>(list, pageRequest, list.size());

						}
					}

					if (!StringUtils.isNotBlank(pageRequestVO.getFromDate())
							&& !StringUtils.isNotBlank(pageRequestVO.getToDate())) {
						teacherAttendances = teacherAttendanceRepository
								.findBySSABlock(Long.valueOf(pageRequestVO.getSsaBlock()));
						for (TeacherAttendance teacherAttendance : teacherAttendances) {

							if (teacherAttendance.getTeacher().getSsaDistrictCode()
									.equals(Long.valueOf(pageRequestVO.getSsaDistrict()))) {
								if (teacherAttendance.getTeacher().getSsaBlockCode()
										.equals(Long.valueOf(pageRequestVO.getSsaBlock()))) {
									if (teacherAttendance.getTeacher().getSsaClusterCode()
											.equals(Long.valueOf(pageRequestVO.getSsaCluster()))) {
										list.add(teacherAttendance);
									}
								}

							}
						}
						teacherAttPage = new PageImpl<>(list, pageRequest, list.size());

					}

				} else {

					if (StringUtils.isNotBlank(pageRequestVO.getFromDate())
							&& StringUtils.isNotBlank(pageRequestVO.getToDate())) {

						teacherAttendances = teacherAttendanceRepository.findByAtDateBetween(
								Utils.parseDate(pageRequestVO.getFromDate()),
								Utils.parseDate(pageRequestVO.getToDate()));

						for (TeacherAttendance teacherAttendance : teacherAttendances) {

							if (teacherAttendance.getTeacher().getSsaDistrictCode()
									.equals(Long.valueOf(pageRequestVO.getSsaDistrict()))) {
								if (teacherAttendance.getTeacher().getSsaBlockCode()
										.equals(Long.valueOf(pageRequestVO.getSsaBlock()))) {
									list.add(teacherAttendance);
								}

							}
						}

						teacherAttPage = new PageImpl<>(list, pageRequest, list.size());

					}

					if (!StringUtils.isNotBlank(pageRequestVO.getFromDate())
							&& !StringUtils.isNotBlank(pageRequestVO.getToDate())) {
						teacherAttendances = teacherAttendanceRepository
								.findBySSADistrict(Long.valueOf(pageRequestVO.getSsaDistrict()));
						for (TeacherAttendance teacherAttendance : teacherAttendances) {

							if (teacherAttendance.getTeacher().getSsaDistrictCode()
									.equals(Long.valueOf(pageRequestVO.getSsaDistrict()))) {
								if (teacherAttendance.getTeacher().getSsaBlockCode()
										.equals(Long.valueOf(pageRequestVO.getSsaBlock()))) {
									list.add(teacherAttendance);
								}

							}
						}
						teacherAttPage = new PageImpl<>(list, pageRequest, list.size());
					}
				}

			} else {

				if (StringUtils.isNotBlank(pageRequestVO.getFromDate())
						&& StringUtils.isNotBlank(pageRequestVO.getToDate())) {

					teacherAttendances = teacherAttendanceRepository.findByAtDateBetween(
							Utils.parseDate(pageRequestVO.getFromDate()), Utils.parseDate(pageRequestVO.getToDate()));

					for (TeacherAttendance teacherAttendance : teacherAttendances) {

						if (teacherAttendance.getTeacher().getSsaDistrictCode()
								.equals(Long.valueOf(pageRequestVO.getSsaDistrict()))) {

							list.add(teacherAttendance);

						}
					}

					teacherAttPage = new PageImpl<>(list, pageRequest, list.size());

				}

				if (!StringUtils.isNotBlank(pageRequestVO.getFromDate())
						&& !StringUtils.isNotBlank(pageRequestVO.getToDate())) {
					teacherAttPage = teacherAttendanceRepository
							.findBySSADistrict(Long.valueOf(pageRequestVO.getSsaDistrict()), pageRequest);

				}

			}

		} else {

			if (StringUtils.isNotBlank(pageRequestVO.getFromDate())
					&& StringUtils.isNotBlank(pageRequestVO.getToDate())) {

				teacherAttPage = teacherAttendanceRepository.findByAtDateBetween(
						Utils.parseDate(pageRequestVO.getFromDate()), Utils.parseDate(pageRequestVO.getToDate()),
						pageRequest);

			}

			else {

				teacherAttPage = teacherAttendanceRepository.findAll(pageRequest);
			}

		}

		Page<TeacherAttendanceVO> teacherVOPage = teacherAttPage.map(obj -> convertToVO(obj));

		return ResponseVO.create(ResponseCode.SUCCESSFUL.getId(), ResponseCode.SUCCESSFUL.getName(),
				ResponseCode.SUCCESSFUL.getName(), teacherVOPage);
	}

	@Override
	public Page<TeacherAttendanceVO> generateTeacherAttendanceReport(AttendanceRequestVO pageRequestVO) {
		PageRequest pageRequest = PageRequest.of(pageRequestVO.getPageNo(), pageRequestVO.getPageSize(),
				Sort.by(Sort.Direction.DESC, "id"));

		Page<TeacherAttendance> teacherAttPage;

		if (StringUtils.isNotBlank(pageRequestVO.getFromDate()) && StringUtils.isNotBlank(pageRequestVO.getToDate())) {
			teacherAttPage = teacherAttendanceRepository.findByAtDateBetween(
					Utils.parseDate(pageRequestVO.getFromDate()), Utils.parseDate(pageRequestVO.getToDate()),
					pageRequest);
			if (StringUtils.isNotBlank(pageRequestVO.getEmployeeId())) {
				teacherAttPage = teacherAttendanceRepository.findByTeacherIdAndDate(
						Long.parseLong(pageRequestVO.getEmployeeId()), Utils.parseDate(pageRequestVO.getFromDate()),
						Utils.parseDate(pageRequestVO.getToDate()), pageRequest);
			}
			if (StringUtils.isNotBlank(pageRequestVO.getSsaDistrict())) {
				teacherAttPage = teacherAttendanceRepository.findByTeacherSSADistrictAndDate(
						Long.parseLong(pageRequestVO.getSsaDistrict()), Utils.parseDate(pageRequestVO.getFromDate()),
						Utils.parseDate(pageRequestVO.getToDate()), pageRequest);
			}
			if (StringUtils.isNotBlank(pageRequestVO.getSsaBlock())) {
				teacherAttPage = teacherAttendanceRepository.findByTeacherSSABlockAndDate(
						Long.parseLong(pageRequestVO.getSsaBlock()), Utils.parseDate(pageRequestVO.getFromDate()),
						Utils.parseDate(pageRequestVO.getToDate()), pageRequest);
			}
			if (StringUtils.isNotBlank(pageRequestVO.getSsaCluster())) {
				teacherAttPage = teacherAttendanceRepository.findByTeacherSSAClusterAndDate(
						Long.parseLong(pageRequestVO.getSsaCluster()), Utils.parseDate(pageRequestVO.getFromDate()),
						Utils.parseDate(pageRequestVO.getToDate()), pageRequest);
			}
			if (StringUtils.isNotBlank(pageRequestVO.getSsaSchool())) {
				teacherAttPage = teacherAttendanceRepository.findByTeacherSSASchoolAndDate(
						Long.parseLong(pageRequestVO.getSsaSchool()), Utils.parseDate(pageRequestVO.getFromDate()),
						Utils.parseDate(pageRequestVO.getToDate()), pageRequest);
			}
			if (StringUtils.isNotBlank(pageRequestVO.getDesignation())) {
				teacherAttPage = teacherAttendanceRepository.findByDesignationAndDate(pageRequestVO.getDesignation(),
						Utils.parseDate(pageRequestVO.getFromDate()), Utils.parseDate(pageRequestVO.getToDate()),
						pageRequest);
			}
		} else if (StringUtils.isNotBlank(pageRequestVO.getUserId()) && StringUtils.isBlank(pageRequestVO.getFromDate())
				&& StringUtils.isBlank(pageRequestVO.getToDate())) {
			teacherAttPage = teacherAttendanceRepository.findByUserId(Long.parseLong(pageRequestVO.getUserId()),
					pageRequest);
		} else if (StringUtils.isNotBlank(pageRequestVO.getEmployeeId())
				&& StringUtils.isBlank(pageRequestVO.getFromDate()) && StringUtils.isBlank(pageRequestVO.getToDate())) {
			teacherAttPage = teacherAttendanceRepository.findByTeacherId(pageRequestVO.getEmployeeId(), pageRequest);
		} else if (StringUtils.isNotBlank(pageRequestVO.getSsaDistrict())
				&& StringUtils.isBlank(pageRequestVO.getFromDate()) && StringUtils.isBlank(pageRequestVO.getToDate())) {
			teacherAttPage = teacherAttendanceRepository
					.findBySSADistrict(Long.parseLong(pageRequestVO.getSsaDistrict()), pageRequest);
		} else if (StringUtils.isNotBlank(pageRequestVO.getSsaBlock())
				&& StringUtils.isBlank(pageRequestVO.getFromDate()) && StringUtils.isBlank(pageRequestVO.getToDate())) {
			teacherAttPage = teacherAttendanceRepository.findBySSABlock(Long.parseLong(pageRequestVO.getSsaBlock()),
					pageRequest);
		} else if (StringUtils.isNotBlank(pageRequestVO.getSsaCluster())
				&& StringUtils.isBlank(pageRequestVO.getFromDate()) && StringUtils.isBlank(pageRequestVO.getToDate())) {
			teacherAttPage = teacherAttendanceRepository.findBySSACluster(Long.parseLong(pageRequestVO.getSsaCluster()),
					pageRequest);
		} else if (StringUtils.isNotBlank(pageRequestVO.getSsaSchool())
				&& StringUtils.isBlank(pageRequestVO.getFromDate()) && StringUtils.isBlank(pageRequestVO.getToDate())) {
			teacherAttPage = teacherAttendanceRepository.findBySSASchool(Long.parseLong(pageRequestVO.getSsaSchool()),
					pageRequest);
		} else if (StringUtils.isNotBlank(pageRequestVO.getDesignation())
				&& StringUtils.isBlank(pageRequestVO.getFromDate()) && StringUtils.isBlank(pageRequestVO.getToDate())) {
			teacherAttPage = teacherAttendanceRepository.findByDesignation(pageRequestVO.getDesignation(), pageRequest);
		} else {
			teacherAttPage = teacherAttendanceRepository.findAll(pageRequest);
		}

		Page<TeacherAttendanceVO> teacherVOPage = teacherAttPage.map(obj -> convertToVO(obj));

		return teacherVOPage;
	}

	private TeacherAttendanceVO convertToVO(TeacherAttendance teacherAttendance) {
		TeacherAttendanceVO vo = new TeacherAttendanceVO();

		vo.setId(teacherAttendance.getId());
		vo.setTeacherName(generateFullName(teacherAttendance.getTeacher()));
		vo.setAttendanceDate(Utils.formatDate(teacherAttendance.getAttendanceDate()));
		vo.setAttendanceStatus(teacherAttendance.getStatus().name());
		// vo.setVideoName(teacherAttendance.getVideoName());
		// vo.setIsProcessed(teacherAttendance.getIsProcessed());
		// vo.setProcessedStatus(teacherAttendance.getProcessedStatus());
		// vo.setVideoLabel(teacherAttendance.getVideoLabel());
		// vo.setProcessStartTime(Utils.formatIoTDate(teacherAttendance.getProcessStartTime()));
		// vo.setProcessEndTime(Utils.formatIoTDate(teacherAttendance.getProcessEndTime()));
		// vo.setTotalTime(Utils.diffBetDates(teacherAttendance.getProcessStartTime(),
		// teacherAttendance.getProcessEndTime()).intValue());
		vo.setAccuracy(StringUtils.defaultString(teacherAttendance.getPersonAccuracy(), ""));
		vo.setLongitude(teacherAttendance.getLongitude());
		vo.setLatitude(teacherAttendance.getLatitude());
		vo.setCreatedDate(Utils.formatDateTime(teacherAttendance.getCreatedDate()));
		vo.setUpdatedDate(Utils.formatDateTime(teacherAttendance.getLastUpdatedDate()));
		vo.setTransactionId(teacherAttendance.getTransactionId());
		vo.setDeviceId(teacherAttendance.getDeviceId());

		return vo;
	}

	private String generateFullName(Teacher teacher) {
		if (Objects.nonNull(teacher)) {
			return StringUtils.join(StringUtils.defaultIfBlank(teacher.getFirstName(), ""), " ",
					StringUtils.defaultIfBlank(teacher.getMiddleName(), ""), " ",
					StringUtils.defaultIfBlank(teacher.getLastName(), ""));
		}

		return StringUtils.EMPTY;
	}

	@Override
	public ResponseVO<List<TeacherAttendanceCount>> TeacherCountByAttendance() {

		try {
			Set<Date> dates = new HashSet<>();
			Set<TeacherAttendanceCount> list = new HashSet<>();
			List<TeacherAttendanceCount> teacherList = new ArrayList<>();
			List<TeacherAttendance> all = teacherAttendanceRepository.findAll();

			// all.forEach(e -> dates.add(e.getAttendanceDate()));

			for (TeacherAttendance teacherAttendance : all) {

				dates.add(teacherAttendance.getAttendanceDate());

			}

			for (Date date : dates) {

				List<TeacherAttendance> attendanceList = new ArrayList<>();
				attendanceList = teacherAttendanceRepository.findByAtDateBetween(date, date);

				Map<Long, TeacherAttendance> map = new LinkedHashMap<>();
				for (TeacherAttendance convert : attendanceList) {
					map.put(convert.getId(), convert);
				}

				for (Map.Entry<Long, TeacherAttendance> entry : map.entrySet()) {

					Long i = map.entrySet().parallelStream().filter(
							e -> e.getValue().getTeacher().getId().equals(entry.getValue().getTeacher().getId()))
							.count();
					if (i > 2) {

						List<String> time = new ArrayList<>();
						for (TeacherAttendance teacherAttendance : attendanceList) {
							if (teacherAttendance.getTeacher().getId().equals(entry.getValue().getTeacher().getId())) {

								if (teacherAttendance.getAttendanceDate().getHours() == 0
										&& teacherAttendance.getAttendanceDate().getMinutes() == 0
										&& teacherAttendance.getAttendanceDate().getSeconds() == 0) {
									time.add(teacherAttendance.getCreatedDate().toString().substring(11, 19));
								} else {

									time.add(teacherAttendance.getAttendanceDate().toString().substring(11, 19));
								}
							}

						}
						TeacherAttendanceCount teacherAttendanceCount = new TeacherAttendanceCount();
						teacherAttendanceCount.setTeacherId(entry.getValue().getTeacher().getId());
						teacherAttendanceCount.setCount(i);
						LocalDate localDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

						teacherAttendanceCount.setDate(localDate);
						teacherAttendanceCount.setTime(time);

						teacherAttendanceCount.setTeacherName(entry.getValue().getTeacher().getFirstName());
						list.add(teacherAttendanceCount);

					}

				}

			}

			// get Comparator using Lambda expression
			Comparator<TeacherAttendanceCount> comparatorAsc = (prod1, prod2) -> prod1.getDate()
					.compareTo(prod2.getDate());

			List<TeacherAttendanceCount> a = new ArrayList<>(list);
			Collections.sort(a, comparatorAsc);
			for (TeacherAttendanceCount teacherAttendanceCount : a) {
				if (!teacherList.parallelStream()
						.anyMatch(e -> e.getTeacherId().equals(teacherAttendanceCount.getTeacherId()))) {
					teacherList.add(teacherAttendanceCount);
				}
			}
			return ResponseVO.create(ResponseCode.SUCCESSFUL.getId(), ResponseCode.SUCCESSFUL.getName(),
					ResponseCode.SUCCESSFUL.getName(), a);

		} catch (Exception e) {
			return ResponseVO.create(ResponseCode.FAIL.getId(), ResponseCode.FAIL.getName(),
					ResponseCode.FAIL.getName(), null);

		}
	}

	@Override
	public ResponseVO<TeacherCountVO> TeacherRegularCount() {
		try {
			Set<Date> dates = new HashSet<>();
			Set<TeacherAttendanceCount> list = new HashSet<>();
			Set<TeacherAttendanceCount> NotRegularlist = new HashSet<>();

			List<TeacherAttendance> all = teacherAttendanceRepository.findAll();

			// all.forEach(e -> dates.add(e.getAttendanceDate()));

			for (TeacherAttendance teacherAttendance : all) {

				dates.add(teacherAttendance.getAttendanceDate());

			}

			for (Date date : dates) {

				List<TeacherAttendance> attendanceList = new ArrayList<>();
				attendanceList = teacherAttendanceRepository.findByAtDateBetween(date, date);

				Map<Long, TeacherAttendance> map = new LinkedHashMap<>();
				for (TeacherAttendance convert : attendanceList) {
					map.put(convert.getId(), convert);
				}

				for (Map.Entry<Long, TeacherAttendance> entry : map.entrySet()) {

					Long i = map.entrySet().parallelStream().filter(
							e -> e.getValue().getTeacher().getId().equals(entry.getValue().getTeacher().getId()))
							.count();
					if (i > 2) {

						List<String> time = new ArrayList<>();
						for (TeacherAttendance teacherAttendance : attendanceList) {
							if (teacherAttendance.getTeacher().getId().equals(entry.getValue().getTeacher().getId())) {

								if (teacherAttendance.getAttendanceDate().getHours() == 0
										&& teacherAttendance.getAttendanceDate().getMinutes() == 0
										&& teacherAttendance.getAttendanceDate().getSeconds() == 0) {
									time.add(teacherAttendance.getCreatedDate().toString().substring(11, 19));
								} else {

									time.add(teacherAttendance.getAttendanceDate().toString().substring(11, 19));
								}
							}

						}
						TeacherAttendanceCount teacherAttendanceCount = new TeacherAttendanceCount();
						teacherAttendanceCount.setTeacherId(entry.getValue().getTeacher().getId());
						teacherAttendanceCount.setCount(i);
						LocalDate localDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

						teacherAttendanceCount.setDate(localDate);
						teacherAttendanceCount.setTime(time);

						teacherAttendanceCount.setTeacherName(entry.getValue().getTeacher().getFirstName());
						list.add(teacherAttendanceCount);

					} else {
						List<String> time = new ArrayList<>();
						for (TeacherAttendance teacherAttendance : attendanceList) {
							if (teacherAttendance.getTeacher().getId().equals(entry.getValue().getTeacher().getId())) {

								if (teacherAttendance.getAttendanceDate().getHours() == 0
										&& teacherAttendance.getAttendanceDate().getMinutes() == 0
										&& teacherAttendance.getAttendanceDate().getSeconds() == 0) {
									time.add(teacherAttendance.getCreatedDate().toString().substring(11, 19));
								} else {

									time.add(teacherAttendance.getAttendanceDate().toString().substring(11, 19));
								}
							}

						}
						TeacherAttendanceCount teacherAttendanceCount = new TeacherAttendanceCount();
						teacherAttendanceCount.setTeacherId(entry.getValue().getTeacher().getId());
						teacherAttendanceCount.setCount(i);
						LocalDate localDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

						teacherAttendanceCount.setDate(localDate);
						teacherAttendanceCount.setTime(time);

						teacherAttendanceCount.setTeacherName(entry.getValue().getTeacher().getFirstName());
						NotRegularlist.add(teacherAttendanceCount);

					}

				}

			}
			TeacherCountVO teacherCountVO = new TeacherCountVO();
			teacherCountVO.setRegular(Long.valueOf(list.size()));
			teacherCountVO.setNotRegular(Long.valueOf(NotRegularlist.size()));
			return ResponseVO.create(ResponseCode.SUCCESSFUL.getId(), ResponseCode.SUCCESSFUL.getName(),
					ResponseCode.SUCCESSFUL.getName(), teacherCountVO);

		} catch (Exception e) {

			return ResponseVO.create(ResponseCode.FAIL.getId(), ResponseCode.FAIL.getName(),
					ResponseCode.FAIL.getName(), null);

		}

	}

}
