package com.ssg.ssgssag.service;

import com.ssg.ssgssag.dto.WareHouseDTO;
import com.ssg.ssgssag.mapper.WareHouseMapper;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Log4j2
@RequiredArgsConstructor
public class WareHouseServiceImpl implements WareHouseService {

  @Autowired
  private final WareHouseMapper wareHouseMapper;

  @Override
  public List<WareHouseDTO> getAllWareHouse() {
    return wareHouseMapper.selectAllWareHouse();
  }

  @Override
  public List<WareHouseDTO> findByWarehouseType(String type) {
    return wareHouseMapper.findByWarehouseType(type);
  }

  @Override
  public List<WareHouseDTO> findByWarehouseLocation(String location) {
    return wareHouseMapper.findByWarehouseLocation(location);
  }

  @Override
  public List<WareHouseDTO> findByTypeAndLocation (String type, String location){
    return wareHouseMapper.findByTypeAndLocation(type,location);
  }

  @Override
  public List<WareHouseDTO> findByWarehouseName(String name) {
    return wareHouseMapper.findByWarehouseName(name);
  }

  @Override
  public List<String> findAllWarehouseLocations() {

    return wareHouseMapper.findAllWarehouseLocations();
  }

  @Override
  public List<String> findAllWarehouseType(){
    List<String> types = wareHouseMapper.findAllWarehouseType();
    return types;

  }

}
