package com.kcs.attendancesystem.enums;

import java.util.Arrays;

import org.apache.commons.lang3.StringUtils;

public enum AttendanceStatus {

	PRESENT("PRESENT"), 
	ABSENT("ABSENT");

	private final String name;

	private AttendanceStatus(String value) {
		this.name = value;
	}

	public String value() {
		return this.name;
	}

	public static AttendanceStatus from(String value) {
		return Arrays.stream(AttendanceStatus.values()).filter(e -> StringUtils.equalsIgnoreCase(e.name(), value)).findFirst()
				.orElse(null);
	}

	@Override
	public String toString() {
		return name;
	}
}
