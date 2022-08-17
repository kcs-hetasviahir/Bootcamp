package com.kcs.attendancesystem.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kcs.attendancesystem.core.City;
import com.kcs.attendancesystem.core.Designation;
import com.kcs.attendancesystem.core.District;
import com.kcs.attendancesystem.core.Institution;
import com.kcs.attendancesystem.core.SSABlock;
import com.kcs.attendancesystem.core.SSACluster;
import com.kcs.attendancesystem.core.SSADistrict;
import com.kcs.attendancesystem.core.SSASchool;
import com.kcs.attendancesystem.core.State;
import com.kcs.attendancesystem.core.Supervisor;
import com.kcs.attendancesystem.core.Taluka;
import com.kcs.attendancesystem.core.Village;
import com.kcs.attendancesystem.dto.CityVO;
import com.kcs.attendancesystem.dto.DesignationVO;
import com.kcs.attendancesystem.dto.DistrictVO;
import com.kcs.attendancesystem.dto.InstitutionVO;
import com.kcs.attendancesystem.dto.ResponseVO;
import com.kcs.attendancesystem.dto.SSABlockVO;
import com.kcs.attendancesystem.dto.SSAClusterVO;
import com.kcs.attendancesystem.dto.SSADistrictVO;
import com.kcs.attendancesystem.dto.SSASchoolVO;
import com.kcs.attendancesystem.dto.StateVO;
import com.kcs.attendancesystem.dto.SupervisorVO;
import com.kcs.attendancesystem.enums.ResponseCode;
import com.kcs.attendancesystem.dto.TalukaVO;
import com.kcs.attendancesystem.dto.VillageVO;
import com.kcs.attendancesystem.repository.CityRepository;
import com.kcs.attendancesystem.repository.DesignationRepository;
import com.kcs.attendancesystem.repository.DistrictRepository;
import com.kcs.attendancesystem.repository.InstitutionRepository;
import com.kcs.attendancesystem.repository.SSABlockRepository;
import com.kcs.attendancesystem.repository.SSAClustersRepository;
import com.kcs.attendancesystem.repository.SSADistrictRepository;
import com.kcs.attendancesystem.repository.SSASchoolRepository;
import com.kcs.attendancesystem.repository.StateRepository;
import com.kcs.attendancesystem.repository.SupervisorRepository;
import com.kcs.attendancesystem.repository.TalukaRepository;
import com.kcs.attendancesystem.repository.VillageRepository;
import com.kcs.attendancesystem.service.MasterCodeService;

@Service
public class MasterCodeServiceImpl implements MasterCodeService {

	@Autowired
	private CityRepository cityRepository;

	@Autowired
	private StateRepository stateRepository;

	@Autowired
	private InstitutionRepository institutionRepository;

	@Autowired
	private SupervisorRepository supervisorRepository;

	@Autowired
	private DistrictRepository districtRepository;

	@Autowired
	private TalukaRepository talukaRepository;

	@Autowired
	private VillageRepository villageRepository;

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

	@Override
	public ResponseVO<List<CityVO>> getCitys() {
		List<City> citys = cityRepository.findAll();

		List<CityVO> cityVOs = new ArrayList<>(citys.size());

		citys.stream().forEach(x -> {
			CityVO dto = new CityVO();
			BeanUtils.copyProperties(x, dto);
			dto.setStateId(Objects.nonNull(x.getState())?x.getState().getId():0);
			cityVOs.add(dto);
		});
		return ResponseVO.create(ResponseCode.SUCCESSFUL.getId(), ResponseCode.SUCCESSFUL.getName(),
				ResponseCode.SUCCESSFUL.getName(), cityVOs);
	}

	@Override
	public ResponseVO<List<StateVO>> getStates() {
		List<State> states = stateRepository.findAll();

		List<StateVO> stateVOs = new ArrayList<>(states.size());

		states.stream().forEach(x -> {
			StateVO dto = new StateVO();
			BeanUtils.copyProperties(x, dto);

			stateVOs.add(dto);
		});
		return ResponseVO.create(ResponseCode.SUCCESSFUL.getId(), ResponseCode.SUCCESSFUL.getName(),
				ResponseCode.SUCCESSFUL.getName(), stateVOs);
	}

	@Override
	public ResponseVO<List<InstitutionVO>> getInstitutions() {
		List<Institution> institutions = institutionRepository.findAll();

		List<InstitutionVO> institutionVOs = new ArrayList<>(institutions.size());

		institutions.stream().forEach(x -> {
			InstitutionVO dto = new InstitutionVO();
			BeanUtils.copyProperties(x, dto);
			dto.setCity(Objects.nonNull(x.getCity())?x.getCity().getId():0);
			dto.setUserId(Objects.nonNull(x.getUser())?x.getUser().getId():0);
			dto.setCityName(Objects.nonNull(x.getCity())?x.getCity().getName():"");
			institutionVOs.add(dto);
		});
		return ResponseVO.create(ResponseCode.SUCCESSFUL.getId(), ResponseCode.SUCCESSFUL.getName(),
				ResponseCode.SUCCESSFUL.getName(), institutionVOs);
	}

	@Override
	public ResponseVO<List<SupervisorVO>> getSupervisors() {
		List<Supervisor> supervisors = supervisorRepository.findAll();

		List<SupervisorVO> supervisorVOs = new ArrayList<>(supervisors.size());

		supervisors.stream().forEach(x -> {
			SupervisorVO dto = new SupervisorVO();
			BeanUtils.copyProperties(x, dto);

			supervisorVOs.add(dto);
		});
		return ResponseVO.create(ResponseCode.SUCCESSFUL.getId(), ResponseCode.SUCCESSFUL.getName(),
				ResponseCode.SUCCESSFUL.getName(), supervisorVOs);
	}

