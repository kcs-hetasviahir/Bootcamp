package com.kcs.attendancesystem.controller;

import com.kcs.attendancesystem.dto.DetectFacesResponseVO;
import com.kcs.attendancesystem.dto.DetectFacesSettingsWrapperVO;
import com.kcs.attendancesystem.dto.DetectFacesWrapperVO;
import com.kcs.attendancesystem.dto.EnrollSubjectWithAlbumWrapperVO;
import com.kcs.attendancesystem.dto.IdentifyFacesResponseVO;
import com.kcs.attendancesystem.dto.IdentifyFacesWrapperVO;
import com.kcs.attendancesystem.dto.ResponseVO;
import com.kcs.attendancesystem.enums.ResponseCode;
import com.kcs.attendancesystem.service.HertaService;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

@CommonRestController
public class HertaController {

    @Autowired
    private HertaService hertaService;

    @Value("${herta.face.detection.threshold}")
    private int faceDetectionThreshold;

    @Value("${herta.face.quality.threshold}")
    private int faceQualityThreshold;

    @Value("${herta.minimum.face.size}")
    private int minimumFaceSize;

    @PostMapping("/detectFace")
    public ResponseVO<DetectFacesResponseVO> detectFaces(@RequestParam("file") MultipartFile file) throws IOException {
        DetectFacesWrapperVO detectFacesWrapperVO = new DetectFacesWrapperVO();
        DetectFacesResponseVO detectFacesResponseVO = null;
        DetectFacesSettingsWrapperVO detectFacesSettingsWrapperVO = new DetectFacesSettingsWrapperVO();
        byte[] image = Base64.encodeBase64(file.getBytes());
        String base64String = new String(image, StandardCharsets.UTF_8);
        detectFacesWrapperVO.setImage(base64String);
        //Set default setting
        detectFacesSettingsWrapperVO.setDetectionThreshold(faceDetectionThreshold);
        detectFacesSettingsWrapperVO.setQualityThreshold(faceQualityThreshold);
        detectFacesSettingsWrapperVO.setMinimumFaceSize(minimumFaceSize);
        detectFacesWrapperVO.setSettings(detectFacesSettingsWrapperVO);
        try {
            detectFacesResponseVO = hertaService.detectFaces(detectFacesWrapperVO);
            return ResponseVO.create(ResponseCode.SUCCESSFUL.getId(), ResponseCode.SUCCESSFUL.getName(),
                    ResponseCode.SUCCESSFUL.getName(), detectFacesResponseVO);
        } catch(Exception exception) {
            return ResponseVO.create(ResponseCode.FAIL.getId(), ResponseCode.FAIL.getName(),
                    ResponseCode.FAIL.getName(), null);
        }
    }

    @PostMapping("/identifyFace")
    public ResponseVO<IdentifyFacesResponseVO> identifyFace(@RequestParam("file") MultipartFile file,
                                                             @RequestParam("maxNumberOfCandidates") int maxNumberOfCandidates) throws IOException {
        IdentifyFacesWrapperVO identifyFacesWrapperVO = new IdentifyFacesWrapperVO();
        IdentifyFacesResponseVO identifyFacesResponseVO = new IdentifyFacesResponseVO();

        byte[] image = Base64.encodeBase64(file.getBytes());
        String base64String = new String(image, StandardCharsets.UTF_8);
        ArrayList<String> images = new ArrayList<String>();
        images.add(base64String);
        identifyFacesWrapperVO.setImages(images);
        identifyFacesWrapperVO.setMaxNumberOfCandidates(maxNumberOfCandidates);
        try {
            identifyFacesResponseVO = hertaService.identifyFace(identifyFacesWrapperVO);
            return ResponseVO.create(ResponseCode.SUCCESSFUL.getId(), ResponseCode.SUCCESSFUL.getName(),
                    ResponseCode.SUCCESSFUL.getName(), identifyFacesResponseVO);
        } catch(Exception exception) {
            return ResponseVO.create(ResponseCode.FAIL.getId(), ResponseCode.FAIL.getName(),
                    ResponseCode.FAIL.getName(), null);
        }
    }

    @PostMapping("/enrollFace")
    public ResponseVO<String> enrollSubjectWithAlbum(@RequestBody EnrollSubjectWithAlbumWrapperVO enrollSubjectWithAlbumWrapperVO) throws IOException {

        try {
            String enrollSubjectWithAlbum = hertaService.enrollSubjectWithAlbum(enrollSubjectWithAlbumWrapperVO);
            return ResponseVO.create(ResponseCode.SUCCESSFUL.getId(), ResponseCode.SUCCESSFUL.getName(),
                    ResponseCode.SUCCESSFUL.getName(), enrollSubjectWithAlbum);
        } catch(Exception exception) {
            return ResponseVO.create(ResponseCode.FAIL.getId(), ResponseCode.FAIL.getName(),
                    ResponseCode.FAIL.getName(), null);
        }
    }
}
