package com.kcs.attendancesystem.dto;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MisReportVO {

	private Integer pageNo;
	
	private Integer pageSize;
	
	private Long totalElements;
	
	private List<ReportVO> data;
	
}
