package com.kcs.attendancesystem.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class EmpMstVO {

	private Long id;

	private String empId;

	private String firstName;

	private String middleName;

	private String lastName;

	private String gender;

	private String doj;

	private String dob;

	private String supervisorId;
}
