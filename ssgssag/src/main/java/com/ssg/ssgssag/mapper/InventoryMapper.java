package com.ssg.ssgssag.mapper;

import com.ssg.ssgssag.domain.InventoryVO;
import com.ssg.ssgssag.dto.CategoryFilterDTO;
import com.ssg.ssgssag.dto.WareHouseZoneDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface InventoryMapper {
    List<InventoryVO> selectAllInventory();

    List<WareHouseZoneDTO> selectAllWareHouseZone();

    List<CategoryFilterDTO> selectCategoryHierarchy();
}
