package com.ssg.ssgssag.service;

import com.ssg.ssgssag.dto.OrderDetailDTO;
import com.ssg.ssgssag.dto.OrderProductDTO;
import com.ssg.ssgssag.mapper.OrderMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Log4j2
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    @Autowired
    private final OrderMapper orderMapper;

    @Override
    public Long createOrderSeq() {
        return orderMapper.selectLastOrderSeq()+1;
    }

    @Override
    public OrderProductDTO getOrderDetail(String productCd, String warehouseCd) {
        Map<String, Object> map = new HashMap<>();
        map.put("productCd", productCd);
        map.put("warehouseCd", warehouseCd);
        return orderMapper.selectProductInventory(map);
    }

//    @Override
//    public int registerOrder(OrderProductDTO orderProduct) {
//        return 0;
//    }
//
//    @Override
//    public List<OrderDetailDTO> getOrderDetailList(OrderDetailDTO orderDetail) {
//        return null;
//    }
}
