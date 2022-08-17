package com.kcs.attendancesystem.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import com.kcs.attendancesystem.core.Designation;
import com.kcs.attendancesystem.core.SSABlock;
import com.kcs.attendancesystem.core.SSACluster;
import com.kcs.attendancesystem.core.SSADistrict;
import com.kcs.attendancesystem.core.SSASchool;
import com.kcs.attendancesystem.core.Teacher;
import com.kcs.attendancesystem.core.TeacherAttendance;
import com.kcs.attendancesystem.dto.ResponseVO;
import com.kcs.attendancesystem.dto.TeacherCountBlock;
import com.kcs.attendancesystem.dto.TeacherCountCluster;
import com.kcs.attendancesystem.dto.TeacherCountDist;
import com.kcs.attendancesystem.dto.TeacherCountSchool;
import com.kcs.attendancesystem.dto.TeacherCountTeacher;
import com.kcs.attendancesystem.dto.TeacherVO;
import com.kcs.attendancesystem.enums.ResponseCode;
import com.kcs.attendancesystem.repository.DesignationRepository;
import com.kcs.attendancesystem.repository.SSABlockRepository;
import com.kcs.attendancesystem.repository.SSAClustersRepository;
import com.kcs.attendancesystem.repository.SSADistrictRepository;
import com.kcs.attendancesystem.repository.SSASchoolRepository;
import com.kcs.attendancesystem.repository.TeacherAttendanceRepository;
import com.kcs.attendancesystem.repository.TeacherRepository;
import com.kcs.attendancesystem.service.TeacherService;
import com.kcs.attendancesystem.utils.Utils;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class TeacherServiceImpl implements TeacherService {

	@Autowired
	private TeacherRepository teacherRepository;

	@Autowired
	private RegistrationServiceImpl registrationService;

	@Autowired
	private SSADistrictRepository ssaDistrictRepository;

	@Autowired
	private SSABlockRepository ssaBlockRepository;

	@Autowired
	private SSAClustersRepository ssaClustersRepository;

	@Autowired
	private SSASchoolRepository ssaSchoolRepository;

	@Autowired
	private DesignationRepository designationRepository;

	@Autowired
	private TeacherAttendanceRepository teacherAttendanceRepository;

	@Override
	public ResponseVO<Page<TeacherVO>> findTeachers(int pageNo, int pageSize) {
		try {
			PageRequest pageRequest = PageRequest.of(pageNo, pageSize);

			Page<Teacher> teacherPage = teacherRepository.findAll(pageRequest);

			Page<TeacherVO> teacherVOPage = teacherPage.map(this::convertToVO);

			return ResponseVO.create(ResponseCode.SUCCESSFUL.getId(), ResponseCode.SUCCESSFUL.getName(),
					ResponseCode.SUCCESSFUL.getName(), teacherVOPage);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			return ResponseVO.create(ResponseCode.FAIL.getId(), ResponseCode.FAIL.getName(),
					ResponseCode.FAIL.getName(), null);
		}
	}

	private TeacherVO convertToVO(Teacher teacher) {
		TeacherVO teacherVO = new TeacherVO();
		SSADistrict ssaDistrict = new SSADistrict();
		SSABlock ssaBlock = new SSABlock();
		SSACluster ssaCluster = new SSACluster();
		SSASchool ssaSchool = new SSASchool();
		Designation designation = new Designation();
		teacherVO.setId(teacher.getId().toString());
		teacherVO.setEmployeeId(teacher.getTeacherId());
		teacherVO.setFirstName(teacher.getFirstName());
		teacherVO.setMiddleName(teacher.getMiddleName());
		teacherVO.setLastName(teacher.getLastName());
		teacherVO.setDateOfBirth(Utils.formatDate(teacher.getDateOfBirth()));
		teacherVO.setDateOfJoining(Utils.formatDate(teacher.getDateOfJoining()));
		teacherVO.setHouseName(teacher.getHouseName());
		teacherVO.setAddress1(teacher.getAddress1());
		teacherVO.setAddress2(teacher.getAddress2());
		teacherVO.setPincode(teacher.getPincode());
		teacherVO.setMobile(teacher.getMobileNumber());
		teacherVO.setEmail(teacher.getEmailAddress());
		// teacherVO.setPostingLocation(Objects.nonNull(teacher.getPostingLocation()) ?
		// teacher.getPostingLocation().getName() : StringUtils.EMPTY);
		// teacherVO.setInstitutionName(Objects.nonNull(teacher.getInstitution()) ?
		// teacher.getInstitution().getName() : StringUtils.EMPTY);
		teacherVO.setSuperivorId(
				Objects.nonNull(teacher.getSupervisorId()) ? teacher.getSupervisorId().toString() : StringUtils.EMPTY);
		teacherVO.setFullName(generateFullName(teacher));
		teacherVO.setDistrict(
				Objects.nonNull(teacher.getDistrict()) ? teacher.getDistrict().getDistrictName() : StringUtils.EMPTY);
		teacherVO.setTaluka(
				Objects.nonNull(teacher.getTaluka()) ? teacher.getTaluka().getTalukaName() : StringUtils.EMPTY);
		teacherVO.setVillage(
				Objects.nonNull(teacher.getVillage()) ? teacher.getVillage().getVillageName() : StringUtils.EMPTY);
		teacherVO.setRegistrationId(teacher.getRegistrationId());
		teacherVO.setGender(teacher.getGender());
		teacherVO.setFullAddress(generateFullAddress(teacher));

		if (Objects.nonNull(teacher.getSsaDistrictCode())) {
			ssaDistrict = ssaDistrictRepository.findByDistrictCode(teacher.getSsaDistrictCode());
			if (ssaDistrict != null) {
				teacherVO.setSsaDistrict(ssaDistrict.getDistrictName());
			}
		}

		if (Objects.nonNull(teacher.getSsaBlockCode())) {
			ssaBlock = ssaBlockRepository.findByBlockCode(teacher.getSsaBlockCode());
			if (ssaBlock != null) {
				teacherVO.setSsaBlock(ssaBlock.getBlockName());
			}
		}

		if (Objects.nonNull(teacher.getSsaClusterCode())) {
			ssaCluster = ssaClustersRepository.findByClusterCode(teacher.getSsaClusterCode());
			if (ssaCluster != null) {
				teacherVO.setSsaCluster(ssaCluster.getCrcName());
			}
		}

		if (Objects.nonNull(teacher.getSsaSchoolCode())) {
			ssaSchool = ssaSchoolRepository.findBySchoolCode(teacher.getSsaSchoolCode());
			if (ssaSchool != null) {
				teacherVO.setSsaSchool(ssaSchool.getSchoolName());
			}
		}

		if (Objects.nonNull(teacher.getDesignation())) {
			designation = designationRepository.findByDesignationId(Long.parseLong(teacher.getDesignation()));
			if (designation != null) {
				teacherVO.setDesignation(designation.getDesignationName());
			}
		}

		if (Objects.nonNull(teacher.getSupervisorId())) {
			teacher = teacherRepository.findByUserId(teacher.getSupervisorId());
			if (teacher != null) {
				teacherVO.setSuperivorName(generateFullName(teacher));
			}
		}

		return teacherVO;
	}

	@Override
	public ResponseVO<List<TeacherVO>> findAll() {
		List<Teacher> teachers = teacherRepository.findAll();
		return ResponseVO.create(200, teachers.stream().map(this::convertToVO).collect(Collectors.toList()));
	}

	@Override
	public ResponseVO<Teacher> updateTeacher(TeacherVO vo) {
		Optional<Teacher> teacherOpt = teacherRepository.findById(Long.parseLong(vo.getId()));
		if (teacherOpt.isPresent()) {
			Teacher teacher = teacherOpt.get();
			BeanUtils.copyProperties(vo, teacher);
			teacher.setEmailAddress(vo.getEmail());
			Teacher savedTeacher = teacherRepository.save(teacher);
			return ResponseVO.create(200, savedTeacher);
		} else {
			return ResponseVO.create(ResponseCode.FAIL.getId(), ResponseCode.FAIL.getName(),
					ResponseCode.FAIL.getName(), null);
		}
	}

	@Override
	public ResponseVO<Void> deleteTeacher(Long teacherId) {
		try {
			Optional<Teacher> teacherOpt = teacherRepository.findById(teacherId);
			List<TeacherAttendance> teacherAttendances = teacherAttendanceRepository.findByTeacher(teacherId);

			if (teacherOpt.isPresent()) {
				teacherAttendanceRepository.deleteAll(teacherAttendances);
				teacherRepository.delete(teacherOpt.get());

				return ResponseVO.create(200, "Teacher deleted successfully");
			}
			return ResponseVO.create(400, "Teacher with provide id not exist");
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			return ResponseVO.create(500, "Error in deleting Teacher");
		}
	}

	@Override
	public ResponseVO<TeacherVO> findTeacher(Long teacherId) {
		try {
			Teacher teacher = new Teacher();
			TeacherVO teacherVO = new TeacherVO();
			teacher = teacherRepository.findByUserId(teacherId);
			teacherVO = convertToVO(teacher);
			return ResponseVO.create(ResponseCode.SUCCESSFUL.getId(), ResponseCode.SUCCESSFUL.getName(),
					ResponseCode.SUCCESSFUL.getName(), teacherVO);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			return ResponseVO.create(ResponseCode.FAIL.getId(), ResponseCode.FAIL.getName(),
					ResponseCode.FAIL.getName(), null);
		}
	}

	@Override
	public ResponseVO<List<TeacherVO>> searchTeachers(String teacherName) {
		try {

			List<Teacher> teacherList = teacherRepository.searchTeacherByName(teacherName);

			List<TeacherVO> teacherVOS = new ArrayList<>(teacherList.size());

			teacherList.stream().forEach(x -> {
				TeacherVO dto = new TeacherVO();
				BeanUtils.copyProperties(x, dto);

				teacherVOS.add(dto);
			});
			return ResponseVO.create(ResponseCode.SUCCESSFUL.getId(), ResponseCode.SUCCESSFUL.getName(),
					ResponseCode.SUCCESSFUL.getName(), teacherVOS);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			return ResponseVO.create(ResponseCode.FAIL.getId(), ResponseCode.FAIL.getName(),
					ResponseCode.FAIL.getName(), null);
		}
	}

	@Override
	public ResponseVO<Long> countAll() {
		Long totalCount = teacherRepository.count();
		return ResponseVO.create(ResponseCode.SUCCESSFUL.getId(), ResponseCode.SUCCESSFUL.getName(),
				ResponseCode.SUCCESSFUL.getName(), totalCount);
	}

	private String generateFullName(Teacher teacher) {
		return StringUtils.join(StringUtils.defaultIfBlank(teacher.getFirstName(), ""), " ",
				StringUtils.defaultIfBlank(teacher.getMiddleName(), ""), " ",
				StringUtils.defaultIfBlank(teacher.getLastName(), ""));
	}

	private String generateFullAddress(Teacher teacher) {
		return StringUtils.join(StringUtils.defaultIfBlank(teacher.getHouseName(), ""), " ",
				StringUtils.defaultIfBlank(teacher.getAddress1(), ""), " ",
				StringUtils.defaultIfBlank(teacher.getAddress2(), ""), " ",
				StringUtils.defaultIfBlank(teacher.getPincode(), ""));
	}

	@Override
	public ResponseVO<List<TeacherCountBlock>> findByDistrict(Long districtCode) {

		try {

			List<TeacherCountBlock> list = new ArrayList<>();

			List<SSABlock> blocks = ssaBlockRepository.findByDistrictId(districtCode);
			for (SSABlock ssaBlock : blocks) {

				List<Teacher> teachers = teacherRepository.findAllBySsaBlockCode(ssaBlock.getBlockCode());

				TeacherCountBlock teacherCountBlock = new TeacherCountBlock();

				teacherCountBlock.setDistrictCode(districtCode);
				;
				teacherCountBlock.setBlockCode(ssaBlock.getBlockCode());
				teacherCountBlock.setTeacherCount(Long.valueOf(teachers.size()));
				teacherCountBlock.setBlockName(ssaBlock.getBlockName());
				list.add(teacherCountBlock);

			}
			return ResponseVO.create(ResponseCode.SUCCESSFUL.getId(), ResponseCode.SUCCESSFUL.getName(),
					ResponseCode.SUCCESSFUL.getName(), list);

		} catch (Exception e) {

			log.error(e.getMessage(), e);
			return ResponseVO.create(ResponseCode.FAIL.getId(), ResponseCode.FAIL.getName(),
					ResponseCode.FAIL.getName(), null);
		}

	}

	@Override
	public ResponseVO<List<TeacherCountCluster>> findBySsaBlockCode(Long districtCode, Long blockCode) {

		try {
			List<TeacherCountCluster> list = new ArrayList<>();

			List<SSACluster> clusters = ssaClustersRepository.findByDistrictIdAndBlockId(districtCode, blockCode);

			for (SSACluster ssaCluster : clusters) {

				List<Teacher> teachers = teacherRepository.findAllBySsaClusterCode(ssaCluster.getCrcCode());

				TeacherCountCluster cluster = new TeacherCountCluster();

				cluster.setDistrictCode(districtCode);
				cluster.setBlockCode(blockCode);

				cluster.setClusterCode(ssaCluster.getCrcCode());
				cluster.setClusterName(ssaCluster.getCrcName());
				cluster.setTeacherCount(Long.valueOf(teachers.size()));

				list.add(cluster);

			}

			return ResponseVO.create(ResponseCode.SUCCESSFUL.getId(), ResponseCode.SUCCESSFUL.getName(),
					ResponseCode.SUCCESSFUL.getName(), list);

		} catch (Exception e) {
			log.error(e.getMessage(), e);
			return ResponseVO.create(ResponseCode.FAIL.getId(), ResponseCode.FAIL.getName(),
					ResponseCode.FAIL.getName(), null);

		}

	}

	@Override
	public ResponseVO<List<TeacherCountSchool>> findByCluster(Long distid, Long blockid, Long crcid) {

		try {

			List<TeacherCountSchool> list = new ArrayList<>();

			List<SSASchool> schools = ssaSchoolRepository.findByDistIdBlockIdClusterId(distid, blockid, crcid);

			for (SSASchool ssaSchool : schools) {

				List<Teacher> teachers = teacherRepository.findAllBySsaSchoolCode(ssaSchool.getSchoolCode());

				TeacherCountSchool teacherCountSchool = new TeacherCountSchool();

				teacherCountSchool.setSchoolCode(ssaSchool.getSchoolCode());
				teacherCountSchool.setSchoolName(ssaSchool.getSchoolName());
				teacherCountSchool.setTeacherCount(Long.valueOf(teachers.size()));
				list.add(teacherCountSchool);
			}
			return ResponseVO.create(ResponseCode.SUCCESSFUL.getId(), ResponseCode.SUCCESSFUL.getName(),
					ResponseCode.SUCCESSFUL.getName(), list);

		} catch (Exception e) {
			log.error(e.getMessage(), e);
			return ResponseVO.create(ResponseCode.FAIL.getId(), ResponseCode.FAIL.getName(),
					ResponseCode.FAIL.getName(), null);

		}
	}

	@Override
	public ResponseVO<List<TeacherCountTeacher>> findBySsaSchoolCode(Long distid, Long blockid, Long crcid,
			Long schoolid) {

		try {

			List<TeacherCountTeacher> list = new ArrayList<>();

			List<SSASchool> schools = ssaSchoolRepository.findByDistIdBlockIdClusterId(distid, blockid, crcid);

			for (SSASchool ssaSchool : schools) {

				if (ssaSchool.getSchoolCode().equals(schoolid)) {

					TeacherCountTeacher teacherCountTeacher = new TeacherCountTeacher();

					List<Teacher> teachers = teacherRepository.findAllBySsaSchoolCode(schoolid);
					teacherCountTeacher.setSchoolCode(schoolid);
					teacherCountTeacher.setSchoolName(ssaSchool.getSchoolName());
					teacherCountTeacher.setTeacherCount(Long.valueOf(teachers.size()));

					list.add(teacherCountTeacher);

				}
			}
			return ResponseVO.create(ResponseCode.SUCCESSFUL.getId(), ResponseCode.SUCCESSFUL.getName(),
					ResponseCode.SUCCESSFUL.getName(), list);

		} catch (Exception e) {

			log.error(e.getMessage(), e);
			return ResponseVO.create(ResponseCode.FAIL.getId(), ResponseCode.FAIL.getName(),
					ResponseCode.FAIL.getName(), null);

		}

	}

	@Override
	public ResponseVO<List<TeacherCountDist>> allData() {

		try {
			List<TeacherCountDist> list = new ArrayList<>();

			List<SSADistrict> districts = ssaDistrictRepository.findAll();

			System.out.println(teacherRepository.findAll().size());

			for (SSADistrict ssaDistrict : districts) {

				List<Teacher> teachers = teacherRepository.findAllBySsaDistrictCode(ssaDistrict.getDistrictCode());

				TeacherCountDist teacherCountDist = new TeacherCountDist();

				teacherCountDist.setDistrictName(ssaDistrict.getDistrictName());
				teacherCountDist.setDistrictCode(ssaDistrict.getDistrictCode());
				teacherCountDist.setTeacherCount(Long.valueOf(teachers.size()));

				list.add(teacherCountDist);

			}

			return ResponseVO.create(ResponseCode.SUCCESSFUL.getId(), ResponseCode.SUCCESSFUL.getName(),
					ResponseCode.SUCCESSFUL.getName(), list);

		} catch (Exception e) {
			log.error(e.getMessage(), e);
			return ResponseVO.create(ResponseCode.FAIL.getId(), ResponseCode.FAIL.getName(),
					ResponseCode.FAIL.getName(), null);

		}

	}

}
