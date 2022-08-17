package com.kcs.attendancesystem.core;

import static javax.persistence.EnumType.STRING;
import static javax.persistence.TemporalType.TIMESTAMP;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;

import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.kcs.attendancesystem.enums.Action;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name="audit_log")
@EntityListeners(AuditingEntityListener.class)
public class AuditLog {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
    private Long id;
	
	@Enumerated(STRING)
	@Column(name = "action")
	private Action action;
	
	@Column(name = "entity_name")
	private String entityName;

	@Column(name = "ref_table_id")
	private Long refTableId;

	@Column(name = "content")
	private String content;

	@CreatedBy
	@Column(name = "modified_by")
	private String modifiedBy;

	@CreatedDate
	@Temporal(TIMESTAMP)
	@Column(name = "modified_date")
	private Date modifiedDate;

}
