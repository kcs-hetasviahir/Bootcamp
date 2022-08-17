package com.kcs.attendancesystem.service;

import java.util.List;

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
import com.kcs.attendancesystem.dto.TalukaVO;
import com.kcs.attendancesystem.dto.VillageVO;

public interface MasterCodeService {

	ResponseVO<List<CityVO>> getCitys();
	
	ResponseVO<List<StateVO>> getStates();
	
	ResponseVO<List<InstitutionVO>> getInstitutions();
	
	ResponseVO<List<SupervisorVO>> getSupervisors();

	ResponseVO<List<DistrictVO>> getDistricts();

	ResponseVO<List<TalukaVO>> getTalukas(Long distId);

	ResponseVO<List<VillageVO>> getVillages(Long talukaId);

	ResponseVO<List<SSADistrictVO>> getSSADistricts();

	ResponseVO<List<SSABlockVO>> getSSABlocks(Long distId);

	ResponseVO<List<SSAClusterVO>> getSSAClusters(Long distId, Long blockId);

	ResponseVO<List<SSASchoolVO>> getSSASchools(Long distId, Long blockId, Long crcId);

	ResponseVO<List<DesignationVO>> getDesignations();

}
