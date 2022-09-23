package com.peertutor.TuitionOrderMgr.model.viewmodel.response;

import com.peertutor.TuitionOrderMgr.service.dto.TuitionOrderDTO;

import javax.validation.constraints.NotNull;
import java.sql.Timestamp;

public class TuitionOrderRes {
    public Long id;

    public Long studentId;

    public Long tutorId;

    public Timestamp startTime;

    public Timestamp endTime;

    public int status;

    public TuitionOrderRes(TuitionOrderDTO tuitionOrderDTO) {
        this.id = tuitionOrderDTO.getId();
        this.studentId = tuitionOrderDTO.getStudentId();
        this.tutorId = tuitionOrderDTO.getTutorId();
        this.startTime = tuitionOrderDTO.getStartTime();
        this.endTime = tuitionOrderDTO.getEndTime();
        this.status = tuitionOrderDTO.getStatus();
    }
}
