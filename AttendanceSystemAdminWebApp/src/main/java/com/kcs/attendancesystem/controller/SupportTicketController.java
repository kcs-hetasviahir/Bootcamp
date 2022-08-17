package com.kcs.attendancesystem.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.kcs.attendancesystem.dto.ResponseVO;
import com.kcs.attendancesystem.dto.SupportTicketVO;
import com.kcs.attendancesystem.service.SupportTicketService;

@CommonRestController
public class SupportTicketController {

	@Autowired
	private SupportTicketService supportTicketService;
	
	@PostMapping("/support-ticket/save")
	public ResponseVO<Long> saveSupportTicket(@RequestBody SupportTicketVO ticketVO) {
		return supportTicketService.saveSupportTicket(ticketVO);
	}
}
