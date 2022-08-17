package com.kcs.attendancesystem.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kcs.attendancesystem.dto.DetectFacesResponseVO;
import com.kcs.attendancesystem.dto.DetectFacesWrapperVO;
import com.kcs.attendancesystem.dto.EnrollSubjectWithAlbumWrapperVO;
import com.kcs.attendancesystem.dto.IdentifyFacesResponseVO;
import com.kcs.attendancesystem.dto.IdentifyFacesWrapperVO;
import com.kcs.attendancesystem.service.HertaService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.concurrent.TimeUnit;

@Slf4j
@Service
public class HertaServiceImpl implements HertaService {

    @Autowired
    private RestTemplate restTemplate;

    @Value("${herta.base.url}")
    String hertaBaseUrl;

    @Value("${herta.face.detection.threshold}")
    private int faceDetectionThreshold;

    @Value("${herta.face.quality.threshold}")
    private int faceQualityThreshold;

    @Value("${herta.minimum.face.size}")
    private int minimumFaceSize;

    ObjectMapper objectMapper;

    @Override
    public DetectFacesResponseVO detectFaces(DetectFacesWrapperVO detectFacesWrapperVO) {
        long startTime = System.nanoTime();
        objectMapper = new ObjectMapper();
        DetectFacesResponseVO detectFacesResponseVO = null;
        String url = hertaBaseUrl + "/DetectFaces";

        try {
            HttpEntity<Object> requestEntity = new HttpEntity<>(detectFacesWrapperVO);
            ResponseEntity<String> restResponse = restTemplate.exchange(url, HttpMethod.POST, requestEntity, String.class);
            String response = restResponse.getBody();
            objectMapper.configure(MapperFeature.ACCEPT_CASE_INSENSITIVE_PROPERTIES, true);
            JsonNode responseObj = objectMapper.readTree(response).findValue("DetectFacesResult");
            detectFacesResponseVO = objectMapper.convertValue(responseObj.get(0), DetectFacesResponseVO.class);
        } catch (JsonProcessingException exception) {
            log.error("Exception ", exception);
        }
        long stopTime = System.nanoTime();
        log.info("Exit from detectFaces Method at " + TimeUnit.MILLISECONDS.convert( stopTime - startTime, TimeUnit.NANOSECONDS) + " ms");
        return detectFacesResponseVO;
    }


    @Override
    public IdentifyFacesResponseVO identifyFace(IdentifyFacesWrapperVO identifyFacesWrapperVO) {
        long startTime = System.nanoTime();
        objectMapper = new ObjectMapper();
        IdentifyFacesResponseVO identifyFacesResponseVO = null;
        String url = hertaBaseUrl + "/IdentifySubject";

        try {
            HttpEntity<Object> requestEntity = new HttpEntity<>(identifyFacesWrapperVO);
            ResponseEntity<String> restResponse = restTemplate.exchange(url, HttpMethod.POST, requestEntity, String.class);
            String response = restResponse.getBody();
            objectMapper.configure(MapperFeature.ACCEPT_CASE_INSENSITIVE_PROPERTIES, true);
            JsonNode responseObj = objectMapper.readTree(response);
            identifyFacesResponseVO = objectMapper.convertValue(responseObj, IdentifyFacesResponseVO.class);
        } catch (JsonProcessingException exception) {
            log.error("Exception ", exception);
        }
        long stopTime = System.nanoTime();
        log.info("Exit from identifyFace Method at " + TimeUnit.MILLISECONDS.convert( stopTime - startTime, TimeUnit.NANOSECONDS) + " ms");
        return identifyFacesResponseVO;
    }

    @Override
    public String enrollSubjectWithAlbum(EnrollSubjectWithAlbumWrapperVO enrollSubjectWithAlbumWrapperVO) {
        long startTime = System.nanoTime();
        objectMapper = new ObjectMapper();
        String url = hertaBaseUrl + "/EnrollSubjectWithAlbum";
        String response = null;

        try {
            HttpEntity<Object> requestEntity = new HttpEntity<>(enrollSubjectWithAlbumWrapperVO);
            ResponseEntity<String> restResponse = restTemplate.exchange(url, HttpMethod.POST, requestEntity, String.class);
            response = restResponse.getBody();
        } catch (Exception exception) {
            log.error("Exception ", exception);
        }
        long stopTime = System.nanoTime();
        log.info("Exit from enrollSubjectWithAlbum Method at " + TimeUnit.MILLISECONDS.convert( stopTime - startTime, TimeUnit.NANOSECONDS) + " ms");
        return response;
    }
}
