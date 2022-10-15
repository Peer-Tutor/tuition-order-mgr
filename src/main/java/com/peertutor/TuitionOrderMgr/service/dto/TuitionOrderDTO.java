package com.peertutor.TuitionOrderMgr.service.dto;

import com.peertutor.TuitionOrderMgr.model.TuitionOrder;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Objects;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TuitionOrderDTO implements Serializable {

    private Long id;

    private Long studentId;

    private Long tutorId;

    private String selectedDates;

    private int status;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getStudentId() {
        return studentId;
    }

    public void setStudentId(Long studentId) {
        this.studentId = studentId;
    }

    public Long getTutorId() {
        return tutorId;
    }

    public void setTutorId(Long tutorId) {
        this.tutorId = tutorId;
    }


    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        TuitionOrder account = (TuitionOrder) o;
        if (account.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), account.getId());
    }

    @Override
    public String toString() {
        return "TuitionOrderDTO{" +
                "id=" + id +
                ", studentId=" + studentId +
                ", tutorId=" + tutorId +
                ", selectedDates=" + selectedDates +
                ", status=" + status +
                '}';
    }


    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    public String getSelectedDates() {
        return selectedDates;
    }

    public void setSelectedDates(String selectedDates) {
        this.selectedDates = selectedDates;
    }
}
