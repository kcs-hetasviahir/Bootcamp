package com.kcs.attendancesystem.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.kcs.attendancesystem.dto.MisReportVO;
import com.kcs.attendancesystem.dto.ReportRequestVO;
import com.kcs.attendancesystem.dto.ReportVO;
import com.kcs.attendancesystem.dto.ResponseVO;
import com.kcs.attendancesystem.service.MisReportService;

@BaseRestController
public class MisReportController {

	@Autowired
	private MisReportService misReportService;
	
	@PostMapping("/mis-report-old")
	public ResponseVO<List<ReportVO>> generateReport(@RequestBody ReportRequestVO reqVO) {
		return misReportService.generateReport(reqVO);
	}
	
	@PostMapping("/mis-report")
	public ResponseVO<MisReportVO> generateReport1(@RequestBody ReportRequestVO reqVO) {
		return misReportService.generateReport1(reqVO);
	}
}
