package com.ssg.ssgssag.mapper;

import com.ssg.ssgssag.domain.WareHouseZoneVO;
import com.ssg.ssgssag.dto.IncomingDTO;
import com.ssg.ssgssag.dto.WareHouseDTO;

import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface WareHouseMapper {

    List<WareHouseDTO> selectAllWareHouse();

    List<WareHouseDTO> findByWarehouseType(@Param("type") String type);

    List<WareHouseDTO> findByWarehouseLocation(@Param("location") String location);

    List<WareHouseDTO> findByTypeAndLocation(@Param("type") String type, @Param("location") String location);

    List<WareHouseDTO> findByWarehouseName(String name);
    List<String> findAllWarehouseLocations();

    List<String> findAllWarehouseType();

    void insertWarehouse(WareHouseDTO wareHouseDTO);

    List<WareHouseZoneVO> selectWareHouseZone(@Param("vWarehouseCd") String vWarehouseCd);
}
