package com.kcs.attendancesystem.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;

import com.kcs.attendancesystem.dto.CityVO;
import com.kcs.attendancesystem.dto.DesignationVO;
import com.kcs.attendancesystem.dto.DistrictVO;
import com.kcs.attendancesystem.dto.InstitutionVO;
import com.kcs.attendancesystem.dto.MisReportVO;
import com.kcs.attendancesystem.dto.ReportRequestVO;
import com.kcs.attendancesystem.dto.ResponseVO;
import com.kcs.attendancesystem.dto.SSABlockVO;
import com.kcs.attendancesystem.dto.SSAClusterVO;
import com.kcs.attendancesystem.dto.SSADistrictVO;
import com.kcs.attendancesystem.dto.SSASchoolVO;
import com.kcs.attendancesystem.dto.StateVO;
import com.kcs.attendancesystem.dto.SupervisorVO;
import com.kcs.attendancesystem.dto.TalukaVO;
import com.kcs.attendancesystem.dto.VillageVO;
import com.kcs.attendancesystem.service.MisReportService;
import com.kcs.attendancesystem.service.MasterCodeService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@CommonRestController
public class MasterCodeController {

	@Autowired
	private MasterCodeService masterCodeService;

	@Autowired
	private MisReportService misReportService;
	
	@GetMapping("/city")
	public ResponseVO<List<CityVO>> getCitys() {
		return masterCodeService.getCitys();
	}	
	
	@GetMapping("/state")
	public ResponseVO<List<StateVO>> getStates() {
		return masterCodeService.getStates();
	}	
	
	@GetMapping("/institution")
	public ResponseVO<List<InstitutionVO>> getInstitutions() {
		return masterCodeService.getInstitutions();
	}	
	
	@GetMapping("/supervisor")
	public ResponseVO<List<SupervisorVO>> getSupervisors() {
		return masterCodeService.getSupervisors();
	}

	@GetMapping("/districts")
	public ResponseVO<List<DistrictVO>> getDistricts() {
		return masterCodeService.getDistricts();
	}

	@GetMapping("/talukas")
	public ResponseVO<List<TalukaVO>> getTalukas(@RequestParam("distId") Long distId) {
		return masterCodeService.getTalukas(distId);
	}

	@GetMapping("/villages")
	public ResponseVO<List<VillageVO>> getVillages(@RequestParam("talukaId") Long talukaId) {
		return masterCodeService.getVillages(talukaId);
	}

	@GetMapping("/ssaDistricts")
	public ResponseVO<List<SSADistrictVO>> getSSADistricts() {
		return masterCodeService.getSSADistricts();
	}

	@GetMapping("/ssaBlocks")
	public ResponseVO<List<SSABlockVO>> getSSABlocks(@RequestParam("distId") Long distId) {
		return masterCodeService.getSSABlocks(distId);
	}

	@GetMapping("/ssaClusters")
	public ResponseVO<List<SSAClusterVO>> getSSAClusters(@RequestParam("distId") Long distId,
														 @RequestParam("blockId") Long blockId) {
		return masterCodeService.getSSAClusters(distId, blockId);
	}

	@GetMapping("/ssaSchools")
	public ResponseVO<List<SSASchoolVO>> getSSASchools(@RequestParam("distId") Long distId,
													   @RequestParam("blockId") Long blockId,
													   @RequestParam("crcId") Long crcId) {
		return masterCodeService.getSSASchools(distId, blockId, crcId);
	}

	@GetMapping("/designation")
	public ResponseVO<List<DesignationVO>> getDesignations() {
		return masterCodeService.getDesignations();
	}

	@PostMapping("/mis-report-mobile")
	public ResponseVO<MisReportVO> generateReportForMobile(@RequestBody ReportRequestVO reqVO) {
		return misReportService.generateReport1(reqVO);
	}
}
