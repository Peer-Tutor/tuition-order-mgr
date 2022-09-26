package com.peertutor.TuitionOrderMgr.service.dto;

import com.peertutor.TuitionOrderMgr.model.viewmodel.request.TuitionOrderReq;
import io.github.jhipster.service.Criteria;
import io.github.jhipster.service.filter.*;

import java.io.Serializable;
import java.util.Objects;
import java.util.Optional;


public class TuitionOrderCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private LongFilter studentId;

    private LongFilter tutorId;

    private IntegerFilter status;

    public TuitionOrderCriteria() {
    }

    public TuitionOrderCriteria(Optional<Long> studentId, Optional<Long> tutorId,
                                Optional<Integer> status) {
        if (studentId.isPresent()) {
            this.studentId = new LongFilter();
            this.studentId.setEquals(studentId.get());
        }

        if (tutorId.isPresent()) {
            this.tutorId = new LongFilter();
            this.tutorId.setEquals(tutorId.get());
        }

        if (status.isPresent()) {
            this.status = new IntegerFilter();
            this.status.setEquals(status.get());
        }
    }

    public TuitionOrderCriteria(TuitionOrderCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.studentId = other.studentId == null ? null : other.studentId.copy();
        this.tutorId = other.tutorId == null ? null : other.tutorId.copy();
        this.status = other.status == null ? null : other.status.copy();
    }

    public TuitionOrderCriteria(TuitionOrderReq req) {
        if (req.studentId != null) {
            this.studentId = new LongFilter();
            this.studentId.setEquals(req.studentId);
        }

        if (req.tutorId != null) {
            this.tutorId = new LongFilter();
            this.tutorId.setEquals(req.tutorId);
        }

        if (req.status != null) {
            this.status = new IntegerFilter();
            this.status.setEquals(req.status);
        }
    }

    @Override
    public TuitionOrderCriteria copy() {
        return new TuitionOrderCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public LongFilter getTutorId() {
        return tutorId;
    }

    public void setTutorId(LongFilter tutorId) {
        this.tutorId = tutorId;
    }

    public IntegerFilter getStatus() {
        return status;
    }

    public void setStatus(IntegerFilter status) {
        this.status = status;
    }

    public LongFilter getStudentId() {
        return studentId;
    }

    public void setStudentId(LongFilter studentId) {
        this.studentId = studentId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TuitionOrderCriteria that = (TuitionOrderCriteria) o;
        return Objects.equals(id, that.id) && Objects.equals(studentId, that.studentId) && Objects.equals(tutorId, that.tutorId) && Objects.equals(status, that.status);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, studentId, tutorId, status);
    }

    @Override
    public String toString() {
        return "TuitionOrderCriteria{" +
                "id=" + id +
                ", studentId=" + studentId +
                ", tutorId=" + tutorId +
                ", status=" + status +
                '}';
    }
}