	@Override
	public ResponseVO<List<DistrictVO>> getDistricts() {
		List<District> districts = districtRepository.findAll();

		List<DistrictVO> districtVOs = new ArrayList<>(districts.size());

		districts.stream().forEach(x -> {
			DistrictVO dto = new DistrictVO();
			BeanUtils.copyProperties(x, dto);

			districtVOs.add(dto);
		});
		return ResponseVO.create(ResponseCode.SUCCESSFUL.getId(), ResponseCode.SUCCESSFUL.getName(),
				ResponseCode.SUCCESSFUL.getName(), districtVOs);
	}

	@Override
	public ResponseVO<List<TalukaVO>> getTalukas(Long distId) {
		List<Taluka> talukas = talukaRepository.findByDistrictId(distId);

		List<TalukaVO> talukaVOs = new ArrayList<>(talukas.size());

		talukas.stream().forEach(x -> {
			TalukaVO dto = new TalukaVO();
			BeanUtils.copyProperties(x, dto);

			talukaVOs.add(dto);
		});
		return ResponseVO.create(ResponseCode.SUCCESSFUL.getId(), ResponseCode.SUCCESSFUL.getName(),
				ResponseCode.SUCCESSFUL.getName(), talukaVOs);
	}

	@Override
	public ResponseVO<List<VillageVO>> getVillages(Long talukaId) {
		List<Village> villages = villageRepository.findByTalukaId(talukaId);

		List<VillageVO> villageVOs = new ArrayList<>(villages.size());

		villages.stream().forEach(x -> {
			VillageVO dto = new VillageVO();
			BeanUtils.copyProperties(x, dto);

			villageVOs.add(dto);
		});
		return ResponseVO.create(ResponseCode.SUCCESSFUL.getId(), ResponseCode.SUCCESSFUL.getName(),
				ResponseCode.SUCCESSFUL.getName(), villageVOs);
	}

	@Override
	public ResponseVO<List<SSADistrictVO>> getSSADistricts() {
		List<SSADistrict> ssaDistricts = ssaDistrictRepository.findAll();

		List<SSADistrictVO> districtVOs = new ArrayList<>(ssaDistricts.size());

		ssaDistricts.stream().forEach(x -> {
			SSADistrictVO dto = new SSADistrictVO();
			BeanUtils.copyProperties(x, dto);

			districtVOs.add(dto);
		});
		return ResponseVO.create(ResponseCode.SUCCESSFUL.getId(), ResponseCode.SUCCESSFUL.getName(),
				ResponseCode.SUCCESSFUL.getName(), districtVOs);
	}

	@Override
	public ResponseVO<List<SSABlockVO>> getSSABlocks(Long distId) {
		List<SSABlock> ssaBlocks = ssaBlockRepository.findByDistrictId(distId);

		List<SSABlockVO> ssaBlockVOs = new ArrayList<>(ssaBlocks.size());

		ssaBlocks.stream().forEach(x -> {
			SSABlockVO dto = new SSABlockVO();
			BeanUtils.copyProperties(x, dto);

			ssaBlockVOs.add(dto);
		});
		return ResponseVO.create(ResponseCode.SUCCESSFUL.getId(), ResponseCode.SUCCESSFUL.getName(),
				ResponseCode.SUCCESSFUL.getName(), ssaBlockVOs);
	}


	@Override
	public ResponseVO<List<SSAClusterVO>> getSSAClusters(Long distId, Long blockId) {
		List<SSACluster> ssaClusters = ssaClustersRepository.findByDistrictIdAndBlockId(distId, blockId);

		List<SSAClusterVO> ssaClusterVOS = new ArrayList<>(ssaClusters.size());

		ssaClusters.forEach(x -> {
			SSAClusterVO dto = new SSAClusterVO();
			BeanUtils.copyProperties(x, dto);

			ssaClusterVOS.add(dto);
		});
		return ResponseVO.create(ResponseCode.SUCCESSFUL.getId(), ResponseCode.SUCCESSFUL.getName(),
				ResponseCode.SUCCESSFUL.getName(), ssaClusterVOS);
	}

	@Override
	public ResponseVO<List<SSASchoolVO>> getSSASchools(Long distId, Long blockId, Long crcId) {
		List<SSASchool> ssaSchools = ssaSchoolRepository.findByDistIdBlockIdClusterId(distId, blockId, crcId);

		List<SSASchoolVO> ssaSchoolVOs = new ArrayList<>(ssaSchools.size());

		ssaSchools.stream().forEach(x -> {
			SSASchoolVO dto = new SSASchoolVO();
			BeanUtils.copyProperties(x, dto);

			ssaSchoolVOs.add(dto);
		});
		return ResponseVO.create(ResponseCode.SUCCESSFUL.getId(), ResponseCode.SUCCESSFUL.getName(),
				ResponseCode.SUCCESSFUL.getName(), ssaSchoolVOs);
	}

	@Override
	public ResponseVO<List<DesignationVO>> getDesignations() {
		List<Designation> designations = designationRepository.findAll();

		List<DesignationVO> designationVOS = new ArrayList<>(designations.size());

		designations.stream().forEach(x -> {
			DesignationVO dto = new DesignationVO();
			BeanUtils.copyProperties(x, dto);

			designationVOS.add(dto);
		});
		return ResponseVO.create(ResponseCode.SUCCESSFUL.getId(), ResponseCode.SUCCESSFUL.getName(),
				ResponseCode.SUCCESSFUL.getName(), designationVOS);
	}
}