package com.uber.driverfinder.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.uber.driverfinder.dto.*;
import com.uber.driverfinder.services.MessageSender;
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

import java.io.IOException;

@RestController
@RequestMapping("/api/v1/driver")
public class TestController {

    ObjectMapper mapper = new ObjectMapper();

    @Autowired
    private MessageSender messageSender;

    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;

    @MessageMapping("/ping")
    @SendTo("/topic/ping")
    public TestResponseDTO ping(TestRequestDTO dto) {
        System.out.println("Received msg: " + dto.getData());

        return new TestResponseDTO().builder().data("Data Received").build();
    }

    @MessageMapping("/accept")
    public void receiveDriverResponse(UpdateBookingDTO dto) {

        System.out.println("Ride accepted by : " + dto.getDriverId());
        try {
            String json = mapper.writeValueAsString(dto);

            this.messageSender.send(json);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Scheduled(fixedDelay = 2000)
    public void schedule() {
        this.simpMessagingTemplate.convertAndSend("/topic/schedule", "Scheduled task");
    }

    @PostMapping("/request")
    public ResponseEntity<Boolean> requestDrivers(@RequestBody RequestDriverDTO dto) {
        System.out.println("In requestDrivers");
        for(SaveDriverLocationDto saveDriverLocationDto : dto.getDriverIds()) {
            String driverId = saveDriverLocationDto.getDriverId();

            this.simpMessagingTemplate.convertAndSend("/topic/ride/" + driverId, dto.getBookingId());
        }
        return new ResponseEntity<>(true, HttpStatus.OK);
    }
}
