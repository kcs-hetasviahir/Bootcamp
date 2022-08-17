package com.kcs.attendancesystem.core;

import static javax.persistence.EnumType.STRING;

import static javax.persistence.TemporalType.DATE;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.kcs.attendancesystem.config.AuditLogEntityListener;
import com.kcs.attendancesystem.enums.AttendanceStatus;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.Generated;
import org.hibernate.annotations.GenerationTime;

@Getter
@Setter
@ToString
@Entity
@Table(name = "teacher_attendance")
@EntityListeners(AuditLogEntityListener.class)
public class TeacherAttendance extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;

	@ManyToOne
	@JoinColumn(name = "teacher_id")
	private Teacher teacher;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "attendance_date")
	private Date attendanceDate;

	@Column(name = "video_name")
	private String videoName;

	@Column(name = "video_location")
	private String videoLocation;

	@Column(name = "status")
	@Enumerated(STRING)
	private AttendanceStatus status;

	@Column(name = "is_processed")
	private Boolean isProcessed;

	@Column(name = "processed_status")
	private String processedStatus;

	@Column(name = "video_label")
	private String videoLabel;

	@Column(name = "device_id")
	private String deviceId;

	@Column(name = "latitude")
	private String latitude;

	@Column(name = "longitude")
	private String longitude;

	@Column(name = "process_start_time")
	private String processStartTime;

	@Column(name = "process_end_time")
	private String processEndTime;

	@Column(name = "person_accuracy")
	private String personAccuracy;

	@Generated(value = GenerationTime.INSERT)
	@Column(name = "transaction_id", insertable = false, updatable = false, nullable = true)
	private String transactionId;
}