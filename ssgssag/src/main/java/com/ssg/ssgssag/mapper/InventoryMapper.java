package com.ssg.ssgssag.mapper;

import com.ssg.ssgssag.domain.InventoryVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface InventoryMapper {
    List<InventoryVO> selectAllInventory();
}
