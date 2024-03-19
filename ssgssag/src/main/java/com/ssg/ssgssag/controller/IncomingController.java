package com.ssg.ssgssag.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Log4j2
@Controller
@RequiredArgsConstructor
@RequestMapping("/incoming")
public class IncomingController {
    @GetMapping("/list")
    public String showIncomingListPage() {
        log.info("incoming controller test");

        return "incoming/incoming-list";
    }
    @GetMapping("/register")
    public String showOrderedIncomingRegisterPage() {
        log.info("incoming controller test");

        return "incoming/ordered-incoming-register";
    }
    @GetMapping("/confirm")
    public String showIncomingConfirmPage() {
        log.info("incoming controller test");

        return "incoming/incoming-confirm";
    }
}
