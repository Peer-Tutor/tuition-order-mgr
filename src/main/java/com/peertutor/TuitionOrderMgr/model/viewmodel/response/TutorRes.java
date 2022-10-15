package com.peertutor.TuitionOrderMgr.model.viewmodel.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class TutorRes {
    public Long id;
    public String displayName;

    public Long getId() {
        return id;
    }

    public String getDisplayName() {
        return displayName;
    }

    public TutorRes(@JsonProperty("id") Long id,
                    @JsonProperty("displayName") String displayName) {
        this.id = id;
        this.displayName = displayName;
    }

    @Override
    public String toString() {
        return "TutorRes{" +
                "id=" + id +
                ", displayName='" + displayName + '\'' +
                '}';
    }
}
