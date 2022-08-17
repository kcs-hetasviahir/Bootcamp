package com.kcs.attendancesystem.dto;

import java.io.Serializable;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
public class SupervisorVO implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private Long id;
	private String supervisorName;
}