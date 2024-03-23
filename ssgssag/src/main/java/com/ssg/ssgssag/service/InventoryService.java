package com.ssg.ssgssag.service;


import com.ssg.ssgssag.domain.InventoryHistoryVO;
import com.ssg.ssgssag.dto.*;

import java.util.List;

public interface InventoryService {
    List<InventoryListDTO> selectAllInventory();

    List<WareHouseZoneDTO> selectAllWareHouseZone();

    List<CategoryFilterDTO> selectCategoryHierarchy();

    List<InventoryListDTO> getInventoryByCategoryAndWarehouse(InventorySearchDTO inventorySearchDTO);

    InventoryHistoryVO getInventoryHistoryBySeq(Integer pkInventorySeq);
}
