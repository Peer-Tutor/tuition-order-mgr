package com.peertutor.TuitionOrderMgr.model.viewmodel.request;

import javax.persistence.Column;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.sql.Timestamp;

public class TuitionOrderReq {
    @NotNull
    @NotEmpty
    public String name;

    @NotNull
    @NotEmpty
    public String sessionToken;

    public Long studentId;

    public Long tutorId;

    public Timestamp startTime;

    public Timestamp endTime;

    public Integer status;
}
