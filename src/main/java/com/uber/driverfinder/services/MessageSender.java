package com.uber.driverfinder.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;

@Service
public class MessageSender {

    @Autowired
    private JmsTemplate jmsTemplate;

    public void send(String msg) {
        jmsTemplate.convertAndSend("defaultQ", msg);
    }
}
