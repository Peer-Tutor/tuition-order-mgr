package com.peertutor.TuitionOrderMgr.controller;

import com.peertutor.TuitionOrderMgr.model.viewmodel.request.TuitionOrderReq;
import com.peertutor.TuitionOrderMgr.model.viewmodel.response.TuitionOrderRes;
import com.peertutor.TuitionOrderMgr.repository.TuitionOrderRepository;
import com.peertutor.TuitionOrderMgr.service.AuthService;
import com.peertutor.TuitionOrderMgr.service.TuitionOrderService;
import com.peertutor.TuitionOrderMgr.service.dto.TuitionOrderCriteria;
import com.peertutor.TuitionOrderMgr.service.dto.TuitionOrderDTO;
import com.peertutor.TuitionOrderMgr.util.AppConfig;
import io.github.jhipster.web.util.PaginationUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping(path = "/tuition-order-mgr")
public class TuitionOrderController {
    @Autowired
    AppConfig appConfig;
    @Autowired
    private TuitionOrderRepository tuitionOrderRepository;
    @Autowired
    private TuitionOrderService tuitionOrderService;
    @Autowired
    private AuthService authService;

    @GetMapping(path = "/health")
    public @ResponseBody String healthCheck() {
        return "Ok";
    }

    @PostMapping(path = "/tuitionOrder")
    public @ResponseBody ResponseEntity<TuitionOrderRes> createTuitionProfile(@RequestBody @Valid TuitionOrderReq req) {
        boolean result = authService.getAuthentication(req.name, req.sessionToken);
        if (!result) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }

        TuitionOrderDTO savedTutionOrder;

        savedTutionOrder = tuitionOrderService.createTuitionOrder(req);

        if (savedTutionOrder == null) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(null);
        }

        TuitionOrderRes res = new TuitionOrderRes(savedTutionOrder);

        return ResponseEntity.ok().body(res);
    }

    @GetMapping("/tuitionOrders")
    public ResponseEntity<List<TuitionOrderDTO>> getTuitionOrderByCriteria(
            @RequestParam(name = "name") String name,
            @RequestParam(name = "sessionToken") String sessionToken,
            @RequestParam(name = "studentId") Optional<Long> studentId,
            @RequestParam(name = "tutorId") Optional<Long> tutorId,
            @RequestParam(name = "startTime") Optional<Timestamp> startTime,
            @RequestParam(name = "endTime") Optional<Timestamp> endTime,
            @RequestParam(name = "status") Optional<Integer> status,
            Pageable pageable
    ) {
        boolean result = authService.getAuthentication(name, sessionToken);
        if (!result) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }

        TuitionOrderCriteria criteria = new TuitionOrderCriteria(studentId, tutorId, status);
        Page<TuitionOrderDTO> page = tuitionOrderService.getTuitionOrderByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    @GetMapping("/tuitionOrder")
    public ResponseEntity<TuitionOrderDTO> getTuitionOrderByCriteria(
            @RequestParam(name = "name") String name,
            @RequestParam(name = "sessionToken") String sessionToken,
            @RequestParam(name = "id") Long id
    ) {
        boolean result = authService.getAuthentication(name, sessionToken);
        if (!result) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }

        TuitionOrderDTO tuitionOrder = tuitionOrderService.getTuitionOrderById(id);

        if (tuitionOrder == null) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
        }
        return ResponseEntity.ok().body(tuitionOrder);
    }
}
