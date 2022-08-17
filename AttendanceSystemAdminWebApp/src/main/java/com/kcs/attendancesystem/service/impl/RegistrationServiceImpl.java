package com.kcs.attendancesystem.service.impl;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Objects;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import javax.transaction.Transactional;

import com.kcs.attendancesystem.core.Institution;
import com.kcs.attendancesystem.core.Teacher;
import com.kcs.attendancesystem.core.TeacherAttendance;
import com.kcs.attendancesystem.core.Village;
import com.kcs.attendancesystem.dto.AttendanceVO;
import com.kcs.attendancesystem.dto.DetectFacesResponseVO;
import com.kcs.attendancesystem.dto.DetectFacesSettingsWrapperVO;
import com.kcs.attendancesystem.dto.DetectFacesWrapperVO;
import com.kcs.attendancesystem.dto.EnrollSubjectWithAlbumRequestRestVO;
import com.kcs.attendancesystem.dto.EnrollSubjectWithAlbumWrapperVO;
import com.kcs.attendancesystem.dto.IdentifyFacesResponseVO;
import com.kcs.attendancesystem.dto.IdentifyFacesWrapperVO;
import com.kcs.attendancesystem.dto.ResponseVO;
import com.kcs.attendancesystem.dto.TeacherVO;
import com.kcs.attendancesystem.enums.AttendanceStatus;
import com.kcs.attendancesystem.enums.ResponseCode;
import com.kcs.attendancesystem.repository.InstitutionRepository;
import com.kcs.attendancesystem.repository.TeacherAttendanceRepository;
import com.kcs.attendancesystem.repository.TeacherRepository;
import com.kcs.attendancesystem.repository.VillageRepository;
import com.kcs.attendancesystem.service.HertaService;
import com.kcs.attendancesystem.service.NotificationService;
import com.kcs.attendancesystem.service.RegistrationService;
import com.kcs.attendancesystem.utility.Utility;
import com.kcs.attendancesystem.utils.Utils;

import org.apache.commons.lang3.StringUtils;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@Transactional
public class RegistrationServiceImpl implements RegistrationService {

	private ExecutorService executor = Executors.newSingleThreadExecutor();

	@Autowired
	private TeacherRepository teacherRepository;



	@Autowired
	private InstitutionRepository institutionRepository;

	@Autowired
	private TeacherAttendanceRepository teacherAttendanceRepository;

	@Autowired
	private NotificationService notificationService;

	@Autowired
	private VillageRepository villageRepository;



	@Autowired
	private HertaService hertaService;

	@Value("${herta.face.detection.threshold}")
	private int faceDetectionThreshold;

	@Value("${herta.face.quality.threshold}")
	private int faceQualityThreshold;

	@Value("${herta.minimum.face.size}")
	private int minimumFaceSize;

	@Value("${herta.identify.subject.ratio}")
	private Double identifySubjectRatio;

