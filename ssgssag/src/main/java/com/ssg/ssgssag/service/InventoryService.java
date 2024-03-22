package com.ssg.ssgssag.service;

import com.ssg.ssgssag.domain.InventoryVO;
import com.ssg.ssgssag.dto.CategoryFilterDTO;
import com.ssg.ssgssag.dto.InventoryListDTO;
import com.ssg.ssgssag.dto.InventorySearchDTO;
import com.ssg.ssgssag.dto.WareHouseZoneDTO;

import java.util.List;

public interface InventoryService {
    List<InventoryListDTO> selectAllInventory();

    List<WareHouseZoneDTO> selectAllWareHouseZone();

    List<CategoryFilterDTO> selectCategoryHierarchy();

    List<InventoryListDTO> getInventoryByCategoryAndWarehouse(InventorySearchDTO inventorySearchDTO);
}
