package com.kcs.attendancesystem.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;


@Getter
@Setter
@NoArgsConstructor
public class SSABlockVO implements Serializable {
	
	private static final long serialVersionUID = 1L;

	private Long blockCode;
	private String blockName;
}