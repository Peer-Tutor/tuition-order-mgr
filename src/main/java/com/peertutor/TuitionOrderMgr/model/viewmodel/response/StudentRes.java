package com.peertutor.TuitionOrderMgr.model.viewmodel.response;

import com.fasterxml.jackson.annotation.JsonProperty;

public class StudentRes {
    private Long id;

    private String displayName;

    public Long getId() {
        return id;
    }

    public String getDisplayName() {
        return displayName;
    }

    public StudentRes(@JsonProperty("id") Long id,
                      @JsonProperty("displayName") String displayName)
    {
        this.id = id;
        this.displayName = displayName;
    }

    @Override
    public String toString() {
        return "StudentRes{" + "id=" + id +
                ", displayName='" + displayName + '\'' +
                '}';
    }
}
