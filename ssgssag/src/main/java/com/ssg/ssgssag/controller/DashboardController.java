package com.ssg.ssgssag.controller;

import com.ssg.ssgssag.dto.IncomingDTO;
import com.ssg.ssgssag.dto.StatusCountDTO;
import com.ssg.ssgssag.service.DashboardService;
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

    private final DashboardService dashboardService;

    @GetMapping()
    public String showDashboardPage(Model model) {
        log.info("Load dashboard page");

        List<StatusCountDTO> statusCountDTOList = dashboardService.getAllStatusCount();

        model.addAttribute("incoming", statusCountDTOList.get(0).getCnt());
        model.addAttribute("outgoing", statusCountDTOList.get(1).getCnt());
        model.addAttribute("purchase", statusCountDTOList.get(2).getCnt());
        model.addAttribute("order", statusCountDTOList.get(3).getCnt());
        model.addAttribute("inventory", statusCountDTOList.get(4).getCnt());
        model.addAttribute("warehouse", statusCountDTOList.get(5).getCnt());

        return "main/main";
    }
}
