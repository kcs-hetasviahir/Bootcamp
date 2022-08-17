package com.kcs.attendancesystem.dto;

import org.springframework.web.multipart.MultipartFile;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class AttendanceVO {

	private String date;
	
	private String status;
	
	private Boolean isHoliday;
	
	private String videoName;
	
	private String teacherId;
	
	private MultipartFile[] files;
	
	private String filePath;
	
	private String deviceId;
	
	private String latitude;
	
	private String longitude;
	
}
