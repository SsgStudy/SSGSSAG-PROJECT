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
    public String selectOne() {
        log.info("incoming controller test");

        return "incoming/incoming-list";
    }
}
