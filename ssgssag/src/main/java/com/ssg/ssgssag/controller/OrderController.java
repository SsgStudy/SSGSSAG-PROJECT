package com.ssg.ssgssag.controller;

import com.ssg.ssgssag.dto.OrderProductDTO;
import com.ssg.ssgssag.dto.OrderReadSearchDTO;
import com.ssg.ssgssag.dto.OrderRequestDTO;
import com.ssg.ssgssag.service.OrderService;
import java.util.HashMap;
import java.util.Map;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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

    @Operation(summary = "발주 등록 페이지", description = "발주 등록 및 삭제")
    @GetMapping("/register")
    public String showOrderRegisterPage() {
        return "order/order-register";
    }

    @Operation(summary = "발주 조회 페이지", description = "발주 조회 페이지 로딩")
    @GetMapping("/read")
    public String showOrderReadPage() {
        return "order/order-read";
    }

    @Operation(summary = "발주 확정 페이지", description = "등록된 발주 건 확정")
    @GetMapping("/confirm")
    public String showOrderConfirmPage() {
        return "order/order-confirm";
    }

    @Operation(summary = "발주 등록 : 발주 번호", description = "마스터 발주 번호 생성해서 반환")
    @ResponseBody
    @GetMapping("/register/order-seq")
    public Map<String, Long> getOrderSeq() {
        Long orderSeq = orderService.createOrderSeq();
        Map<String, Long> res = new HashMap<>();
        res.put("orderSeq", orderSeq);
        return res;
    }

    @Operation(summary = "발주 등록 : 단품 발주 정보", description = "유효 상품 검증 및 매입처의 제품 검증 후 단품 정보 반환")
    @PostMapping("/register/detail")
    @ResponseBody
    public ResponseEntity<?> createOrderDetailForm(@RequestBody OrderProductDTO order) {
        log.info("orderRegister {}", order);
        OrderProductDTO createdOrderProduct = orderService.createOrderDetail(order);

        String result = createdOrderProduct.getResult();
        if (result.equals("INVALID")) {
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body("유효하지 않은 상품입니다.");
        } else if (result.equals("UNLISTED")) {
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY)
                    .body("해당 회사에서 제공하지 않는 상품입니다.");
        }
        return ResponseEntity.ok(createdOrderProduct);
    }


    @Operation(summary = "발주 등록 : 발주 저장", description = "마스터 발주와 단품 발주 저장")
    @PostMapping("/register")
    @ResponseBody
    public String registerOrderAndOrderDetail(@RequestBody OrderRequestDTO orderRequest) {
        log.info("orderDTO {}", orderRequest.getOrder());
        orderService.registerOrder(orderRequest.getOrder(), orderRequest.getOrderDetails());
        return "ok";
//        return "order/orderRegister";
    }

    @Operation(summary = "발주 조회 : 마스터 발주", description = "기간, 창고, 매입처, 발주 상태 조건으로 등록된 발주 조회")
    @PostMapping("/read")
    @ResponseBody
    public List<OrderProductDTO> getOrderList(@RequestBody OrderReadSearchDTO orderReadSearch) {
        List<OrderProductDTO> dto = orderService.getOrderList(orderReadSearch);
        return dto;
    }

    @Operation(summary = "발주 조회 : 단품 발주 조회", description = "마스터 발주의 단품 발주 내역 조회")
    @GetMapping("/single")
    @ResponseBody
    public List<OrderProductDTO> getMasterOrderList(@RequestParam("order-seq") Long orderSeq) {
        return orderService.getOrderSigleList(orderSeq);
    }

    @Operation(summary = "발주 확정", description = "선택된 발주 건 확정 상태로 변경")
    @PostMapping("/confirm")
    @ResponseBody
    public String orderConfirmation(@RequestBody Map<String, List<Long>> param) {
        log.info("orderSeq {}", param);
        orderService.updateOrderStatusConfirmed(param.get("orderSeq"));

        return "ok";
    }

    @Operation(summary = "발주 삭제", description = "등록된 발주 내역 삭제")
    @ResponseStatus(HttpStatus.SEE_OTHER)
    @DeleteMapping("/register/{orderSeq}")
    public String deleteOrder(@PathVariable Long orderSeq) {
        orderService.deleteOrder(orderSeq);

        return "redirect:/order/register";
    }
}
