package com.uber.driverfinder.controller;

import com.uber.driverfinder.dto.TestRequestDTO;
import com.uber.driverfinder.dto.TestResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;

@Controller
public class TestController {

    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;

    @MessageMapping("/ping")
    @SendTo("/topic/ping")
    public TestResponseDTO ping(TestRequestDTO dto) {
        System.out.println("Received msg: " + dto.getData());

        return new TestResponseDTO().builder().data("Data Received").build();
    }

    @Scheduled(fixedDelay = 2000)
    public void schedule() {
        this.simpMessagingTemplate.convertAndSend("/topic/schedule", "Scheduled task");
    }
}
