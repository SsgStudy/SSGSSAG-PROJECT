package com.ssg.ssgssag.controller;

import com.ssg.ssgssag.dto.IncomingDTO;
import com.ssg.ssgssag.service.IncomingService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Log4j2
@Controller
@RequiredArgsConstructor
@RequestMapping("/incoming")
public class IncomingController {

    private final IncomingService incomingService;

    //입고 현황 리스트 조회
    @GetMapping("/list")
    public String showIncomingListPage(Model model) {
        log.info("incoming controller test");

        List<IncomingDTO> incomingOrderList = incomingService.getAllIncomingProductsWithDetails();
        model.addAttribute("incomingOrderList", incomingOrderList);

        return "incoming/incoming-list";
    }

    //발주 입고 등록
    @GetMapping("/register")
    public String showOrderedIncomingRegisterPage(Model model) {
        log.info("incoming controller test");

        return "incoming/ordered-incoming-register";
    }

    //입고 승인
    @GetMapping("/confirm")
    public String showIncomingConfirmPage() {
        log.info("incoming controller test");

        return "incoming/incoming-confirm";
    }
}
