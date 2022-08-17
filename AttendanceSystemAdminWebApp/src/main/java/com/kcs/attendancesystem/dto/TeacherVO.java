package com.kcs.attendancesystem.dto;

import org.springframework.web.multipart.MultipartFile;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class TeacherVO {

	private String employeeId;
	
	private String firstName;
	
	private String middleName;
	
	private String lastName;
	
	private String fullName;
	
	private String dateOfBirth;
	
	private String dob;
		
	private String dateOfJoining;
	
	private String address1;
	
	private String address2;
	
	private String mobile;
	
	private String email;
	
	private String designation;
	
	private String postingLocation;
	
	private String institutionName;
	
	private String superivorName;
	
	private String institutionId;
	
	private String superivorId;
	
	private MultipartFile[] files;
	
	private String filePath;
	
	private String cityId;
	
	private String stateId;
	
	private String mobileNumber;

	private String houseName;
	
	private String landmark;
	
	private String pincode;
	
	private String fullAddress;
	
	private String deviceId;
	
	private String latitude;
	
	private String longitude;

	private String district;

	private String taluka;

	private String village;

	private String ssaDistrict;

	private String ssaBlock;

	private String ssaCluster;

	private String ssaSchool;

	private String id;

	private String gender;

	private String registrationId;
}
