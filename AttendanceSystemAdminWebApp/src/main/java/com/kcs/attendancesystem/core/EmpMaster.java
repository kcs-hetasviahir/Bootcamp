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
import javax.persistence.Temporal;

import java.util.Date;

import static javax.persistence.TemporalType.DATE;

@Getter
@Setter
@ToString
@Entity
@Table(name="emp_mst")
@EntityListeners(AuditLogEntityListener.class)
public class EmpMaster extends BaseEntity {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id")
	private Long id;

	@Column(name="emp_id")
	private String empId;

	@Column(name="first_name")
	private String firstName;

	@Column(name="middle_name")
	private String middleName;

	@Column(name="last_name")
	private String lastName;

	@Column(name="gender")
	private String gender;

	@Temporal(DATE)
	@Column(name="doj")
	private Date doj;

	@Temporal(DATE)
	@Column(name="dob")
	private Date dob;

	@Column(name="supervisor_id")
	private String supervisorId;
}