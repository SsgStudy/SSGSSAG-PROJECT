package com.ssg.ssgssag.service;

import com.ssg.ssgssag.domain.WareHouseZoneVO;
import com.ssg.ssgssag.dto.WareHouseDTO;
import com.ssg.ssgssag.mapper.WareHouseMapper;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

  @Override
  @Transactional
  public void addWarehouse(WareHouseDTO wareHouseDTO) {
    String baseCode = generateWarehouseCode(wareHouseDTO.getVWarehouseLoc());
    String latestWarehouseCd = wareHouseMapper.findLatestWarehouseCdByPrefix("KR-" + baseCode + "-");

    // 최신 코드의 숫자 부분 추출 및 +1
    int nextNumber = 1; // 기본값
    if (latestWarehouseCd != null && !latestWarehouseCd.isEmpty()) {
      String numberPart = latestWarehouseCd.substring(latestWarehouseCd.lastIndexOf('-') + 1);
      nextNumber = Integer.parseInt(numberPart) + 1;
    }


    // 최종 창고 코드 생성
    String finalWarehouseCd = "KR-" + baseCode + "-" + nextNumber;
    wareHouseDTO.setVWarehouseCd(finalWarehouseCd);

    wareHouseMapper.insertWarehouse(wareHouseDTO);
    log.info("Added new warehouse with code: {}", finalWarehouseCd);
  }

  @Override
  public List<WareHouseZoneVO> getWareHouseZones(String vWarehouseCd) {
    return wareHouseMapper.selectWareHouseZone(vWarehouseCd);
  }

  @Override
  public void addWarehouseZone(WareHouseZoneVO wareHouseZoneVO) {
    wareHouseMapper.insertWarehouseZone(wareHouseZoneVO);
  }

  private String generateWarehouseCode(String location) {
    if (location.contains("서울")) return "SEO";
    else if (location.contains("부산")) return "BUS";
    else if (location.contains("인천")) return "INC";
    else if (location.contains("대구")) return "DAG";
    else if (location.contains("광주")) return "GWA";
    else if (location.contains("대전")) return "DAE";
    else if (location.contains("경기")) return "GYE";
    else return "ETC"; // 기타 지역에 대한 처리
  }

}

