package com.kcs.attendancesystem.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class FaceLocationVO implements Serializable {

    private static final long serialVersionUID = 1L;

    private long x;
    private long y;
}
