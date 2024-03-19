package com.ssg.ssgssag.service;


import com.ssg.ssgssag.domain.OrderVO;
import com.ssg.ssgssag.domain.ProductVO;
import com.ssg.ssgssag.dto.OrderDetailDTO;
import com.ssg.ssgssag.dto.OrderProductDTO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

public interface OrderService {

    Long createOrderSeq(); // 발주번호 생성
    OrderProductDTO createOrderDetail(OrderProductDTO orderProduct);  // 발주상세 입력
//    int registerOrder(OrderProductDTO orderProduct); // 발주 등록
//    List<OrderDetailDTO> getOrderDetailList(OrderDetailDTO orderDetail); // 발주 상세 조회

//    List<OrderVO> getAllOrdersWithDetails();
//    List<OrderVO> getAllOrdersStatusProgress();
//    List<ProductVO> getProductInventoryList();
//    OrderVO getOneOrderInformation(Long orderSeq);
//    int updateOrderStauts(Long orderSeq);
}
