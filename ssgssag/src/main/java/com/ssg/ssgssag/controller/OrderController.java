package com.ssg.ssgssag.controller;

import com.ssg.ssgssag.service.OrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

@RequiredArgsConstructor
@Log4j2
@Controller
@RequestMapping("/order")
public class OrderController {

    @Autowired
    private final OrderService orderService;

    @ResponseBody
    @GetMapping("/order-seq")
    public Map<String, Long> getOrderSeq() {
        Long orderSeq = orderService.createOrderSeq();
        Map<String, Long> res = new HashMap<>();
        res.put("orderSeq", orderSeq);
        log.info("orderSeq {}", orderSeq);

        return res;
    }

//    @GetMapping("/register/detail?productCd={productCd}")
//    public String getOrderDetail() {
//    }
}
