package com.ssg.ssgssag.controller;

import com.ssg.ssgssag.dto.IncomingDTO;
import com.ssg.ssgssag.dto.IncomingDetailDTO;
import com.ssg.ssgssag.service.IncomingService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

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

        List<IncomingDTO> incomingList = incomingService.getAllIncomingProductsWithDetails();
        model.addAttribute("incomingList", incomingList);


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
    public String showIncomingConfirmPage(Model model) {
        log.info("incoming controller test");

        List<IncomingDTO> incomingList = incomingService.getAllIncomingProgressProductsWithDetails();
        model.addAttribute("incomingList", incomingList);

        return "incoming/incoming-confirm";
    }

    //입고 상세 조회
    @GetMapping("/details/{pkIncomingProductSeq}")
    @ResponseBody
    public IncomingDetailDTO getIncomingDetailByPk(@PathVariable String pkIncomingProductSeq) {
        return incomingService.getIncomingDetailByCode(pkIncomingProductSeq);
    }

    //입고 승인(확정)
    @PostMapping("/confirm")
    @ResponseBody
    public ResponseEntity<?> confirmIncomingProducts(@RequestBody List<String> pkIncomingProductSeqs) {
        incomingService.confirmIncomingProducts(pkIncomingProductSeqs);
        return ResponseEntity.ok().build();
    }
}
