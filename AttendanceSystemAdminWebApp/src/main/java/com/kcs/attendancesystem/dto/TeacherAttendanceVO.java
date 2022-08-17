package com.kcs.attendancesystem.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TeacherAttendanceVO {

	private Long id;
	
	private String teacherName;
	
	private String attendanceDate;
	
	private String attendanceStatus;
	
	private String videoName;
	
	private Boolean isProcessed;
	
	private String processedStatus;
	
	private String videoLabel;
	
	private String processStartTime;
	
	private String processEndTime;
	
	private Integer totalTime;
	
	private String accuracy;

	private String latitude;

	private String longitude;

	private String createdDate;

	private String updatedDate;

	private String transactionId;

	private String deviceId;
}
