package com.ssg.ssgssag.controller;

import com.ssg.ssgssag.domain.InventoryVO;
import com.ssg.ssgssag.dto.CategoryFilterDTO;
import com.ssg.ssgssag.dto.WareHouseZoneDTO;
import com.ssg.ssgssag.service.InventoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.*;

@Log4j2
@Controller
@RequiredArgsConstructor
@RequestMapping("/inventory")
public class InventoryController {

    private final InventoryService inventoryService;

    @GetMapping("/list")
    public String showInventoryListPage(Model model) {
        log.info("[controller] inventory all list");

//        모든 재고 리스트 가져오기
        List<InventoryVO> inventoryList = inventoryService.selectAllInventory();
        model.addAttribute("inventoryList", inventoryList);

//       창고 이름만 가져오기
//        List<WareHouseZoneDTO> wareHouseZoneList = inventoryService.selectAllWareHouseZone();

//        Map<String, Object> map = new HashMap<>();
//        map.put("inventoryList", inventoryList);
//        map.put("wareHouseZoneList",wareHouseZoneList);
//        return map;

//        Set<String> uniqueWareHouseNames = new HashSet<>();
//        for (WareHouseZoneDTO wareHouseZone : wareHouseZoneList) {
//            uniqueWareHouseNames.add(wareHouseZone.getVWarehouseNm());
//        }
//        model.addAttribute("uniqueWareHouseNames", uniqueWareHouseNames);
//        model.addAttribute("wareHouseZoneList", wareHouseZoneList);

        return "/inventory/inventory-list";
    }

    @GetMapping("/warehouse")
    @ResponseBody
    public List<WareHouseZoneDTO> getWarehouseAndZone() {
        List<WareHouseZoneDTO> wareHouseZoneList = inventoryService.selectAllWareHouseZone();

        return wareHouseZoneList;
    }

    @GetMapping("/category")
    @ResponseBody
    public List<CategoryFilterDTO> getCategoryHierarchy() {
        List<CategoryFilterDTO> categoryList = inventoryService.selectCategoryHierarchy();
        return categoryList;
    }

}
