package com.ssg.ssgssag.service;

import com.ssg.ssgssag.dto.InventoryShortageDTO;
import com.ssg.ssgssag.mapper.UtilMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Log4j2
@RequiredArgsConstructor
public class UtilServiceImpl implements UtilService{

    @Autowired
    private final UtilMapper utilMapper;

    @Override
    public List<InventoryShortageDTO> findShortageInventory() {
        return utilMapper.findShortageInventory();
    }
}
