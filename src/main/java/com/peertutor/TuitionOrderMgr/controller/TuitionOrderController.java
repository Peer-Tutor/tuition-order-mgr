package com.peertutor.TuitionOrderMgr.controller;

import com.peertutor.TuitionOrderMgr.model.viewmodel.request.TuitionOrderReq;
import com.peertutor.TuitionOrderMgr.model.viewmodel.response.StudentRes;
import com.peertutor.TuitionOrderMgr.model.viewmodel.response.TuitionOrderRes;
import com.peertutor.TuitionOrderMgr.model.viewmodel.response.TutorRes;
import com.peertutor.TuitionOrderMgr.repository.TuitionOrderRepository;
import com.peertutor.TuitionOrderMgr.service.AuthService;
import com.peertutor.TuitionOrderMgr.service.ExternalCallService;
import com.peertutor.TuitionOrderMgr.service.TuitionOrderService;
import com.peertutor.TuitionOrderMgr.service.dto.TuitionOrderCriteria;
import com.peertutor.TuitionOrderMgr.service.dto.TuitionOrderDTO;
import com.peertutor.TuitionOrderMgr.service.dto.TuitionOrderDetailedDTO;
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
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.logging.Logger;
import java.util.stream.Collectors;

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

    @Autowired
    private ExternalCallService externalCallService;

    @GetMapping(path = "/health")
    public @ResponseBody String healthCheck() {
        return "Ok";
    }

    @GetMapping(path = "/test")
    public @ResponseBody String test(
            @RequestParam(name = "name") String name,
            @RequestParam(name = "sessionToken") String sessionToken) {
        List<TutorRes> res = externalCallService.getAllTutorName(name, sessionToken);
        List<StudentRes> res2 = externalCallService.getAllStudentName(name, sessionToken);
        System.out.println("making api call");
        System.out.println(res2);

        return "";
    }

    @PostMapping(path = "/tuitionOrder")
    public @ResponseBody ResponseEntity<TuitionOrderRes> createTuitionProfile(@RequestBody @Valid TuitionOrderReq req) {
        boolean result = authService.getAuthentication(req.name, req.sessionToken);
        if (!result) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }

        TuitionOrderDTO savedTuitionOrder;

        savedTuitionOrder = tuitionOrderService.createTuitionOrder(req);

        if (savedTuitionOrder == null) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(null);
        }

        TuitionOrderRes res = new TuitionOrderRes(savedTuitionOrder);

        return ResponseEntity.ok().body(res);
    }

    @GetMapping("/detailedTuitionOrders")
    public ResponseEntity<List<TuitionOrderDetailedDTO>> getAllDetailedTuitionOrders(
            @RequestParam(name = "name") String name,
            @RequestParam(name = "sessionToken") String sessionToken
    ) {
        System.out.println("Authenticating...");
        boolean result = authService.getAuthentication(name, sessionToken);
        if (!result) {
            System.out.println("Not authenticated");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }
        List<TuitionOrderDetailedDTO> tuitionOrders = tuitionOrderService.getTuitionOrderDetails(name, sessionToken);

        System.out.println("finally, "+ tuitionOrders);
        return ResponseEntity.ok().body(tuitionOrders);
    }

    @GetMapping("/tuitionOrders")
    public ResponseEntity<List<TuitionOrderDTO>> getTuitionOrderByCriteria(
            @RequestParam(name = "name") String name,
            @RequestParam(name = "sessionToken") String sessionToken,
            @RequestParam(name = "studentId") Optional<Long> studentId,
            @RequestParam(name = "tutorId") Optional<Long> tutorId,
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
        // get student name

        // get tutor name
        TuitionOrderDTO tuitionOrder = tuitionOrderService.getTuitionOrderById(id);

        if (tuitionOrder == null) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
        }
        return ResponseEntity.ok().body(tuitionOrder);
    }
}
