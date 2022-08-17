package com.kcs.attendancesystem.service;

import com.kcs.attendancesystem.dto.OtpDetailsVO;
import com.kcs.attendancesystem.dto.ResponseVO;

public interface OtpService {

    ResponseVO<Long> saveOTPDetails(OtpDetailsVO otpDetailsVO);

    OtpDetailsVO getOTPDetails(String receiverId);
}
