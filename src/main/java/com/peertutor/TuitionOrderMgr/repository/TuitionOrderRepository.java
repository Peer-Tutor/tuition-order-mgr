package com.peertutor.TuitionOrderMgr.repository;

import com.peertutor.TuitionOrderMgr.model.TuitionOrder;
//import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TuitionOrderRepository extends JpaRepository<TuitionOrder, Long> {
    List<TuitionOrder> findByLastName(String lastName);
    TuitionOrder findById(long id);
}

