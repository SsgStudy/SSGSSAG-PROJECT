package com.ssg.ssgssag.service;


import com.ssg.ssgssag.mapper.OrderMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderMapper orderMapper;
    public void selectOne() {
        System.out.println(orderMapper.selectOne());
    }

}
