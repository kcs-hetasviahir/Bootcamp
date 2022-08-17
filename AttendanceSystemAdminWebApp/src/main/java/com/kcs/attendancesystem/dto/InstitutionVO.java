package com.kcs.attendancesystem.dto;

import java.io.Serializable;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class InstitutionVO implements Serializable {
	private static final long serialVersionUID = 1L;

	private Long id;
	private String description;
	private String deviceName;
	private String deviceUniqueId;
	private String name;
	private Long city;
	private Long userId;
	private String cityName;
}