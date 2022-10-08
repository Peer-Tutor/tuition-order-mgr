package com.peertutor.TuitionOrderMgr.model.viewmodel.request;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class TutorReq {
    @NotNull
    @NotEmpty
    public Integer page;

    @NotNull
    @NotEmpty
    public Integer size;


    public TutorReq(Integer page, Integer size) {
        this.page = page;
        this.size = size;
    }
}
