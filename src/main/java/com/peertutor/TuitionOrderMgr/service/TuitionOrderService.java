package com.peertutor.TuitionOrderMgr.service;

import com.peertutor.TuitionOrderMgr.model.TuitionOrder;
import com.peertutor.TuitionOrderMgr.model.viewmodel.request.TuitionOrderReq;
import com.peertutor.TuitionOrderMgr.model.viewmodel.response.StudentRes;
import com.peertutor.TuitionOrderMgr.model.viewmodel.response.TutorRes;
import com.peertutor.TuitionOrderMgr.repository.TuitionOrderRepository;
import com.peertutor.TuitionOrderMgr.service.dto.TuitionOrderCriteria;
import com.peertutor.TuitionOrderMgr.service.dto.TuitionOrderDTO;
import com.peertutor.TuitionOrderMgr.service.dto.TuitionOrderDetailedDTO;
import com.peertutor.TuitionOrderMgr.service.mapper.TuitionOrderMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TuitionOrderService {

    private static final Logger logger = LoggerFactory.getLogger(TuitionOrder.class);
    @Autowired
    private final TuitionOrderMapper tuitionOrderMapper;
    @Autowired
    private TuitionOrderRepository tuitionOrderRepository;
    @Autowired
    private TuitionOrderQueryService tuitionOrderQueryService;
    @Autowired
    private TutorCalendarService tutorCalendarService;
    @Autowired
    private ExternalCallService externalCallService;

    public TuitionOrderService(TuitionOrderRepository tuitionOrderRepository, TuitionOrderMapper tuitionOrderMapper) {
        this.tuitionOrderRepository = tuitionOrderRepository;
        this.tuitionOrderMapper = tuitionOrderMapper;
    }

    public List<TuitionOrderDTO> getAllTuitionOrder() {
        List<TuitionOrder> orders = tuitionOrderRepository.findAll();

        return orders.stream().map(tuitionOrderMapper::toDto).collect(Collectors.toList());

    }

    public List<TuitionOrderDetailedDTO> getTuitionOrderDetails(String name, String sessionToken) {

        logger.debug("Retrieving all tutor names and ids");
        List<TutorRes> tutorList = externalCallService.getAllTutorName(name, sessionToken);

        logger.debug("Retrieving all student names and ids");
        List<StudentRes> studentList = externalCallService.getAllStudentName(name, sessionToken);

        // convert to map for processing
        Map<Long, String> studentNameIdMap = studentList.stream()
                .collect(Collectors.toMap(
                        StudentRes::getId, StudentRes::getDisplayName));

        List<TuitionOrderDTO> tuitionOrders = getAllTuitionOrder();

        // convert to map for processing
        Map<Long, String> tutorNameIdMap = tutorList.stream()
                .collect(Collectors.toMap(
                        TutorRes::getId, TutorRes::getDisplayName));

        // manual join table.........
        logger.debug("Combine multiple tables - student, tutor, tuition order");
        List<TuitionOrderDetailedDTO> detailedOrders = tuitionOrders.stream().map(order -> {
            TuitionOrderDetailedDTO newOrder = new TuitionOrderDetailedDTO();

            newOrder.setId(order.getId());

            newOrder.setTutorId(order.getTutorId());
            newOrder.setTutorName(tutorNameIdMap.get(order.getTutorId()));

            newOrder.setStudentId(order.getStudentId());
            newOrder.setStudentName(studentNameIdMap.get(order.getStudentId()));
            newOrder.setSelectedDates(order.getSelectedDates());

            newOrder.setStatus(order.getStatus());

            return newOrder;
        }).collect(Collectors.toList());
        logger.debug("Successfully joined data");
        return detailedOrders;
    }

    public Page<TuitionOrderDTO> getTuitionOrderByCriteria(TuitionOrderCriteria criteria, Pageable pageable) {
        Page<TuitionOrderDTO> page = tuitionOrderQueryService.findByCriteria(criteria, pageable);
        return page;
    }

    public TuitionOrderDTO createTuitionOrder(TuitionOrderReq req) {
        TuitionOrder tuitionOrder = new TuitionOrder();

        if (req.id != null) {
            tuitionOrderRepository.findById(req.id);
        }

        tuitionOrder.setStatus(req.status);
        tuitionOrder.setStudentId(req.studentId);
        tuitionOrder.setTutorId(req.tutorId);

        Collections.sort(req.selectedDates);

        List<Date> availableDates = tutorCalendarService.getTutorCalendar(req.name, req.sessionToken, req.tutorId);
        String selectedDates = String.join(";", req.selectedDates.toString());
        if (req.status != null && req.status != 2) {
            if (!availableDates.containsAll(req.selectedDates)) {
                logger.info("Tutor is not available on selected dates");
                return null;
            } else {
                logger.info("Tutor is available on selected dates");
                
            }
        }

        tuitionOrder.setSelectedDates(selectedDates);

        try {
            tuitionOrder = tuitionOrderRepository.save(tuitionOrder);
        } catch (Exception e) {
            logger.error("TuitionOrder Profile Creation Failed: " + e.getMessage());
            return null;
        } finally {
            if (req.id != null && req.status == 1) {
                removeConflictTuitionOrder(req.selectedDates, req.tutorId);
                tutorCalendarService.deleteTutorCalendar(req.name, req.sessionToken, req.tutorId, selectedDates);
            }
        }

        TuitionOrderDTO result = tuitionOrderMapper.toDto(tuitionOrder);

        return result;
    }

    public TuitionOrderDTO getTuitionOrderById(Long id) {
        Optional<TuitionOrder> tuitionOrder = tuitionOrderRepository.findById(id);

        if (!tuitionOrder.isPresent()) {
            return null;
        }
        TuitionOrderDTO result = tuitionOrderMapper.toDto(tuitionOrder.get());

        return result;
    }

    public void removeConflictTuitionOrder(List<Date> selectedDates, Long tutorId) {
        List<String> dates = selectedDates.stream().map(date -> {
            return date.toString();
        }).collect(Collectors.toList());

        dates.forEach(date -> {
            List<TuitionOrder> orders =
                    tuitionOrderRepository.findBySelectedDatesContainingAndStatusAndTutorId(date, 0, tutorId);
            orders.forEach(order -> {
                try {
                    order.setStatus(2);
                    tuitionOrderRepository.save(order);
                } catch (Exception e) {
                    logger.error("TuitionOrder Profile Update Failed: " + e.getMessage());
                }
            });
        });
    }

}
