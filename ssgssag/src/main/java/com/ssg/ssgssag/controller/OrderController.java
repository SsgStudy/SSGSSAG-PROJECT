package com.ssg.ssgssag.controller;

import com.ssg.ssgssag.dto.OrderProductDTO;
import com.ssg.ssgssag.dto.OrderReadSearchDTO;
import com.ssg.ssgssag.dto.OrderRequestDTO;
import com.ssg.ssgssag.service.OrderService;
import java.util.HashMap;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;


@RequiredArgsConstructor
@Log4j2
@Controller
@RequestMapping("/order")
public class OrderController {

    @Autowired
    private final OrderService orderService;


    @GetMapping("/register")
    public String showOrderRegisterPage() {
        log.info("발주 등록 페이지");

        return "order/order-register";
    }

    @GetMapping("/read")
    public String showOrderReadPage() {
        log.info("발주 조회 페이지");

        return "order/order-read";
    }

    @GetMapping("/confirm")
    public String showOrderConfirmPage() {
        log.info("발주 확정 페이지");

        return "order/order-confirm";
    }

    @ResponseBody
    @GetMapping("/register/order-seq")
    public Map<String, Long> getOrderSeq() {
        Long orderSeq = orderService.createOrderSeq();
        Map<String, Long> res = new HashMap<>();
        res.put("orderSeq", orderSeq);
        return res;
    }

    @PostMapping("/register/detail")
    @ResponseBody
    public ResponseEntity<OrderProductDTO> createOrderDetailForm(@RequestBody OrderProductDTO order) {
        OrderProductDTO createdOrderProduct = orderService.createOrderDetail(order);
        return ResponseEntity.ok(createdOrderProduct);
    }

    @PostMapping("/register")
    @ResponseBody
    public String registerOrderAndOrderDetail(@RequestBody OrderRequestDTO orderRequest) {
        orderService.registerOrder(orderRequest.getOrder(), orderRequest.getOrderDetails());
        return "ok";
//        return "order/orderRegister";
    }

    @PostMapping("/read")
    @ResponseBody
    public List<OrderProductDTO> getOrderList(@RequestBody OrderReadSearchDTO orderReadSearch) {
        List<OrderProductDTO> dto = orderService.getOrderList(orderReadSearch);
        return dto;
    }

    @GetMapping("/confirm/{orderSeq}")
    @ResponseBody
    public String orderConfirmation(@RequestParam String orderSeq) {
        Map<String, Long> map = new HashMap<>();
        return null;
    }

    @GetMapping("/single")
    @ResponseBody
    public List<OrderProductDTO> getMasterOrderList(@RequestParam("order-seq") Long orderSeq) {
        return orderService.getOrderSigleList(orderSeq);
    }
}
