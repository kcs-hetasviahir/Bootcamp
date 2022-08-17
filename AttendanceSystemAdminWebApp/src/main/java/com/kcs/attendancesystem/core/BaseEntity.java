package com.kcs.attendancesystem.core;

import static javax.persistence.TemporalType.TIMESTAMP;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import javax.persistence.Temporal;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class BaseEntity {
	
	
	@CreatedDate
	@Temporal(TIMESTAMP)
	@Column(name = "created_dttm")
	protected Date createdDate;

	@LastModifiedDate
	@Temporal(TIMESTAMP)
	@Column(name = "last_updated_dttm")
	protected Date lastUpdatedDate;
	
	public abstract Long getId();
	
}
