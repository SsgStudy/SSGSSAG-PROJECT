package com.ssg.ssgssag.service;

import com.ssg.ssgssag.domain.InventoryVO;

import java.util.List;

public interface InventoryService {
    List<InventoryVO> selectAllInventory();
}
