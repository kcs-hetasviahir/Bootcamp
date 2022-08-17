package com.kcs.attendancesystem.controller;

import java.util.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.kcs.attendancesystem.dto.IotServiceVO;
import com.kcs.attendancesystem.dto.ResponseVO;
import com.kcs.attendancesystem.dto.ServiceStatusVO;
import com.kcs.attendancesystem.enums.ResponseCode;
import com.kcs.attendancesystem.service.IOTCommnadService;

@CommonRestController
public class IOTCommandController {
	
	private ExecutorService executor = Executors.newSingleThreadExecutor();
	
	@Autowired
	private IOTCommnadService iotCommnadService;
	
	@GetMapping("/register/status")
	public ResponseVO<ServiceStatusVO> registerStatus() {
		return iotCommnadService.checkRegisterServiceStatus();
	}
	
	@GetMapping("/attendance/status")
	public ResponseVO<ServiceStatusVO> attendanceStatus() {
		return iotCommnadService.checkAttendanceServiceStatus();
	}
	
	@GetMapping("/register/start")
	public ResponseVO<String> startRegisterService() {
		return iotCommnadService.startRegisterService();
	}
	
	@GetMapping("/attendance/start")
	public ResponseVO<String> startAttendanceService() {
		return iotCommnadService.startAttendanceService();
	}
	
	@PostMapping("/iot-service/status")
	public ResponseVO<String> iotServiceStatus(@RequestBody IotServiceVO vo) {
		executor.submit(() -> iotCommnadService.callAndExecuteProcess());
		return ResponseVO.create(ResponseCode.SUCCESSFUL.getId(), ResponseCode.SUCCESSFUL.getName(), "IOT service status updated successfully", new Date().toString());
	}
}