	@Override
	public ResponseVO<String> addRegistration(TeacherVO teacherVO) throws RuntimeException 
	{
		long startTime = System.nanoTime();
		Teacher teacher = null;
		Teacher savedTeacher;

		boolean flag = false;
		String message = null;

		if (!Objects.nonNull(teacherVO)) {
			throw new RuntimeException(ResponseCode.INVALID_ARGUMENT.getName());
		}
		teacher = teacherRepository.findByTeacherId(teacherVO.getEmployeeId());
		if (teacher != null && teacher.getTeacherId().equals(teacherVO.getEmployeeId())) {
			return ResponseVO
					.create(ResponseCode.FAIL.getId(), ResponseCode.FAIL.getName(),
							"You are already registered in the system with registration id :"
									+ teacher.getRegistrationId() + " and employeeId is :" + teacher.getTeacherId(),
							StringUtils.EMPTY);
		}
		if (teacherVO.getFiles() != null && !(teacherVO.getFiles().length <= 0)) {
			for (MultipartFile uploadedFile : teacherVO.getFiles()) {

				/*
				 * EmpMaster empMaster =
				 * empMstRepository.findByEmpId(teacherVO.getEmployeeId());
				 * 
				 * 
				 * if (empMaster != null) { teacherVO.setFirstName(empMaster.getFirstName());
				 * teacherVO.setLastName(empMaster.getLastName());
				 * teacherVO.setDateOfBirth(Utils.formatDateTime(empMaster.getDob()));
				 * teacherVO.setDateOfJoining(Utils.formatDateTime(empMaster.getDoj()));
				 * teacherVO.setSuperivorId(empMaster.getSupervisorId());
				 * teacherVO.setGender(empMaster.getGender()); }
				 */
				// Detect Face in Herta System
				DetectFacesResponseVO detectFacesResponseVO = detectFaces(uploadedFile);

				if (detectFacesResponseVO != null && StringUtils.isNotBlank(detectFacesResponseVO.getFaceImage())) {
					// Identify Face in Herta System
					IdentifyFacesResponseVO identifyFacesResponseVO = identifyFace(uploadedFile);
					if (identifyFacesResponseVO != null
							&& StringUtils
									.isNotBlank(identifyFacesResponseVO.getIdentifySubjectResult().get(0).getKey())
							&& identifyFacesResponseVO.getIdentifySubjectResult().get(0)
									.getValue() < identifySubjectRatio) {

						// Enroll Face in Herta System
						enrollSubjectWithAlbum(teacherVO);
						Village village = null;
						if (!StringUtils.isBlank(teacherVO.getVillage())) {
							village = villageRepository.findById(Long.parseLong(teacherVO.getVillage())).get();
						}
						/*
						 * State state = null; Taluka taluka = null; District district = null;
						 * 
						 * 
						 * 
						 * if (!StringUtils.isBlank(teacherVO.getTaluka())) { taluka =
						 * talukaRepository.findById(Long.parseLong(teacherVO.getTaluka())).get(); } if
						 * (!StringUtils.isBlank(teacherVO.getDistrict())) { district =
						 * districtRepository.findById(Long.parseLong(teacherVO.getDistrict())).get(); }
						 * if (!StringUtils.isBlank(teacherVO.getStateId())) { state =
						 * stateRepository.findById(Long.parseLong(teacherVO.getStateId())).get(); }
						 */

//                        String filename = StringUtils.join(village.getId(), "-", taluka.getId(), "-", district.getId(), "-", state.getCode(), "-", teacherVO.getEmployeeId(), ".", FilenameUtils.getExtension(uploadedFile.getOriginalFilename()));
//                        log.info("FILENAME : {}", filename);
//
//                        teacherVO.setFilePath(teacherVO.getFilePath() + filename);

						teacher = new Teacher();
						BeanUtils.copyProperties(teacherVO, teacher);
//                        teacher.setVideoName(filename);
//                        teacher.setVideoLabel(FilenameUtils.getBaseName(filename));
//                        teacher.setIsProcessed(false);
						teacher.setTeacherId(teacherVO.getEmployeeId());
						teacher.setEmailAddress(teacherVO.getEmail());
						teacher.setDateOfBirth(Utils.parseDate(teacherVO.getDateOfBirth()));
						teacher.setDateOfJoining(Utils.parseDate(teacherVO.getDateOfJoining()));
						teacher.setVillage(village);
						/*
						 * teacher.setTaluka(taluka); teacher.setDistrict(district);
						 * teacher.setState(state);
						 */
						teacher.setGender(teacherVO.getGender());
						if (!StringUtils.isBlank(teacherVO.getSsaDistrict())) {
							teacher.setSsaDistrictCode(Long.parseLong(teacherVO.getSsaDistrict()));
						}
						if (!StringUtils.isBlank(teacherVO.getSsaBlock())) {
							teacher.setSsaBlockCode(Long.parseLong(teacherVO.getSsaBlock()));
						}
						if (!StringUtils.isBlank(teacherVO.getSsaCluster())) {
							teacher.setSsaClusterCode(Long.parseLong(teacherVO.getSsaCluster()));
						}
						if (!StringUtils.isBlank(teacherVO.getSsaSchool())) {
							teacher.setSsaSchoolCode(Long.parseLong(teacherVO.getSsaSchool()));
						}

						if (!StringUtils.isBlank(teacherVO.getSuperivorId())) {
							Teacher teacherDetails = teacherRepository.findByTeacherId(teacherVO.getSuperivorId());
							if (teacherDetails != null) {
								teacher.setSupervisorId(teacherDetails.getId());
							} else {
								teacher.setSupervisorId(Long.parseLong(teacherVO.getSuperivorId()));
							}
						}

						if (!StringUtils.isBlank(teacherVO.getInstitutionId())) {
							Institution institution = institutionRepository
									.findById(Long.parseLong(teacherVO.getInstitutionId())).get();
							teacher.setInstitution(institution);
						}

						/*
						 * if (!StringUtils.isBlank(teacherVO.getPostingLocation())) { City
						 * postingLocation = cityRepository
						 * .findById(Long.parseLong(teacherVO.getPostingLocation())).get();
						 * teacher.setPostingLocation(postingLocation); }
						 */

						savedTeacher = teacherRepository.save(teacher);

						final Teacher fTeacher = savedTeacher;
						executor.submit(() -> notificationService.sendMail(fTeacher));
						message = StringUtils.join("You are successfully registered with id : ",
								savedTeacher.getRegistrationId(), ", Latitude : ", savedTeacher.getLatitude(),
								" And Longitude : ", savedTeacher.getLongitude());

					} else {
						Teacher teacherResponse = teacherRepository
								.findByTeacherId(identifyFacesResponseVO.getIdentifySubjectResult().get(0).getKey());
						long stopTime = System.nanoTime();
						log.info("Exit from addRegistration Method from identifyFaces at "
								+ TimeUnit.MILLISECONDS.convert(stopTime - startTime, TimeUnit.NANOSECONDS) + " ms");
						return ResponseVO.create(ResponseCode.FAIL.getId(), ResponseCode.FAIL.getName(),
								"You are already registered in the system with registration id :"
										+ teacherResponse.getRegistrationId() + " and employeeId is :"
										+ teacherResponse.getTeacherId(),
								StringUtils.EMPTY);
					}
				} else {
					long stopTime = System.nanoTime();
					log.info("Exit from addRegistration Method form detectFaces at "
							+ TimeUnit.MILLISECONDS.convert(stopTime - startTime, TimeUnit.NANOSECONDS) + " ms");
					return ResponseVO.create(ResponseCode.FAIL.getId(), ResponseCode.FAIL.getName(),
							"Unable to detect face !, Please try again", StringUtils.EMPTY);
				}
			}
			flag = true;
		}
		if (!flag) {
			throw new RuntimeException(ResponseCode.FAIL.getName());
		}
		long stopTime = System.nanoTime();
		log.info("Exit from addRegistration Method at "
				+ TimeUnit.MILLISECONDS.convert(stopTime - startTime, TimeUnit.NANOSECONDS) + " ms");
		return ResponseVO.create(ResponseCode.SUCCESSFUL.getId(), ResponseCode.SUCCESSFUL.getName(), message,
				teacher.getTeacherId());
	}

