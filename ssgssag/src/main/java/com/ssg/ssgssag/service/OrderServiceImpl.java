package com.ssg.ssgssag.service;

import com.ssg.ssgssag.domain.OrderDetailVO;
import com.ssg.ssgssag.domain.OrderProductVO;
import com.ssg.ssgssag.domain.OrderVO;
import com.ssg.ssgssag.dto.OrderDetailDTO;
import com.ssg.ssgssag.dto.OrderProductDTO;
import com.ssg.ssgssag.dto.OrderReadSearchDTO;
import com.ssg.ssgssag.mapper.OrderMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Log4j2
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    @Autowired
    private final OrderMapper orderMapper;

    private ModelMapper modelMapper = new ModelMapper();

    @Override
    public Long createOrderSeq() {
        return orderMapper.selectLastOrderSeq()+1;
    }

    @Override
    public OrderProductDTO createOrderDetail(OrderProductDTO orderProduct) {
        Map<String, Object> map = new HashMap<>();
        map.put("productCd", orderProduct.getvProductCd());
        map.put("warehouseCd", orderProduct.getvWarehouseCd());

        String manufactor = orderMapper.selectProductSupplier(orderProduct.getvProductCd());
        if (!manufactor.equals(orderProduct.getvIncomingProductSupplierNm())) return null;
        OrderProductVO orderProductVO = orderMapper.selectProductInventory(map);

        return modelMapper.map(orderProductVO, OrderProductDTO.class);
    }

    @Override
    @Transactional
    public int registerOrder(OrderVO order, List<OrderDetailVO> orderDetails) {

        try {
            orderMapper.insertOrder(order); // 주문 정보
            orderMapper.insertOrderDetail(orderDetails); // 주문 상세 정보
            log.info("발주 등록 성공");
            return 1;
        } catch (RuntimeException e) {
            log.info("발주 등록 실패");
            return 0;
        }
    }

    @Override
    public List<OrderProductDTO> getOrderList(OrderReadSearchDTO orderReadSearch) {
        return orderMapper.selectOrderDetailByDateOrString(orderReadSearch);
    }

    @Override
    public List<OrderProductDTO> getOrderSigleList(Long orderSeq) {
        return orderMapper.selectOrderDetailByOrderSeq(orderSeq);
    }
//
//    @Override
//    public List<OrderDetailDTO> getOrderDetailList(OrderDetailDTO orderDetail) {
//        return null;
//    }
}
