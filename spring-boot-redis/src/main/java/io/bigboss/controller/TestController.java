package io.bigboss.controller;

import io.bigboss.service.MessagePublisher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

@RestController
public class TestController {

    @Autowired
    private MessagePublisher redisPublisher;

    @GetMapping("/test")
    public String test() {
        String message = "Hello, World! - " + new Date().toString();
        redisPublisher.publish(message);
        return message;
    }
}
