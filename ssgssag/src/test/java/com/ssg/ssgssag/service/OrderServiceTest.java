package com.ssg.ssgssag.service;

import com.ssg.ssgssag.dto.OrderProductDTO;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

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
}