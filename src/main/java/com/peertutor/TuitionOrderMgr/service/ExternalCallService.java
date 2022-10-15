package com.peertutor.TuitionOrderMgr.service;

import com.peertutor.TuitionOrderMgr.model.viewmodel.request.AuthenticationReq;
import com.peertutor.TuitionOrderMgr.model.viewmodel.request.TutorReq;
import com.peertutor.TuitionOrderMgr.model.viewmodel.response.StudentRes;
import com.peertutor.TuitionOrderMgr.model.viewmodel.response.TutorRes;
import com.peertutor.TuitionOrderMgr.util.AppConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

@Service
public class ExternalCallService {

    @Autowired
    AppConfig appConfig;

    public List<TutorRes> getAllTutorName(String name, String sessionToken) {

        String url = appConfig.getTutorMgr().get("url");

        String endpoint = url + "/tutors";

        Integer page = 0;
        // potential problem: pagination limit. existing api design doesn't return all info
        Integer size = 50;

        String urlTemplate = UriComponentsBuilder.fromHttpUrl(endpoint)
                .queryParam("name", name)
                .queryParam("sessionToken", sessionToken)
                .queryParam("page", page)
                .queryParam("size", size)
//                .encode()
                .toUriString();

        System.out.println("urlTemplate" + urlTemplate);

        MultiValueMap<String, String> headers = new LinkedMultiValueMap<String, String>();
        headers.add("Content-Type", "application/json");

        RestTemplate restTemplate = new RestTemplate();
        restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());

        TutorReq req = new TutorReq(page, size);
        HttpEntity<TutorReq> request = new HttpEntity<TutorReq>(req, headers);

        ResponseEntity<List<TutorRes>> response = restTemplate.exchange(urlTemplate,
                HttpMethod.GET, request, new ParameterizedTypeReference<List<TutorRes>>() {
                });

        List<TutorRes> result = response.getBody();

        return result;
    }

    public List<StudentRes> getAllStudentName(String name, String sessionToken) {

        String url = appConfig.getStudentMgr().get("url");

        String endpoint = url + "/students";

        String urlTemplate = UriComponentsBuilder.fromHttpUrl(endpoint)
                .queryParam("name", name)
                .queryParam("sessionToken", sessionToken)
//                .encode()
                .toUriString();

        System.out.println("urlTemplate" + urlTemplate);

        MultiValueMap<String, String> headers = new LinkedMultiValueMap<String, String>();
        headers.add("Content-Type", "application/json");

        RestTemplate restTemplate = new RestTemplate();
        restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());

        ResponseEntity<List<StudentRes>> response = restTemplate.exchange(urlTemplate,
                HttpMethod.GET, null, new ParameterizedTypeReference<List<StudentRes>>() {
                });

        List<StudentRes> result = response.getBody();

        return result;
    }

}
