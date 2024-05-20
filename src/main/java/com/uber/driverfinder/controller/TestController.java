package com.uber.driverfinder.controller;

import com.uber.driverfinder.dto.RequestDriverDTO;
import com.uber.driverfinder.dto.TestRequestDTO;
import com.uber.driverfinder.dto.TestResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/driver")
public class TestController {

    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;

    @MessageMapping("/ping")
    @SendTo("/topic/ping")
    public TestResponseDTO ping(TestRequestDTO dto) {
        System.out.println("Received msg: " + dto.getData());

        return new TestResponseDTO().builder().data("Data Received").build();
    }

    @MessageMapping("/accept/{driverId}")
    public void receiveDriverResponse(@DestinationVariable String driverId) {
        //Call BookingService API to update the issue
    }

    @Scheduled(fixedDelay = 2000)
    public void schedule() {
        this.simpMessagingTemplate.convertAndSend("/topic/schedule", "Scheduled task");
    }

    @PostMapping("/request")
    public ResponseEntity<Boolean> requestDrivers(@RequestBody RequestDriverDTO dto) {
        System.out.println("In requestDrivers");
        dto.getDriverIds().stream().forEach(saveDriverLocationDto -> {
            this.simpMessagingTemplate.convertAndSend("/topic/ride/{saveDriverLocationDto.driverId}", "Ride received");
        });
        return new ResponseEntity<>(true, HttpStatus.OK);
    }
}
