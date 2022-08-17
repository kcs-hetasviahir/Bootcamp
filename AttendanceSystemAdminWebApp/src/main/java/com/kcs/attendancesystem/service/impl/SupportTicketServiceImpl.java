package com.kcs.attendancesystem.service.impl;

import java.util.Optional;

import com.kcs.attendancesystem.core.Teacher;
import com.kcs.attendancesystem.repository.TeacherRepository;
import com.kcs.attendancesystem.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kcs.attendancesystem.core.SupportTicket;
import com.kcs.attendancesystem.core.User;
import com.kcs.attendancesystem.dto.ResponseVO;
import com.kcs.attendancesystem.dto.SupportTicketVO;
import com.kcs.attendancesystem.enums.ResponseCode;
import com.kcs.attendancesystem.repository.SupportTicketRepository;
import com.kcs.attendancesystem.repository.UserRepository;
import com.kcs.attendancesystem.service.SupportTicketService;

@Service
public class SupportTicketServiceImpl implements SupportTicketService {

    @Autowired
    private SupportTicketRepository supportTicketRepository;

    @Autowired
    private TeacherRepository teacherRepository;

    @Autowired
    private NotificationService notificationService;

    @Override
    public ResponseVO<Long> saveSupportTicket(SupportTicketVO supportTicketVO) {
        Teacher teacher = teacherRepository.findByEmailId(supportTicketVO.getEmailAddress());

        if (teacher != null) {
            SupportTicket supportTicket = new SupportTicket();
            supportTicket.setTeacher(teacher);
            supportTicket.setEmailAddress(supportTicketVO.getEmailAddress());
            supportTicket.setCategory(supportTicketVO.getCategory());
            supportTicket.setDescription(supportTicketVO.getDescription());
            SupportTicket savedSupportTicket = supportTicketRepository.save(supportTicket);
            notificationService.sendSupportTicketMail(teacher, supportTicket);

            return ResponseVO.create(ResponseCode.SUCCESSFUL.getId(), ResponseCode.SUCCESSFUL.getName(), ResponseCode.SUCCESSFUL.getName(), savedSupportTicket.getId());
        }

        return ResponseVO.create(ResponseCode.FAIL.getId(), ResponseCode.FAIL.getName(), "Invalid emailId, please provide valid emailId", null);
    }
}
