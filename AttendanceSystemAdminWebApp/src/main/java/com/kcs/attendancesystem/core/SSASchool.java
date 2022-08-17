package com.kcs.attendancesystem.core;

import com.kcs.attendancesystem.config.AuditLogEntityListener;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Getter
@Setter
@ToString
@Entity
@Table(name="ssa_schools")
@EntityListeners(AuditLogEntityListener.class)
public class SSASchool extends BaseEntity {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id")
	private Long id;

	@Column(name="district_code")
	private Long districtCode;

	@Column(name="block_id")
	private Long blockId;

	@Column(name="crc_code")
	private Long crcCode;

	@Column(name="school_code")
	private Long schoolCode;

	@Column(name="school_name")
	private String schoolName;

	@Column(name="school_latitude")
	private String schoolLatitude;

	@Column(name="school_longitude")
	private String schoolLongitude;

}