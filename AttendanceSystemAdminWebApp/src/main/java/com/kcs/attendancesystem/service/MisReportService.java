package com.kcs.attendancesystem.service;

import java.util.List;

import com.kcs.attendancesystem.dto.MisReportVO;
import com.kcs.attendancesystem.dto.ReportRequestVO;
import com.kcs.attendancesystem.dto.ReportVO;
import com.kcs.attendancesystem.dto.ResponseVO;

public interface MisReportService {

	ResponseVO<List<ReportVO>> generateReport(ReportRequestVO reqVO);

	ResponseVO<MisReportVO> generateReport1(ReportRequestVO reqVO);

}
