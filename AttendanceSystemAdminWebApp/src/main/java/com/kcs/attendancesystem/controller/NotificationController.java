package com.kcs.attendancesystem.controller;

import com.kcs.attendancesystem.core.Teacher;
import com.kcs.attendancesystem.dto.OtpDetailsVO;
import com.kcs.attendancesystem.dto.ResponseVO;
import com.kcs.attendancesystem.dto.ValidateMobileResponseVO;
import com.kcs.attendancesystem.enums.ResponseCode;
import com.kcs.attendancesystem.service.NotificationService;
import com.kcs.attendancesystem.service.OtpService;
import com.kcs.attendancesystem.utils.HmacShaAlgorithm;
import com.kcs.attendancesystem.utils.TOTP;
import com.kcs.attendancesystem.utils.TOTPValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.UnsupportedEncodingException;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@CommonRestController
public class NotificationController {

    @Autowired
    private NotificationService notificationService;

    @Autowired
    private OtpService otpService;

    @Value("${totp.time.step}")
    long totpTime;

    @Value("${totp.digit}")
    int totpDigit;

    @Value("${totp.validity.period}")
    int totpValidity;

    @PostMapping("/validateMobile")
    public ResponseVO<ValidateMobileResponseVO> validateMobile(@RequestParam("mobileNumber") String mobileNumber) throws UnsupportedEncodingException {
        Teacher teacher = notificationService.validateMobile(mobileNumber);
        if (teacher != null) {
            StringBuilder sb = new StringBuilder();
            sb.append(UUID.randomUUID().toString().replaceAll("-", "").toUpperCase());
            sb.append(UUID.randomUUID().toString().replaceAll("-", "").toUpperCase());
            byte[] key = sb.toString().getBytes("US-ASCII");
            TOTP totp = TOTP.key(key).timeStep(TimeUnit.SECONDS.toMillis(totpValidity)).digits(totpDigit).hmacSha512().build();
            OtpDetailsVO otpDetailsVO = new OtpDetailsVO();
            otpDetailsVO.setHasKey(key);
            otpDetailsVO.setReceiverId(String.valueOf(mobileNumber));
            otpService.saveOTPDetails(otpDetailsVO);
            ValidateMobileResponseVO validateMobileResponseVO = new ValidateMobileResponseVO();
            validateMobileResponseVO.setOtp(totp.value());
            validateMobileResponseVO.setUserId(teacher.getId());
            validateMobileResponseVO.setEmployeeId(teacher.getTeacherId());
            if (teacher.getRegistrationId() != null) {
                validateMobileResponseVO.setRegistrationId(teacher.getRegistrationId());
            }
            //TODO Remove commented code to send SMS on Mobile
//            notificationService.sendOtpSMS(mobileNumber, totp.value());
            notificationService.sendOtpMail(teacher.getEmailAddress(), totp.value());
            return ResponseVO.create(ResponseCode.SUCCESSFUL.getId(), ResponseCode.SUCCESSFUL.getName(),
                    ResponseCode.SUCCESSFUL.getName(), validateMobileResponseVO);
        } else {
            return ResponseVO.create(ResponseCode.FAIL.getId(), ResponseCode.FAIL.getName(),
                    ResponseCode.FAIL.getName(), null);
        }
    }

    @PostMapping("/getOTP")
    public ResponseVO<String> sendMobileOtp(@RequestParam("value") String value, @RequestParam("type") String type) throws UnsupportedEncodingException {
        StringBuilder sb = new StringBuilder();
        sb.append(UUID.randomUUID().toString().replaceAll("-", "").toUpperCase());
        sb.append(UUID.randomUUID().toString().replaceAll("-", "").toUpperCase());
        byte[] key = sb.toString().getBytes("US-ASCII");
        TOTP totp = TOTP.key(key).timeStep(TimeUnit.SECONDS.toMillis(totpValidity)).digits(totpDigit).hmacSha512().build();
        OtpDetailsVO otpDetailsVO = new OtpDetailsVO();
        otpDetailsVO.setHasKey(key);
        otpDetailsVO.setReceiverId(String.valueOf(value));
        otpService.saveOTPDetails(otpDetailsVO);
        if (type.equalsIgnoreCase("mobile")) {
            //TODO Remove commented code to send SMS on Mobile
            //notificationService.sendOtpSMS(value, totp.value());
        } else if (type.equalsIgnoreCase("email")) {
            notificationService.sendOtpMail(value, totp.value());
        }
        return ResponseVO.create(ResponseCode.SUCCESSFUL.getId(), ResponseCode.SUCCESSFUL.getName(),
                ResponseCode.SUCCESSFUL.getName(), totp.value());
    }

    @PostMapping("/validateOtp")
    public ResponseVO<Boolean> validateOtp(@RequestParam("receiverId") String receiverId, @RequestParam("otp") String otp) throws UnsupportedEncodingException {

        OtpDetailsVO otpDetailsVO = otpService.getOTPDetails(receiverId);

        byte[] key = otpDetailsVO.getHasKey();
        long timeStep = totpTime;

        int otpValidityPeriod = totpValidity;
        int window = (int) ((otpValidityPeriod - timeStep) / timeStep);

        return ResponseVO.create(ResponseCode.SUCCESSFUL.getId(), ResponseCode.SUCCESSFUL.getName(),
                ResponseCode.SUCCESSFUL.getName(), TOTPValidator.window(window).isValid(key, TimeUnit.SECONDS.toMillis(totpValidity), totpDigit, HmacShaAlgorithm.HMAC_SHA_512, String.valueOf(otp)));
    }

    @PostMapping("/sendMailWithAttachment")
    public ResponseVO<Boolean> sendMailWithAttachment(@RequestParam("receiverId") String receiverId,
                                                      @RequestParam("file") MultipartFile fileToAttach) throws UnsupportedEncodingException {

        notificationService.sendMailWithAttachment(receiverId, fileToAttach);

        return ResponseVO.create(ResponseCode.SUCCESSFUL.getId(), ResponseCode.SUCCESSFUL.getName(),
                ResponseCode.SUCCESSFUL.getName(), true);
    }
}
