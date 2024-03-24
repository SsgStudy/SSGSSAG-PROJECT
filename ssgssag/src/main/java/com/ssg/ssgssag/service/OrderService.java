package com.ssg.ssgssag.service;


import com.ssg.ssgssag.domain.OrderDetailVO;
import com.ssg.ssgssag.domain.OrderVO;
import com.ssg.ssgssag.domain.ProductVO;
import com.ssg.ssgssag.dto.OrderDetailDTO;
import com.ssg.ssgssag.dto.OrderProductDTO;
import com.ssg.ssgssag.dto.OrderReadSearchDTO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

public interface OrderService {

    Long createOrderSeq(); // 발주번호 생성
    OrderProductDTO createOrderDetail(OrderProductDTO orderProduct);  // 발주상세 입력
    int registerOrder(OrderVO order, List<OrderDetailVO> orderDetail); // 발주 등록

    List<OrderProductDTO> getOrderList(OrderReadSearchDTO orderReadSearch);
    List<OrderProductDTO> getOrderSigleList(Long orderSeq); // 발주 상세 조회
    int updateOrderStatusConfirmed(List<Long> orderSeq);

    int deleteOrder(Long orderSeq);

//    List<OrderVO> getAllOrdersWithDetails();
//    List<OrderVO> getAllOrdersStatusProgress();
//    List<ProductVO> getProductInventoryList();
//    OrderVO getOneOrderInformation(Long orderSeq);

}
