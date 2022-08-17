package com.kcs.attendancesystem.service;

import com.kcs.attendancesystem.dto.ResponseVO;
import com.kcs.attendancesystem.dto.ServiceStatusVO;

public interface IOTCommnadService {

	ResponseVO<String> executeStatusCommand();

	ResponseVO<ServiceStatusVO> checkRegisterServiceStatus();

	ResponseVO<ServiceStatusVO> checkAttendanceServiceStatus();

	ResponseVO<String> startRegisterService();

	ResponseVO<String> startAttendanceService();

	void checkAndExecuteIoTProcess();

	void callAndExecuteProcess();

}
