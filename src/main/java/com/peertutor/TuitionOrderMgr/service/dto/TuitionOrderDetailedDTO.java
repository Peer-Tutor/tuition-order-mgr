package com.peertutor.TuitionOrderMgr.service.dto;

public class TuitionOrderDetailedDTO extends TuitionOrderDTO {

    public TuitionOrderDetailedDTO(){
        super();
    }
    public String studentName;

    public String tutorName;

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public String getTutorName() {
        return tutorName;
    }

    public void setTutorName(String tutorName) {
        this.tutorName = tutorName;
    }

    @Override
    public String toString() {
        return "TuitionOrderDetailedDTO{" +
                super.toString() +
                "studentName='" + studentName + '\'' +
                ", tutorName='" + tutorName + '\'' +
                '}';
    }
}
