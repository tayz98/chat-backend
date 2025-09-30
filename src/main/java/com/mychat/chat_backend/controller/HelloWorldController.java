package com.mychat.chat_backend.controller;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

// just a dummy controller to test the application
@RestController
public class HelloWorldController {
    @GetMapping("/")
    public String hello() {
        return "Hello World!";
    }
}
