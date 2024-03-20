package com.ssg.ssgssag.service;

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


}
