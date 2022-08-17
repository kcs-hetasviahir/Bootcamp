package com.kcs.attendancesystem.core;

import static javax.persistence.TemporalType.TIMESTAMP;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;

import com.kcs.attendancesystem.config.AuditLogEntityListener;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.Generated;
import org.hibernate.annotations.GenerationTime;

@Getter
@Setter
@ToString
@Entity
@Table(name="teacher")
@EntityListeners(AuditLogEntityListener.class)
public class Teacher extends BaseEntity {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id")
	private Long id;
	
	@Column(name="teacher_id")
	private String teacherId;

	@Column(name="first_name")
	private String firstName;
	
	@Column(name="middle_name")
	private String middleName;
	
	@Column(name="last_name")
	private String lastName;
	
	@Column(name="date_of_birth")
	@Temporal(TIMESTAMP)
	private Date dateOfBirth;

	@Column(name="date_of_joining")
	@Temporal(TIMESTAMP)
	private Date dateOfJoining;
	
	@Column(name="mobile_number")
	private String mobileNumber;
	
	@Column(name="email_address")
	private String emailAddress;
	
	@Column(name="designation")
	private String designation;
	
	@ManyToOne
	@JoinColumn(name="posting_location")
	private City postingLocation;
	
	@ManyToOne
	@JoinColumn(name="institution_id")
	private Institution institution;
	
	
	@Column(name="house_name")
	private String houseName;
	
	@Column(name="address1")
	private String address1;

	@Column(name="address2")
	private String address2;

	@Column(name="landmark")
	private String landmark;
	
	@ManyToOne
	@JoinColumn(name="city")
	private City city;
	
	@ManyToOne
	@JoinColumn(name="state")
	private State state;

	@ManyToOne
	@JoinColumn(name="district")
	private District district;

	@ManyToOne
	@JoinColumn(name="taluka")
	private Taluka taluka;

	@ManyToOne
	@JoinColumn(name="village")
	private Village village;

	@Column(name="ssa_district_code")
	private Long ssaDistrictCode;

	@Column(name="ssa_block_code")
	private Long ssaBlockCode;

	@Column(name="ssa_cluster_code")
	private Long ssaClusterCode;

	@Column(name="ssa_school_code")
	private Long ssaSchoolCode;

	@Column(name="pincode")
	private String pincode;

	@Column(name="video_name")
	private String videoName;
	
	@Column(name="is_processed")
	private Boolean isProcessed;
	
	@Column(name="processed_status")
	private String processedStatus;
	
	@Column(name="video_label")
	private String videoLabel;
	
	@Column(name="device_id")
	private String deviceId;
	
	@Column(name="latitude")
	private String latitude;
	
	@Column(name="longitude")
	private String longitude;

	@Column(name="process_start_time")
	private String processStartTime;
	
	@Column(name="process_end_time")
	private String processEndTime;

	@Column(name="supervisor_id")
	private Long supervisorId;

	@Column(name="gender")
	private String gender;

	@Generated(value = GenerationTime.INSERT)
	@Column(name="registration_id",insertable = false,
			updatable = false, nullable = true)
	private String registrationId;

}