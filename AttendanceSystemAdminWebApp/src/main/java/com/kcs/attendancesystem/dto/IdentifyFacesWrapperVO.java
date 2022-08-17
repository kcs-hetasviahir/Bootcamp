package com.kcs.attendancesystem.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.ArrayList;

@Getter
@Setter
@NoArgsConstructor
public class IdentifyFacesWrapperVO implements Serializable {

    private static final long serialVersionUID = 1L;

    private ArrayList<String> images;
    private int maxNumberOfCandidates;
}
