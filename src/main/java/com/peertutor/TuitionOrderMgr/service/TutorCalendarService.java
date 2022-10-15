package com.peertutor.TuitionOrderMgr.service;

import com.peertutor.TuitionOrderMgr.model.viewmodel.response.TutorCalendarRes;
import com.peertutor.TuitionOrderMgr.util.AppConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.sql.Date;
import java.util.List;

@Service
public class TutorCalendarService {

    @Autowired
    AppConfig appConfig;

    public List<Date> getTutorCalendar(String name, String sessionToken, Long tutorId) {
        String url = appConfig.getTutorCalendarMgr().get("url");
        String port = appConfig.getTutorCalendarMgr().get("port");

        String endpoint = url + "/calendar?name=" + name + "&sessionToken=" + sessionToken + "&tutorId=" + tutorId;

        MultiValueMap<String, String> headers = new LinkedMultiValueMap<String, String>();
        headers.add("Content-Type", "application/json");

        RestTemplate restTemplate = new RestTemplate();
        restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());

        TutorCalendarRes result = (TutorCalendarRes) restTemplate.getForObject(endpoint, TutorCalendarRes.class);
        return result.availableDate;
    }

    public void deleteTutorCalendar(String name, String sessionToken, Long tutorId, String dates) {
        String url = appConfig.getTutorCalendarMgr().get("url");
        String port = appConfig.getTutorCalendarMgr().get("port");

        String endpoint = url + "/calendar?name=" + name + "&sessionToken=" + sessionToken + "&tutorId=" + tutorId
                + "&dates=" + dates;

        MultiValueMap<String, String> headers = new LinkedMultiValueMap<String, String>();
        headers.add("Content-Type", "application/json");

        RestTemplate restTemplate = new RestTemplate();
        restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());

        restTemplate.delete(endpoint);
    }
}
