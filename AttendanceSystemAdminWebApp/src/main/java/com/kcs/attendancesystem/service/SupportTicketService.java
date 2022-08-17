package com.kcs.attendancesystem.service;

import com.kcs.attendancesystem.dto.ResponseVO;
import com.kcs.attendancesystem.dto.SupportTicketVO;

public interface SupportTicketService {

	ResponseVO<Long> saveSupportTicket(SupportTicketVO supportTicketVO);

}
