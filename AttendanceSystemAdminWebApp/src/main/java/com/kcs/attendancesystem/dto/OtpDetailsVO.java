package com.kcs.attendancesystem.dto;

import lombok.Data;

@Data
public class OtpDetailsVO {

    private String receiverId;
    private byte[] hasKey;
}
