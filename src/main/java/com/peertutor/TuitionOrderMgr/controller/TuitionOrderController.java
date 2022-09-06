package com.peertutor.TuitionOrderMgr.controller;

import com.peertutor.TuitionOrderMgr.model.TuitionOrder;
import com.peertutor.TuitionOrderMgr.repository.TuitionOrderRepository;
import com.peertutor.TuitionOrderMgr.util.AppConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.SpringVersion;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Controller
@RequestMapping(path="/tuition-order-mgr")
public class TuitionOrderController {
    @Autowired
    AppConfig appConfig;
    @Autowired
    private TuitionOrderRepository tuitionOrderRepository;// = new CustomerRepository();
    @GetMapping(path="/")
    public @ResponseBody String defaultResponse(){

        System.out.println("appConfig="+ appConfig.toString());
        System.out.println("ver"+ SpringVersion.getVersion());
        return "Hello world Spring Ver = " + SpringVersion.getVersion() + "From TuitionOrder mgr";

    }
    @GetMapping(path="/public-api")
    public @ResponseBody String callPublicApi() {
        String endpoint = "https://api.publicapis.org/entries"; //url+":"+port;
        System.out.println("endpoint" + endpoint);

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> response = restTemplate.getForEntity(endpoint, String.class);

        return response.toString();
    }

    @GetMapping(path="/call-app-bookmark-mgr")
    public @ResponseBody String callAppTwo() {
        String url = appConfig.getBookmarkMgr().get("url");
        String port = appConfig.getBookmarkMgr().get("port");

        String endpoint = url; //+":"+port + "/";
        System.out.println("endpoint" + endpoint);

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> response = restTemplate.getForEntity(endpoint, String.class);

        return response.toString();
    }
    @GetMapping(path="/health")
    public @ResponseBody String healthCheck(){
        return "Ok";
    }

    @PostMapping(path = "/add")
    public @ResponseBody String addNewCustomer(@RequestBody Map<String, String> customerDTO) {

        // <validation logic here>
        // todo: generalise validation logic

        // <retrieve data from request body>
        System.out.println("customerMap= " +customerDTO);
        String firstName = customerDTO.get("firstName");
        String lastName = customerDTO.get("lastName");
        // create DTO
        TuitionOrder customer = new TuitionOrder(firstName, lastName);

        // dao layer: save object to db
        tuitionOrderRepository.save(customer);

        // todo: better logging
        // todo: generalise response message
        return "Saved";
    }
    @GetMapping(path="/all")
    public @ResponseBody Iterable<TuitionOrder> getAllCustomers (){

        return tuitionOrderRepository.findAll();
    }


}
