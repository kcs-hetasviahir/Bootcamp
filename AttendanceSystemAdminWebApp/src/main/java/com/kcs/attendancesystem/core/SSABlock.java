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
@Table(name="ssa_blocks")
@EntityListeners(AuditLogEntityListener.class)
public class SSABlock extends BaseEntity {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id")
	private Long id;

	@Column(name="block_code")
	private Long blockCode;

	@Column(name="dist_id")
	private Long distId;

	@Column(name="block_name")
	private String blockName;

}