package com.kcs.attendancesystem.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class SupportTicketVO {

	private String emailAddress;
	
	private String category;

	private String description;
}
