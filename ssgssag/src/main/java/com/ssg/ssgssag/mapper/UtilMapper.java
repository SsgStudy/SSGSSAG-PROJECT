package com.ssg.ssgssag.mapper;

import com.ssg.ssgssag.dto.InventoryShortageDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface UtilMapper {
    List<InventoryShortageDTO> findShortageInventory();

    List<String> selectMemberEmail();
}
