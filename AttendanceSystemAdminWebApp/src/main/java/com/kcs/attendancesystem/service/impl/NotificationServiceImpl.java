package com.kcs.attendancesystem.service.impl;

import java.io.File;
import java.util.Objects;

import javax.mail.internet.MimeMessage;

import com.kcs.attendancesystem.core.SupportTicket;
import com.kcs.attendancesystem.core.Teacher;
import com.kcs.attendancesystem.core.TeacherAttendance;
import com.kcs.attendancesystem.repository.TeacherRepository;
import com.kcs.attendancesystem.service.NotificationService;
import com.kcs.attendancesystem.utils.Utils;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class NotificationServiceImpl implements NotificationService {

    @Autowired
    private JavaMailSender javaMailSender;

    @Autowired
    private TeacherRepository teacherRepository;

    @Autowired
    private RestTemplate restTemplate;

    @Value("${spring.mail.username}")
    String mailUserName;

    @Value("${sms.api.key}")
    String smsApiKey;

    @Value("${sms.type}")
    int smsType;

    @Value("${sms.sender.id}")
    String smsSenderId;

    @Value("${sms.entity.id}")
    String smsEntityId;

    @Value("${sms.template.id}")
    String smsTemplateId;

    @Value("${sms.base.url}")
    String smsBaseUrl;

    @Override
    @Async
    public void sendMail(Teacher teacher) {
        try {
            String htmlMsg = StringUtils.join("Thank you ", generateFullName(teacher),
                    ",<br /><br />You are successfully registered with following detail : ", "<br />",
                    "<ul><li><b>Registered ID : </b>", teacher.getRegistrationId(), "</li> ", "<li><b>Latitude : </b>",
                    teacher.getLatitude(), "</li> ", "<li><b>Longitude : </b>", teacher.getLongitude(), "</li></ul>",
                    "<br /> Thanks & Regards", "<br />", "Vidhya Samiksha Kendra", "<br />", "Sector-19, Gandhinagar, Gujarat.", "<br />");
            MimeMessage mailMessage = javaMailSender.createMimeMessage();
            mailMessage.setSubject("Attendance System - Registration Successful", "UTF-8");

            MimeMessageHelper helper = new MimeMessageHelper(mailMessage, true, "UTF-8");
            helper.setFrom(mailUserName);
            helper.setTo(teacher.getEmailAddress());
            helper.setText(htmlMsg, true);

            javaMailSender.send(mailMessage);

            log.info("Email sent successfully");
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
    }

    @Override
    @Async
    public void sendAttendanceMail(Teacher teacher, TeacherAttendance teacherAttendance) {
        try {
            String htmlMsg = StringUtils.join("Thank you ", generateFullName(teacher),
                    ",<br /><br />Your attendance submitted successfully with following detail : ", "<br />",
                    "<ul><li><b>Transaction ID : </b>", teacherAttendance.getTransactionId(), "</li> ", "<li><b>Attendance Date And Time : </b>",
                    Utils.formatDateTime(teacherAttendance.getCreatedDate()), "</li> ", "<li><b>Latitude : </b>",
                    teacherAttendance.getLatitude(), "</li> ", "<li><b>Longitude : </b>", teacherAttendance.getLongitude(), "</li></ul>",
                    "<br /> Thanks & Regards", "<br />", "Vidhya Samiksha Kendra", "<br />", "Sector-19, Gandhinagar, Gujarat.", "<br />");
            MimeMessage mailMessage = javaMailSender.createMimeMessage();
            mailMessage.setSubject("Attendance System - Attendance Submitted Successfully", "UTF-8");

            MimeMessageHelper helper = new MimeMessageHelper(mailMessage, true, "UTF-8");
            helper.setFrom(mailUserName);
            helper.setTo(teacher.getEmailAddress());
            helper.setText(htmlMsg, true);

            javaMailSender.send(mailMessage);

            log.info("Email sent successfully");
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
    }


    @Override
    @Async
    public void sendSupportTicketMail(Teacher teacher, SupportTicket supportTicket) {
        try {
            String htmlMsg = StringUtils.join("Thank you ", generateFullName(teacher),
                    ",<br /><br />Your support ticket submitted successfully with following detail : ", "<br />",
                    "<ul><li><b>Ticket Number : </b>", supportTicket.getId(), "</li> ", "<li><b>Category : </b>",
                    supportTicket.getCategory(), "</li> ", "<li><b>Description : </b>", supportTicket.getDescription(), "</li></ul>",
                    "<br /> Thanks & Regards", "<br />", "Team KCS", "<br />");
            MimeMessage mailMessage = javaMailSender.createMimeMessage();
            mailMessage.setSubject("Attendance System - Support Ticket Submitted Successfully", "UTF-8");

            MimeMessageHelper helper = new MimeMessageHelper(mailMessage, true, "UTF-8");
            helper.setFrom(mailUserName);
            helper.setBcc(mailUserName);
            helper.setTo(supportTicket.getEmailAddress());
            helper.setText(htmlMsg, true);

            javaMailSender.send(mailMessage);

            log.info("Email sent successfully");
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
    }


    @Override
    @Async
    public void sendOtpMail(String emailId, String otp) {
        try {
            String htmlMsg = StringUtils.join("Hello ",
                    "<br /> <br /><b>", otp, "</b> is your verification code. Have a nice day! ",
                    "<br /> <br /> Thanks & Regards", "<br />", "Team KCS", "<br />");

            MimeMessage mailMessage = javaMailSender.createMimeMessage();
            mailMessage.setSubject("Attendance System - OTP", "UTF-8");

            MimeMessageHelper helper = new MimeMessageHelper(mailMessage, true, "UTF-8");
            helper.setFrom(mailUserName);
            helper.setTo(emailId);
            helper.setText(htmlMsg, true);

            javaMailSender.send(mailMessage);

            log.info("Email sent successfully");
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
    }

    @Override
    @Async
    public void sendMailWithAttachment(String receiverId, MultipartFile fileToAttach) {
        try {
            String htmlMsg = StringUtils.join("Thank you for generating the report ",
                    ",<br /><br />Please find attached report", "<br />",
                    "<br /> Thanks & Regards", "<br />", "Team KCS", "<br />");
            MimeMessage mailMessage = javaMailSender.createMimeMessage();
            mailMessage.setSubject("Attendance System - Reports", "UTF-8");

            MimeMessageHelper helper = new MimeMessageHelper(mailMessage, true, "UTF-8");
            helper.setFrom(mailUserName);
            helper.setTo(receiverId);
            helper.setText(htmlMsg, true);
            File file = multipartToFile(fileToAttach);
            if (file != null) {
                helper.addAttachment(file.getName(), file);
            }

            javaMailSender.send(mailMessage);

            log.info("Email sent successfully");
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
    }

    @Override
    @Async
    public void sendOtpSMS(String mobileNumber, String otp) {
        try {
            String msg = StringUtils.join(otp, " is your verification code. Have a nice day! From KRISH COMPUSOFT");

            String url = String.format(smsBaseUrl + "APIkey=" + smsApiKey
                    + "&SenderID=" + smsSenderId
                    + "&SMSType=" + smsType
                    + "&Mobile=" + mobileNumber
                    + "&MsgText=" + msg
                    + "&EntityID=" + smsEntityId
                    + "&TemplateID=" + smsTemplateId);

            ResponseEntity<String> responseEntity = restTemplate.exchange(url, HttpMethod.GET, null, String.class);

            log.info("SMS sent successfully" + responseEntity.getBody());
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
    }

    @Override
    public Teacher validateMobile(String mobileNumber) {
        try {
            Teacher teacher = teacherRepository.findByMobileNumber(mobileNumber);
            return teacher;
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return null;
        }
    }

    private File multipartToFile(MultipartFile multipart) {
        try {
            String os = System.getProperty("os.name").toLowerCase();
            File convFile = null;
            if(os.startsWith("windows")){
                convFile =  new File(System.getProperty("java.io.tmpdir") + "/" + multipart.getOriginalFilename());
            } else {
                convFile = new File("/var/tmp" + "/" + multipart.getOriginalFilename());
            }

            multipart.transferTo(convFile);
            return convFile;
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return null;
        }
    }

    private String generateFullName(Teacher teacher) {
        if (Objects.nonNull(teacher)) {
            return StringUtils.join(StringUtils.defaultIfBlank(teacher.getFirstName(), ""), " ",
                    StringUtils.defaultIfBlank(teacher.getMiddleName(), ""), " ",
                    StringUtils.defaultIfBlank(teacher.getLastName(), ""));
        }

        return StringUtils.EMPTY;
    }
}
