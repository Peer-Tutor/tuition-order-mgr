package com.peertutor.TuitionOrderMgr.model.viewmodel.response;

import com.peertutor.TuitionOrderMgr.service.dto.TuitionOrderDTO;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class TuitionOrderRes {
    public Long id;

    public Long studentId;

    public Long tutorId;

    public List<Date> selectedDates;

    public int status;

    public TuitionOrderRes(TuitionOrderDTO tuitionOrderDTO) {
        this.id = tuitionOrderDTO.getId();
        this.studentId = tuitionOrderDTO.getStudentId();
        this.tutorId = tuitionOrderDTO.getTutorId();
        this.status = tuitionOrderDTO.getStatus();

        List<Date> result = Arrays.asList(tuitionOrderDTO.getSelectedDates().replaceAll("[()\\[\\]]", "").split(",")).stream().map(
                date -> {
                    try {
                        return new SimpleDateFormat("yyyy-MM-dd").parse(date);
                    } catch (ParseException e) {
                        throw new RuntimeException(e);
                    }
                }
        ).collect(Collectors.toList());
        this.selectedDates = result;
    }
}
