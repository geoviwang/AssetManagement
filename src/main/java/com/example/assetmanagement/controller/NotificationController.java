package com.example.assetmanagement.controller;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
public class NotificationController {

    @MessageMapping("/update")
    @SendTo("/topic/amountUpdate")
    public String sendNotification(String message) {
        return message;
    }
}
