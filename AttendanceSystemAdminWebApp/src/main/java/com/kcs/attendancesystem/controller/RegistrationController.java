package com.kcs.attendancesystem.controller;

import java.io.IOException;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.kcs.attendancesystem.dto.AttendanceVO;
import com.kcs.attendancesystem.dto.ResponseVO;
import com.kcs.attendancesystem.dto.TeacherVO;
import com.kcs.attendancesystem.enums.ResponseCode;
import com.kcs.attendancesystem.service.RegistrationService;
import com.kcs.attendancesystem.utility.JsonUtil;

@CommonRestController
public class RegistrationController {

    @Value("${documnet.file.path}")
    private String documentPath;

    @Value("${documnet.attendance.file.path}")
    private String documentAttendancePath;

    @Autowired
    private RegistrationService registrationService;

    @PostMapping("/registration")
    public ResponseVO<String> addRegistration(@RequestParam("data") String data,
                                              @RequestParam("files") MultipartFile[] files) throws JsonParseException, JsonMappingException, IOException {


        if (StringUtils.isBlank(data)) {
            return ResponseVO.create(ResponseCode.INVALID_ARGUMENT.getId(), ResponseCode.INVALID_ARGUMENT.getName(), ResponseCode.INVALID_ARGUMENT.getName(), StringUtils.EMPTY);
        }

        TeacherVO teacherVO = JsonUtil.toObject(data.getBytes(), TeacherVO.class);
        teacherVO.setFiles(files);
        //teacherVO.setFilePath(documentPath);
        return registrationService.addRegistration(teacherVO);
    }


    @PostMapping("/attendance")
    public ResponseVO<String> addAttendance(@RequestParam("data") String data,
                                            @RequestParam("files") MultipartFile[] files) throws JsonParseException, JsonMappingException, IOException {


        if (StringUtils.isBlank(data)) {
            return ResponseVO.create(ResponseCode.INVALID_ARGUMENT.getId(), ResponseCode.INVALID_ARGUMENT.getName(), ResponseCode.INVALID_ARGUMENT.getName(), StringUtils.EMPTY);
        }

        AttendanceVO attendanceVO = JsonUtil.toObject(data.getBytes(), AttendanceVO.class);
        attendanceVO.setFiles(files);
//		attendanceVO.setFilePath(documentAttendancePath+Utility.toDateString(Calendar.getInstance().getTime())+File.separator);
        return registrationService.addAttendance(attendanceVO);
    }
}
