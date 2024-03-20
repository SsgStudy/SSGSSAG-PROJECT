package com.ssg.ssgssag.controller;

import com.ssg.ssgssag.dto.WareHouseDTO;
import com.ssg.ssgssag.service.WareHouseService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Log4j2
@Controller
@RequiredArgsConstructor
@RequestMapping("/warehouse")
public class WareHouseController {

  private final WareHouseService wareHouseService;

  @GetMapping("/warehouse")
  public String showWareHousListPage(Model model){

    log.info("WareHouse controller test");

    List<WareHouseDTO> warehouselist = wareHouseService.getAllWareHouse();

    log.info(warehouselist);
    model.addAttribute("warehouselist",warehouselist);

    return "warehouse/warehouse";

  }

//  @GetMapping("/search")
//  public String searchByType(@RequestParam(value = "type", required = false) String type,
//      @RequestParam(value = "location", required = false) String location, Model model) {
//    List<WareHouseDTO> warehouselist = wareHouseService.findByWarehouseType(type);
//    model.addAttribute("warehouselist", warehouselist);
//    return "warehouse/warehouse";
//  }


  @GetMapping("/search")
  public String search(@RequestParam(value = "type", required = false) String type,
      @RequestParam(value = "location", required = false) String location, Model model) {
    List<WareHouseDTO> warehouselist;

    if("창고종류".equals(type)){
      type = null;
    }

    if("창고소재지".equals(location)){
      location=null;
    }

    if (type != null && !type.isEmpty() && location != null && !location.isEmpty()) {
      warehouselist = wareHouseService.findByTypeAndLocation(type, location);
    }
    // type만 있는 경우
    else if (type != null && !type.isEmpty()) {
      warehouselist = wareHouseService.findByWarehouseType(type);
    }
    // location만 있는 경우
    else if (location != null && !location.isEmpty()) {
      warehouselist = wareHouseService.findByWarehouseLocation(location);
    }
    // 둘 다 없는 경우
    else {
      warehouselist = wareHouseService.getAllWareHouse();
    }

    model.addAttribute("warehouselist", warehouselist);
    return "warehouse/warehouse";

  }


}
