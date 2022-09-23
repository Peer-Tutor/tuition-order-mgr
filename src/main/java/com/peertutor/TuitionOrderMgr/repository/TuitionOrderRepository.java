package com.peertutor.TuitionOrderMgr.repository;

import com.peertutor.TuitionOrderMgr.model.TuitionOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;

@Repository
public interface TuitionOrderRepository extends JpaRepository<TuitionOrder, Long>, JpaSpecificationExecutor<TuitionOrder> {
    TuitionOrder findByStudentIdAndTutorIdAndStartTimeAndEndTime(long studentId, long tutorId, Timestamp startTime, Timestamp endTime);
}

