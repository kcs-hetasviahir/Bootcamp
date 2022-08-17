package com.kcs.attendancesystem.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;


@Getter
@Setter
@NoArgsConstructor
public class SSAClusterVO implements Serializable {
	
	private static final long serialVersionUID = 1L;

	private Long districtCode;
	private Long blockCode;
	private Long crcCode;
	private String crcName;
}