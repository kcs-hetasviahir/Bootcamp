package com.kcs.attendancesystem.service;

import com.kcs.attendancesystem.dto.DetectFacesResponseVO;
import com.kcs.attendancesystem.dto.DetectFacesWrapperVO;
import com.kcs.attendancesystem.dto.EnrollSubjectWithAlbumWrapperVO;
import com.kcs.attendancesystem.dto.IdentifyFacesResponseVO;
import com.kcs.attendancesystem.dto.IdentifyFacesWrapperVO;

public interface HertaService {

    DetectFacesResponseVO detectFaces(DetectFacesWrapperVO detectFacesWrapperVO);

    IdentifyFacesResponseVO identifyFace(IdentifyFacesWrapperVO identifyFacesWrapperVO);

    String enrollSubjectWithAlbum(EnrollSubjectWithAlbumWrapperVO enrollSubjectWithAlbumWrapperVO);
}
