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
public class DetectFacesResponseVO implements Serializable {

    private static final long serialVersionUID = 1L;

    private String faceImage;
    private double quality;
    private double score;
    private long size;
    private FaceLocationVO faceLocation;

}
