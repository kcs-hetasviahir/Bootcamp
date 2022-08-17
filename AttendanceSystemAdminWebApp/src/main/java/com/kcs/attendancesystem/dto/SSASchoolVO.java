package com.kcs.attendancesystem.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;


@Getter
@Setter
@NoArgsConstructor
public class SSASchoolVO implements Serializable {
	
	private static final long serialVersionUID = 1L;

	private Long districtCode;
	private Long blockId;
	private Long crcCode;
	private Long schoolCode;
	private String schoolName;
	private String schoolLatitude;
	private String schoolLongitude;
}