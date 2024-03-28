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
        map.put("productManufactor", orderProduct.getvIncomingProductSupplierNm());

        String result = orderMapper.selectProductSupplier(orderProduct);

        if (result.equals("VALID")) {
            OrderProductVO orderProductVO = orderMapper.selectProductInventory(map);
            OrderProductDTO orderProductDTO = modelMapper.map(orderProductVO, OrderProductDTO.class);
            orderProductDTO.setResult(result);
            return orderProductDTO;
        }
        else
            return OrderProductDTO.builder().result(result).build();
    }

    @Override
    @Transactional
    public int registerOrder(OrderVO order, List<OrderDetailVO> orderDetails) {

        try {
            orderMapper.insertOrder(order); // 주문 정보
            orderMapper.insertOrderDetail(orderDetails); // 주문 상세 정보
            return 1;
        } catch (RuntimeException e) {
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

    @Override
    public int updateOrderStatusConfirmed(List<Long> orderSeqList) {
        return orderMapper.updateOrderStatusByOrderSeq(orderSeqList);
    }

    @Override
    public int deleteOrder(Long orderSeq) {
        return orderMapper.deleteOneOrderByOrderSeq(orderSeq);
    }

}
