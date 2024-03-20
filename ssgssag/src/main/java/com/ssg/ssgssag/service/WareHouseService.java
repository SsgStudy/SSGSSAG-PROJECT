package com.ssg.ssgssag.service;

import com.ssg.ssgssag.dto.IncomingDTO;
import com.ssg.ssgssag.dto.WareHouseDTO;
import java.util.List;

public interface WareHouseService {

  List<WareHouseDTO> getAllWareHouse();

  List<WareHouseDTO> findByWarehouseType(String type);

  List<WareHouseDTO> findByWarehouseLocation(String location);

  List<WareHouseDTO> findByTypeAndLocation (String type, String location);

}
