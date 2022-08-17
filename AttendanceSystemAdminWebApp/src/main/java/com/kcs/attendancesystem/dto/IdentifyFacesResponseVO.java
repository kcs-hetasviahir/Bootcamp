package com.kcs.attendancesystem.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.ArrayList;

@Getter
@Setter
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class IdentifyFacesResponseVO implements Serializable {

    private static final long serialVersionUID = 1L;

    private ArrayList<IdentifyFaceValueVO> identifySubjectResult;

}
