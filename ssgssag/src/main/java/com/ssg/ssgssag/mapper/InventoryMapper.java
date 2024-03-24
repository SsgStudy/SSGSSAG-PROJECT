package com.ssg.ssgssag.mapper;

import com.ssg.ssgssag.domain.InventoryHistoryVO;
import com.ssg.ssgssag.dto.*;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface InventoryMapper {
    List<InventoryListDTO> selectAllInventory();

    List<WareHouseZoneDTO> selectAllWareHouseZone();

    List<CategoryFilterDTO> selectCategoryHierarchy();

    List<InventoryListDTO> searchInventoryByCategoryAndWarehouse(InventorySearchDTO inventorySearchDTO);

    InventoryHistoryVO selectInventoryHistoryBySeq(Integer pkInventorySeq);

    void updateInventoryWithHistory(InventoryAdjustmentDTO inventoryAdjustmentDTO);
    void updateInventoryWithHistoryMove(InventoryMovementDTO inventoryMovementDTO);
}
