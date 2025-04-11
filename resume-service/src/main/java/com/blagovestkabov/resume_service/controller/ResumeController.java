package com.blagovestkabov.resume_service.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/resume")
public class ResumeController {

    @GetMapping("/test")
    public String testMethod() {
        return "This is just a test.";
    }

}
