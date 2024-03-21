package com.ssg.ssgssag.service;

import com.ssg.ssgssag.domain.InventoryVO;
import com.ssg.ssgssag.dto.CategoryFilterDTO;
import com.ssg.ssgssag.dto.WareHouseZoneDTO;

import java.util.List;

public interface InventoryService {
    List<InventoryVO> selectAllInventory();

    List<WareHouseZoneDTO> selectAllWareHouseZone();

    List<CategoryFilterDTO> selectCategoryHierarchy();
}
