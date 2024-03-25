package com.ssg.ssgssag.service;

import com.ssg.ssgssag.domain.InventoryHistoryVO;
import com.ssg.ssgssag.dto.*;
import com.ssg.ssgssag.mapper.InventoryMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Log4j2
@RequiredArgsConstructor
public class InventoryServiceImpl implements InventoryService{

    @Autowired
    private final InventoryMapper inventoryMapper;

    @Override
    public List<InventoryListDTO> selectAllInventory() {
        return inventoryMapper.selectAllInventory();
    }

    @Override
    public List<WareHouseZoneDTO> selectAllWareHouseZone() {
        return inventoryMapper.selectAllWareHouseZone();
    }

    @Override
    public List<CategoryFilterDTO> selectCategoryHierarchy() {
        return inventoryMapper.selectCategoryHierarchy();
    }

    @Override
    public List<InventoryListDTO> getInventoryByCategoryAndWarehouse(InventorySearchDTO inventorySearchDTO) {
        return inventoryMapper.searchInventoryByCategoryAndWarehouse(inventorySearchDTO);
    }

    @Override
    public List<InventoryHistoryVO> getInventoryHistoryBySeq(Integer pkInventorySeq) {
        return inventoryMapper.selectInventoryHistoryBySeq(pkInventorySeq);
    }

    @Override
    @Transactional
    public void updateInventoryWithHistoryCnt(InventoryAdjustmentDTO inventoryAdjustmentDTO) {
        inventoryMapper.updateInventoryWithHistoryCnt(inventoryAdjustmentDTO);
    }

    @Override
    public void updateInventoryWithHistoryMove(InventoryMovementDTO inventoryMovementDTO) {
        inventoryMapper.updateInventoryWithHistoryMove(inventoryMovementDTO);
    }
}