	@Override
	public ResponseVO<String> addAttendance(AttendanceVO attendanceVO) {
		long startTime = System.nanoTime();
		TeacherAttendance teacherAttendance = null;
		TeacherAttendance savedTeacherAttendance = null;
		Teacher teacher = null;
		String message = null;

		boolean flag = false;
		if (!Objects.nonNull(attendanceVO)) {
			throw new RuntimeException(ResponseCode.INVALID_ARGUMENT.getName());
		}
		if (attendanceVO.getFiles() != null && !(attendanceVO.getFiles().length <= 0)) {
			for (MultipartFile uploadedFile : attendanceVO.getFiles()) {

				// Detect Face in Herta System
				DetectFacesResponseVO detectFacesResponseVO = detectFaces(uploadedFile);

				if (detectFacesResponseVO != null && StringUtils.isNotBlank(detectFacesResponseVO.getFaceImage())) {
					// Identify Face in Herta System
					IdentifyFacesResponseVO identifyFacesResponseVO = identifyFace(uploadedFile);
					if (identifyFacesResponseVO != null
							&& StringUtils
									.isNotBlank(identifyFacesResponseVO.getIdentifySubjectResult().get(0).getKey())
							&& identifyFacesResponseVO.getIdentifySubjectResult().get(0)
									.getValue() > identifySubjectRatio) {

						teacher = teacherRepository
								.findByTeacherId(identifyFacesResponseVO.getIdentifySubjectResult().get(0).getKey());
						if (teacher != null) {
							// attendanceVO.setFilePath(attendanceVO.getFilePath() +
							// uploadedFile.getOriginalFilename());
							teacherAttendance = new TeacherAttendance();
							BeanUtils.copyProperties(attendanceVO, teacherAttendance);
							teacherAttendance.setStatus(AttendanceStatus.PRESENT);
							teacherAttendance.setAttendanceDate(Utility.toDate(attendanceVO.getDate()));
//							teacherAttendance.setVideoLocation(attendanceVO.getFilePath());
							teacherAttendance.setIsProcessed(true);
							teacherAttendance.setAttendanceDate(new java.util.Date());
//							teacherAttendance.setVideoLabel(FilenameUtils.getBaseName(attendanceVO.getFilePath()));
							teacherAttendance.setPersonAccuracy(String
									.valueOf(identifyFacesResponseVO.getIdentifySubjectResult().get(0).getValue()));

							teacherAttendance.setTeacher(teacher);
							savedTeacherAttendance = teacherAttendanceRepository.save(teacherAttendance);

							final TeacherAttendance fTeacherAttendance = savedTeacherAttendance;
							final Teacher fTeacher = teacher;
							executor.submit(() -> notificationService.sendAttendanceMail(fTeacher, fTeacherAttendance));
							message = StringUtils.join("Your attendance submitted with id : ",
									savedTeacherAttendance.getTransactionId(), ", Latitude : ",
									savedTeacherAttendance.getLatitude(), " And Longitude : ",
									savedTeacherAttendance.getLongitude());
						} else {
							long stopTime = System.nanoTime();
							log.info("Exit from addAttendance Method at "
									+ TimeUnit.MILLISECONDS.convert(stopTime - startTime, TimeUnit.NANOSECONDS)
									+ " ms");
							return ResponseVO.create(ResponseCode.FAIL.getId(), ResponseCode.FAIL.getName(),
									"User Attendance Record Not Found !", StringUtils.EMPTY);
						}
					} else {
						long stopTime = System.nanoTime();
						log.info("Exit from addAttendance Method at "
								+ TimeUnit.MILLISECONDS.convert(stopTime - startTime, TimeUnit.NANOSECONDS) + " ms");
						return ResponseVO.create(ResponseCode.FAIL.getId(), ResponseCode.FAIL.getName(),
								"Unable to add user attendance because of identify subject ratio is less than :"
										+ identifySubjectRatio,
								StringUtils.EMPTY);
					}
				} else {
					long stopTime = System.nanoTime();
					log.info("Exit from addAttendance Method at "
							+ TimeUnit.MILLISECONDS.convert(stopTime - startTime, TimeUnit.NANOSECONDS) + " ms");
					return ResponseVO.create(ResponseCode.FAIL.getId(), ResponseCode.FAIL.getName(),
							"Unable to detect face !, Please try again", StringUtils.EMPTY);
				}
			}
			flag = true;
		}
		if (!flag) {
			throw new RuntimeException(ResponseCode.FAIL.getName());
		}
		long stopTime = System.nanoTime();
		log.info("Exit from addAttendance Method at "
				+ TimeUnit.MILLISECONDS.convert(stopTime - startTime, TimeUnit.NANOSECONDS) + " ms");
		return ResponseVO.create(ResponseCode.SUCCESSFUL.getId(), ResponseCode.SUCCESSFUL.getName(), message,
				StringUtils.EMPTY);
	}

