package com.kcs.attendancesystem.service;

import com.kcs.attendancesystem.core.SupportTicket;
import com.kcs.attendancesystem.core.Teacher;
import com.kcs.attendancesystem.core.TeacherAttendance;
import org.springframework.web.multipart.MultipartFile;

public interface NotificationService {

	void sendMail(Teacher teacher);

	void sendAttendanceMail(Teacher teacher, TeacherAttendance teacherAttendance);

	void sendOtpMail(String emailId, String otp);

	void sendOtpSMS(String mobileNumber, String otp);

	Teacher validateMobile(String mobileNumber);

	void sendSupportTicketMail(Teacher teacher, SupportTicket supportTicket);

	void sendMailWithAttachment(String receiverId, MultipartFile fileToAttach);
}
