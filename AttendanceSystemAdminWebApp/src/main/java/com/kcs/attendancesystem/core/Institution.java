package com.kcs.attendancesystem.core;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.kcs.attendancesystem.config.AuditLogEntityListener;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Entity
@Table(name="institution")
@EntityListeners(AuditLogEntityListener.class)
public class Institution extends BaseEntity {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id")
	private Long id;
	
	@Column(name="name")
	private String name;

	@Column(name="description")
	private String description;

	@ManyToOne
	@JoinColumn(name="city")
	private City city;
	
	@Column(name="device_name")
	private String deviceName;

	@Column(name="device_unique_id")
	private String deviceUniqueId;

	@ManyToOne
	@JoinColumn(name="user_id")
	private User user;

}