package com.kcs.attendancesystem.service.impl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.kcs.attendancesystem.core.ServiceStatus;
import com.kcs.attendancesystem.core.Teacher;
import com.kcs.attendancesystem.core.TeacherAttendance;
import com.kcs.attendancesystem.dto.ResponseVO;
import com.kcs.attendancesystem.dto.ServiceStatusVO;
import com.kcs.attendancesystem.enums.ResponseCode;
import com.kcs.attendancesystem.repository.ServiceStatusRepository;
import com.kcs.attendancesystem.repository.TeacherAttendanceRepository;
import com.kcs.attendancesystem.repository.TeacherRepository;
import com.kcs.attendancesystem.service.IOTCommnadService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class IOTCommnadServiceImpl implements IOTCommnadService {
	
	@Autowired
	private ServiceStatusRepository serviceStatusRepository;
	
	@Autowired
	private TeacherRepository teacherRepository;
	
	@Autowired
	private TeacherAttendanceRepository teacherAttendanceRepository;

	@Override
	public ResponseVO<ServiceStatusVO> checkRegisterServiceStatus() {
		try {
			return getServiceStatus("Register");			
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			return ResponseVO.create(ResponseCode.FAIL.getId(), ResponseCode.FAIL.getName(), e.getMessage(), null);
		}
	}
	
	@Override
	public ResponseVO<ServiceStatusVO> checkAttendanceServiceStatus() {
		try {
			return getServiceStatus("Recognize");
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			return ResponseVO.create(ResponseCode.FAIL.getId(), ResponseCode.FAIL.getName(), e.getMessage(), null);
		}
	}

	private ResponseVO<ServiceStatusVO> getServiceStatus(String serviceName) {
		Optional<ServiceStatus> serviceStatusOpt = serviceStatusRepository
				.findTopByServiceNameOrderByIdDesc(serviceName);

		if (serviceStatusOpt.isPresent()) {
			ServiceStatus serviceStatus = serviceStatusOpt.get();
			return ResponseVO.create(ResponseCode.SUCCESSFUL.getId(), ResponseCode.SUCCESSFUL.getName(),
					serviceStatus.getStatus(), convertToVO(serviceStatus));
		}

		return ResponseVO.create(ResponseCode.SUCCESSFUL.getId(), ResponseCode.SUCCESSFUL.getName(),
				"No service found", null);
	}
	
	private ServiceStatusVO convertToVO(ServiceStatus serviceStatus) {
		ServiceStatusVO statusVO = new ServiceStatusVO();
		statusVO.setStartTime(serviceStatus.getStartTime());
		statusVO.setEndTime(serviceStatus.getEndTime());
		statusVO.setStatus(serviceStatus.getStatus());
		statusVO.setDescription(serviceStatus.getDescription());
		
		return statusVO;
	}
	
	@Override
	public ResponseVO<String> startRegisterService() {
		try {
			List<Teacher> teachers = teacherRepository.findByIsProcessed(false);
			
			if (teachers.isEmpty()) {
				return ResponseVO.create(ResponseCode.SUCCESSFUL.getId(), ResponseCode.SUCCESSFUL.getName(),
						"No register video exist which required to process", "No attendance video exist which required to process");
			}
			
			String command = "sudo systemctl start faceregister.service";
			
			Optional<ServiceStatus> serviceStatusOpt = serviceStatusRepository
					.findTopByServiceNameOrderByIdDesc("Register");

			if (serviceStatusOpt.isPresent()) {
				ServiceStatus serviceStatus = serviceStatusOpt.get();
				
				if (StringUtils.equals("NotRunning", serviceStatus.getStatus())) {
					startService(command);
					return ResponseVO.create(ResponseCode.SUCCESSFUL.getId(), ResponseCode.SUCCESSFUL.getName(),
							ResponseCode.SUCCESSFUL.getName(), "Register service started successfully");
				} else {
					return ResponseVO.create(ResponseCode.SUCCESSFUL.getId(), ResponseCode.SUCCESSFUL.getName(),
							ResponseCode.SUCCESSFUL.getName(), "Register service is already running");
				}
			} else {
				startService(command);
				return ResponseVO.create(ResponseCode.SUCCESSFUL.getId(), ResponseCode.SUCCESSFUL.getName(),
						ResponseCode.SUCCESSFUL.getName(), "Register service started successfully");
			}
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			
			return ResponseVO.create(ResponseCode.FAIL.getId(), ResponseCode.FAIL.getName(),
					ResponseCode.FAIL.getName(), "Error in starting Register service");
		}
	}
	
	@Override
	public ResponseVO<String> startAttendanceService() {
		try {
			List<TeacherAttendance> teacherAttendanceList = teacherAttendanceRepository.findByIsProcessed(false);
			
			if (teacherAttendanceList.isEmpty()) {
				return ResponseVO.create(ResponseCode.SUCCESSFUL.getId(), ResponseCode.SUCCESSFUL.getName(),
						"No attendance video exist which required to process", "No attendance video exist which required to process");
			}
			
			String command = "sudo systemctl start facerecognize.service";
			
			Optional<ServiceStatus> serviceStatusOpt = serviceStatusRepository
					.findTopByServiceNameOrderByIdDesc("Recognize");

			if (serviceStatusOpt.isPresent()) {
				ServiceStatus serviceStatus = serviceStatusOpt.get();
				
				if (StringUtils.equals("NotRunning", serviceStatus.getStatus())) {
					startService(command);
					return ResponseVO.create(ResponseCode.SUCCESSFUL.getId(), ResponseCode.SUCCESSFUL.getName(),
							ResponseCode.SUCCESSFUL.getName(), "Recognize service started successfully");
				} else {
					return ResponseVO.create(ResponseCode.SUCCESSFUL.getId(), ResponseCode.SUCCESSFUL.getName(),
							ResponseCode.SUCCESSFUL.getName(), "Recognize service is already running");
				}
			} else {
				startService(command);
				return ResponseVO.create(ResponseCode.SUCCESSFUL.getId(), ResponseCode.SUCCESSFUL.getName(),
						ResponseCode.SUCCESSFUL.getName(), "Recognize service started successfully");
			}
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			
			return ResponseVO.create(ResponseCode.FAIL.getId(), ResponseCode.FAIL.getName(),
					ResponseCode.FAIL.getName(), "Error in starting Recognize service");
		}
	}
	
	private void startService(String command) throws IOException {
		Runtime runtime = Runtime.getRuntime();		
		runtime.exec(command);
	}

	@Override
	public ResponseVO<String> executeStatusCommand() {
		try {
			String command = "sudo systemctl status faceregister.service";
			Runtime runtime = Runtime.getRuntime();
			log.info("Status commnad execution started");

			Process process = runtime.exec(command);
			BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(process.getInputStream()));
			String line;
			while ((line = bufferedReader.readLine()) != null) {
//				System.out.println(line);
				
				log.info(line);
			}
			
			return ResponseVO.create(ResponseCode.SUCCESSFUL.getId(), ResponseCode.SUCCESSFUL.getName(),
					ResponseCode.SUCCESSFUL.getName(), "");
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			
			return ResponseVO.create(ResponseCode.FAIL.getId(), ResponseCode.FAIL.getName(),
					ResponseCode.FAIL.getName(), "");
		}
	}
	
	@Override
	public void checkAndExecuteIoTProcess() {
		try {
			List<String> serviceNames = new ArrayList<String>();
			serviceNames.add("Recognize");
			serviceNames.add("Register");
			
			List<ServiceStatus> serviceStatusList = serviceStatusRepository.findByServiceNamesAndStatus(serviceNames, "Running");
			
			if (serviceStatusList.isEmpty()) {
				log.info("Process is not running");
				
				// Check attendance video exist
				List<TeacherAttendance> teacherAttendanceList = teacherAttendanceRepository.findByIsProcessed(false);
				
				if (!teacherAttendanceList.isEmpty()) {
					// Attendance video exist, so start Recognize process
					log.info("Recognize service started : {}", new Date());
					String command = "sudo systemctl start facerecognize.service";					
					startService(command);
				} else {
					// Attendance video not exist, so check Registration video
					List<Teacher> teachers = teacherRepository.findByIsProcessed(false);
					
					if (!teachers.isEmpty()) {
						// Register video exist, so start Register process					
						log.info("Register service started : {}", new Date());
						String command = "sudo systemctl start faceregister.service";
						startService(command);
					} else {
						log.info("No new video exist : {}", new Date());
					}
				}			
			} else {
				log.info("Process is already running : {}", new Date());			
			}
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
	}
	
	@Async
	@Override
	public void callAndExecuteProcess() {
		try {
			Thread.sleep(15000);
		
			log.info("callAndExecuteProcess : {}", new Date());
			
			this.checkAndExecuteIoTProcess();
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		
	}
}
