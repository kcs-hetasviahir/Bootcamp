package com.kcs.attendancesystem.service.impl;

import java.util.Date;

import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class ScheduledIoTTasks {

//	@Autowired
//	private IOTCommnadService iotCommnadService;
	
//	@Scheduled(cron = "0 0/5 * 1/1 * ?")
	public void scheduleTask() {
		log.info("Scheduled IOT task called : {}", new Date());
		
//		iotCommnadService.checkAndExecuteIoTProcess();
	}
}
