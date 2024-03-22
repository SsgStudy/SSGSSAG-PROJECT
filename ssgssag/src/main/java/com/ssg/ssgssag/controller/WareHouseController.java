package com.ssg.ssgssag.controller;

import com.ssg.ssgssag.domain.WareHouseZoneVO;
import com.ssg.ssgssag.dto.WareHouseDTO;
import com.ssg.ssgssag.service.WareHouseService;
import io.swagger.v3.oas.annotations.Operation;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Log4j2
@Controller
@RequiredArgsConstructor
@RequestMapping("/warehouse")
public class WareHouseController {

  private final WareHouseService wareHouseService;

  @GetMapping()
  @Operation(summary = "창고 목록 조회", description = "모든 창고의 목록을 조회합니다.")
  public String showWareHouseListPage(Model model) {

    log.info("WareHouse controller test");

    List<WareHouseDTO> warehouselist = wareHouseService.getAllWareHouse();

    log.info(warehouselist);
    model.addAttribute("warehouselist", warehouselist);
    model.addAttribute("locations", wareHouseService.findAllWarehouseLocations());
    model.addAttribute("types", wareHouseService.findAllWarehouseType());

    return "warehouse/warehouse";

  }

  @PostMapping("/search")
  @Operation(summary = "창고 목록 검색", description = "이름,소재지,종류 각 조건별로 창고를 검색합니다.")
  @ResponseBody
  public ResponseEntity<List<WareHouseDTO>> search(@RequestParam(value = "type", required = false) String type,
      @RequestParam(value = "location", required = false) String location,
      @RequestParam(value = "name", required = false) String name, Model model) {

    List<WareHouseDTO> warehouselist;

    if ("창고종류".equals(type)) {
      type = null;
    }

    if ("창고소재지".equals(location)) {
      location = null;
    }

    if (name != null && !name.isEmpty()) {
      warehouselist = wareHouseService.findByWarehouseName(name);
    }
    else{
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
    }

    model.addAttribute("warehouselist", warehouselist);
    model.addAttribute("locations", wareHouseService.findAllWarehouseLocations());
    model.addAttribute("types", wareHouseService.findAllWarehouseType());

    return ResponseEntity.ok(warehouselist);

  }

  @PostMapping("/add")
  @Operation(summary = "창고 등록", description = "창고를 등록합니다.")
  public String addWarehouse(@ModelAttribute WareHouseDTO wareHouseDTO) {
    log.info("창고등록 시작");
    wareHouseService.addWarehouse(wareHouseDTO);
    log.info(wareHouseDTO);
    return "redirect:/warehouse";
  }

  @GetMapping("/{vWarehouseCd}/zones")
  @Operation(summary = "창고 구역 조회", description = "창고 구역 조회합니다.")
  @ResponseBody
  public List<WareHouseZoneVO> getWareHouseZones(@PathVariable String vWarehouseCd) {
    return wareHouseService.getWareHouseZones(vWarehouseCd);
  }

  @PostMapping("/addZone")
  @Operation(summary = "창고 구역 추가", description = "창고 구역을 추가합니다.")
  @ResponseBody
  public ResponseEntity<?> addWarehouseZone(
      @RequestParam("vZoneCd") String vZoneCd,
      @RequestParam("vWarehouseCd") String vWarehouseCd,
      @RequestParam("vZoneNm") String vZoneNm) {

    WareHouseZoneVO wareHouseZoneVO = WareHouseZoneVO.builder()
        .vZoneCd(vZoneCd)
        .vWarehouseCd(vWarehouseCd)
        .vZoneNm(vZoneNm)
        .build();

    log.info("Received zone data: {}", wareHouseZoneVO);
    wareHouseService.addWarehouseZone(wareHouseZoneVO);
    return ResponseEntity.ok().build();
  }


}
