package com.kcs.attendancesystem.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@XmlRootElement(name="DetectFacesSettingsWrapperVO")
@JsonNaming(PropertyNamingStrategy.PascalCaseStrategy.class)
public class DetectFacesSettingsWrapperVO implements Serializable {

    private static final long serialVersionUID = 1L;

    private int detectionThreshold;
    private int qualityThreshold;
    private int minimumFaceSize;
    
}
