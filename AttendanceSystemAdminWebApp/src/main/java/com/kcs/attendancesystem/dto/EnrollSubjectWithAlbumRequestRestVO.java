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
@XmlRootElement(name="EnrollSubjectWithAlbumRequestRestVO")
@JsonNaming(PropertyNamingStrategy.PascalCaseStrategy.class)
public class EnrollSubjectWithAlbumRequestRestVO implements Serializable {

    private static final long serialVersionUID = 1L;

    private String subjectCode;

    private String subjectName;

    private String subjectLastName;

    private boolean subjectActive;

    private String subjectProfilePhoto;
}
