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
@Table(name="ssa_cluster")
@EntityListeners(AuditLogEntityListener.class)
public class SSACluster extends BaseEntity {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id")
	private Long id;

	@Column(name="district_code")
	private Long districtCode;

	@Column(name="block_code")
	private Long blockCode;

	@Column(name="crc_code")
	private Long crcCode;

	@Column(name="crc_name")
	private String crcName;

}