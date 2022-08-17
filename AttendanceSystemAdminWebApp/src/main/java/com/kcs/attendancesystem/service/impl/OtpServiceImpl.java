package com.kcs.attendancesystem.service.impl;

import java.util.List;

import com.kcs.attendancesystem.core.OtpDetails;
import com.kcs.attendancesystem.dto.OtpDetailsVO;
import com.kcs.attendancesystem.dto.ResponseVO;
import com.kcs.attendancesystem.enums.ResponseCode;
import com.kcs.attendancesystem.repository.OtpDetailsRepository;
import com.kcs.attendancesystem.service.OtpService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class OtpServiceImpl implements OtpService {

    @Autowired
    private OtpDetailsRepository otpDetailsRepository;


    @Override
    public ResponseVO<Long> saveOTPDetails(OtpDetailsVO otpDetailsVO) {

        OtpDetails otpDetails = new OtpDetails();
        otpDetails.setReceiverId(otpDetailsVO.getReceiverId());
        otpDetails.setOtpKey(otpDetailsVO.getHasKey());
        OtpDetails savedOtpDetails = otpDetailsRepository.save(otpDetails);
        return ResponseVO.create(ResponseCode.SUCCESSFUL.getId(), ResponseCode.SUCCESSFUL.getName(), ResponseCode.SUCCESSFUL.getName(), savedOtpDetails.getId());
    }

    @Override
    public OtpDetailsVO getOTPDetails(String receiverId) {
        List<OtpDetails> otpDetailsList = otpDetailsRepository.findByMobileNumber(receiverId);
        OtpDetails otpDetails = otpDetailsList.get(otpDetailsList.size() - 1);
        OtpDetailsVO otpDetailsVO = new OtpDetailsVO();
        otpDetailsVO.setHasKey(otpDetails.getOtpKey());
        otpDetailsVO.setReceiverId(otpDetails.getReceiverId());
        return otpDetailsVO;
    }
}
