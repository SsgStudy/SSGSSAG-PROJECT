package com.ssg.ssgssag.service;

import com.ssg.ssgssag.dto.OrderProductDTO;
import com.ssg.ssgssag.dto.OrderReadSearchDTO;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.swing.plaf.synth.SynthTextAreaUI;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Log4j2
class OrderServiceTest {

    @Autowired
    private final OrderService orderService;

    @Autowired
    OrderServiceTest(OrderService orderService) {
        this.orderService = orderService;
    }

    @Test
    public void createOrderSeqTest() {
        Long orderSeq = orderService.createOrderSeq();
        log.info("orderSeq {}", orderSeq);
    }

    @Test
    public void getOrderDetailInputTest() {
        OrderProductDTO orderProductDTO = OrderProductDTO.builder()
                .vProductCd("880-5678-0523")
                .vIncomingProductSupplierNm("Samsung Electronics")
                .vWarehouseCd("KR_SEO_02")
                .build();
        OrderProductDTO orderProduct
                = orderService.createOrderDetail(orderProductDTO);
        log.info("getOrderDetailInputTest {}", orderProduct);
    }

    @Test
    public void getOrderList() {
        OrderReadSearchDTO dto1 = OrderReadSearchDTO.builder()
                .startDate("2024-01-01")
                .endDate("2024-04-01")
                .build();
        System.out.println("조건 1");
        orderService.getOrderList(dto1).forEach(System.out::println);

        OrderReadSearchDTO dto2 = OrderReadSearchDTO.builder()
                .startDate("2024-01-01")
                .endDate("2024-04-01")
                .vIncomingProductSupplierNm("Samsung Electronics")
                .build();

        System.out.println("조건 2");
        orderService.getOrderList(dto2).forEach(System.out::println);

        OrderReadSearchDTO dto3 = OrderReadSearchDTO.builder()
                .startDate("2024-01-01")
                .endDate("2024-04-01")
                .vWarehouseCd("KR-SEO-02")
                .build();

        System.out.println("조건 3");
        orderService.getOrderList(dto3).forEach(System.out::println);

        OrderReadSearchDTO dto4 = OrderReadSearchDTO.builder()
                .startDate("2024-01-01")
                .endDate("2024-04-01")
                .vOrderStatus("미확정")
                .build();

        System.out.println("조건 4");
        orderService.getOrderList(dto4).forEach(System.out::println);
    }

    @Test
    public void getOrderDetail() {
        log.info("상세 발주 조회");
        orderService.getOrderSigleList(3L).forEach(System.out::println);
    }
}