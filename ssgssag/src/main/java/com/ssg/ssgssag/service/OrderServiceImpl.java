package com.ssg.ssgssag.service;

import com.ssg.ssgssag.domain.OrderProductVO;
import com.ssg.ssgssag.dto.OrderDetailDTO;
import com.ssg.ssgssag.dto.OrderProductDTO;
import com.ssg.ssgssag.mapper.OrderMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
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

    private ModelMapper modelMapper = new ModelMapper();

    @Override
    public Long createOrderSeq() {
        return orderMapper.selectLastOrderSeq()+1;
    }

    @Override
    public OrderProductDTO createOrderDetail(OrderProductDTO orderProduct) {
        Map<String, Object> map = new HashMap<>();
        map.put("productCd", orderProduct.getVProductCd());
        map.put("warehouseCd", orderProduct.getVWarehouseCd());

        String manufactor = orderMapper.selectProductSupplier(orderProduct.getVProductCd());
        if (!manufactor.equals(orderProduct.getVIncomingProductSupplierNm())) return null;
        OrderProductVO orderProductVO = orderMapper.selectProductInventory(map);

        return modelMapper.map(orderProductVO, OrderProductDTO.class);
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
