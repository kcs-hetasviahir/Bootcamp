package com.kcs.attendancesystem.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
public class DetectFacesWrapperVO implements Serializable {

    private static final long serialVersionUID = 1L;

    private String image;
    private DetectFacesSettingsWrapperVO settings;
}
