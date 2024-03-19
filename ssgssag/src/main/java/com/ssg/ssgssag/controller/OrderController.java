package com.ssg.ssgssag.controller;

import com.ssg.ssgssag.domain.OrderDetailVO;
import com.ssg.ssgssag.domain.OrderProductVO;
import com.ssg.ssgssag.domain.OrderVO;
import com.ssg.ssgssag.dto.OrderProductDTO;
import com.ssg.ssgssag.dto.OrderRequestDTO;
import com.ssg.ssgssag.service.OrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@Log4j2
@Controller
@RequestMapping("/order")
public class OrderController {

    @Autowired
    private final OrderService orderService;

    @ResponseBody
    @GetMapping("/register/order-seq")
    public Map<String, Long> getOrderSeq() {
        Long orderSeq = orderService.createOrderSeq();
        Map<String, Long> res = new HashMap<>();
        res.put("orderSeq", orderSeq);
        log.info("orderSeq {}", orderSeq);

        return res;
    }

    @PostMapping("/register/detail")
    @ResponseBody
    public ResponseEntity<OrderProductDTO> createOrderDetailForm(@ModelAttribute OrderProductDTO orderProduct) {
        OrderProductDTO createdOrderProduct = orderService.createOrderDetail(orderProduct);

        return ResponseEntity.ok(createdOrderProduct);
    }

    @PostMapping("/register")
    @ResponseBody
    public String registerOrderAndOrderDetail(@RequestBody OrderRequestDTO orderRequest) {
        orderService.registerOrder(orderRequest.getOrder(), orderRequest.getOrderDetails());
        return "ok";
//        return "order/orderRegister";
    }

}
