package com.ssg.ssgssag.service;

import com.ssg.ssgssag.domain.WareHouseZoneVO;
import com.ssg.ssgssag.dto.IncomingDTO;
import com.ssg.ssgssag.dto.WareHouseDTO;
import java.util.List;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@Log4j2
public class WareHouseServiceTest {

  @Autowired
  private final WareHouseService wareHouseService;

  @Autowired
  public WareHouseServiceTest(WareHouseService wareHouseService) {
    this.wareHouseService = wareHouseService;
  }

  @Test
  public void getAllWareHouseTest(){

    List<WareHouseDTO> wareHouseDTOList = wareHouseService.getAllWareHouse();
    log.info(wareHouseDTOList);

  }

  @Test
  public void findByWareHouseTypeTest(){
    String type="제조";
    List<WareHouseDTO> results = wareHouseService.findByWarehouseType(type);

    log.info(results);
  }

  @Test
  public void findByWareHouseNameTest(){
    String name="서울 중앙 창고";
    List<WareHouseDTO> results = wareHouseService.findByWarehouseName(name);

    log.info(results);
  }

  @Test
  public void addWareHouseTest(){
    WareHouseDTO wareHouseDTO = WareHouseDTO.builder()
        .vWarehouseCd("1").vWarehouseNm("테스트창고").vWarehouseLoc("서울, 대한민국")
        .sWarehouseType("소매").pkMemberSeq(1L).build();

    wareHouseService.addWarehouse(wareHouseDTO);
    log.info(wareHouseDTO);
  }

  @Test
  public void getAllWareHouseZoneTest(){
    String code="KR-BUS-01";
    List<WareHouseZoneVO> results = wareHouseService.getWareHouseZones(code);
    log.info(results);

  }

  @Test
  public void addWareHouseZoneTest(){
    WareHouseZoneVO wareHouseZoneVO = WareHouseZoneVO.builder()
        .vWarehouseCd("KR-BUS-01").vZoneCd("G-01").vZoneNm("갯벌 1구역").build();

    wareHouseService.addWarehouseZone(wareHouseZoneVO);
    log.info(wareHouseZoneVO);

  }




}
