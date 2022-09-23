package com.peertutor.TuitionOrderMgr.service;

import com.peertutor.TuitionOrderMgr.model.TuitionOrder;
import com.peertutor.TuitionOrderMgr.model.viewmodel.request.TuitionOrderReq;
import com.peertutor.TuitionOrderMgr.repository.TuitionOrderRepository;
import com.peertutor.TuitionOrderMgr.service.dto.TuitionOrderCriteria;
import com.peertutor.TuitionOrderMgr.service.dto.TuitionOrderDTO;
import com.peertutor.TuitionOrderMgr.service.mapper.TuitionOrderMapper;
import io.github.jhipster.web.util.PaginationUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.List;

@Service
public class TuitionOrderService {

    private static final Logger logger = LoggerFactory.getLogger(TuitionOrder.class);
    @Autowired
    private final TuitionOrderMapper tuitionOrderMapper;
    @Autowired
    private TuitionOrderRepository tuitionOrderRepository;
    @Autowired
    private TuitionOrderQueryService tuitionOrderQueryService;

    public TuitionOrderService(TuitionOrderRepository tuitionOrderRepository, TuitionOrderMapper tuitionOrderMapper) {
        this.tuitionOrderRepository = tuitionOrderRepository;
        this.tuitionOrderMapper = tuitionOrderMapper;
    }

    public Page<TuitionOrderDTO> getTuitionOrderByCriteria(TuitionOrderCriteria criteria, Pageable pageable) {
        Page<TuitionOrderDTO> page = tuitionOrderQueryService.findByCriteria(criteria, pageable);
        return page;
    }

    public TuitionOrderDTO createTuitionOrder(TuitionOrderReq req) {
        TuitionOrder tuitionOrder = tuitionOrderRepository.findByStudentIdAndTutorIdAndStartTimeAndEndTime(req.studentId, req.tutorId, req.startTime, req.endTime);

        if (tuitionOrder == null) {
            tuitionOrder = new TuitionOrder();
        }

        tuitionOrder.setEndTime(req.endTime);
        tuitionOrder.setStatus(req.status);
        tuitionOrder.setStudentId(req.studentId);
        tuitionOrder.setTutorId(req.tutorId);
        tuitionOrder.setStartTime(req.startTime);

        try {
            tuitionOrder = tuitionOrderRepository.save(tuitionOrder);
        } catch (Exception e) {
            logger.error("TuitionOrder Profile Creation Failed: " + e.getMessage());
            return null;
        }

        TuitionOrderDTO result = tuitionOrderMapper.toDto(tuitionOrder);

        return result;
    }

}
