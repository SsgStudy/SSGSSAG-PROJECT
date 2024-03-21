package com.ssg.ssgssag.controller;

import com.ssg.ssgssag.dto.IncomingDTO;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Log4j2
@Controller
@RequiredArgsConstructor
@RequestMapping("/")
public class DashboardController {

    @GetMapping()
    public String showDashboardPage(Model model) {
        log.info("Load dashboard page");

        return "main/main";
    }
}
