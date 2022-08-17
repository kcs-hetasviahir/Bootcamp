package com.kcs.attendancesystem.dto;

import lombok.Data;

@Data
public class ReportRequestVO {

	private Integer month;
	
	private Integer year;
	
	private Integer pageNo;
	
	private Integer pageSize;
}