	private String convertImageToBase64(MultipartFile files) {
		try {
			byte[] image = Base64.encodeBase64(files.getBytes());
			return new String(image, StandardCharsets.UTF_8);
		} catch (Exception exception) {
			log.error("Exception ", exception);
			return exception.getMessage();
		}
	}

	private DetectFacesResponseVO detectFaces(MultipartFile files) {
		DetectFacesWrapperVO detectFacesWrapperVO = new DetectFacesWrapperVO();
		DetectFacesSettingsWrapperVO detectFacesSettingsWrapperVO = new DetectFacesSettingsWrapperVO();
		String base64String = convertImageToBase64(files);
		detectFacesWrapperVO.setImage(base64String);
		// Set default setting
		detectFacesSettingsWrapperVO.setDetectionThreshold(faceDetectionThreshold);
		detectFacesSettingsWrapperVO.setQualityThreshold(faceQualityThreshold);
		detectFacesSettingsWrapperVO.setMinimumFaceSize(minimumFaceSize);
		detectFacesWrapperVO.setSettings(detectFacesSettingsWrapperVO);
		return hertaService.detectFaces(detectFacesWrapperVO);
	}

	private IdentifyFacesResponseVO identifyFace(MultipartFile files) {
		IdentifyFacesWrapperVO identifyFacesWrapperVO = new IdentifyFacesWrapperVO();
		ArrayList<String> images = new ArrayList<String>();
		String base64String = convertImageToBase64(files);
		images.add(base64String);
		identifyFacesWrapperVO.setImages(images);
		identifyFacesWrapperVO.setMaxNumberOfCandidates(1);
		return hertaService.identifyFace(identifyFacesWrapperVO);
	}

	private void enrollSubjectWithAlbum(TeacherVO teacherVO) {
		EnrollSubjectWithAlbumWrapperVO enrollSubjectWithAlbumWrapperVO = new EnrollSubjectWithAlbumWrapperVO();
		EnrollSubjectWithAlbumRequestRestVO enrollSubjectWithAlbumRequestRestVO = new EnrollSubjectWithAlbumRequestRestVO();
		enrollSubjectWithAlbumRequestRestVO.setSubjectCode(teacherVO.getEmployeeId());
		enrollSubjectWithAlbumRequestRestVO.setSubjectName(teacherVO.getFirstName());
		enrollSubjectWithAlbumRequestRestVO.setSubjectLastName(teacherVO.getLastName());
		String base64String = convertImageToBase64(teacherVO.getFiles()[0]);
		enrollSubjectWithAlbumRequestRestVO.setSubjectProfilePhoto(base64String);
		enrollSubjectWithAlbumRequestRestVO.setSubjectActive(true);
		enrollSubjectWithAlbumWrapperVO.setRequest(enrollSubjectWithAlbumRequestRestVO);
		hertaService.enrollSubjectWithAlbum(enrollSubjectWithAlbumWrapperVO);
	}
}