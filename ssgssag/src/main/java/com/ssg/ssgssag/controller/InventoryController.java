package com.ssg.ssgssag.controller;

import com.ssg.ssgssag.domain.InventoryHistoryVO;
import com.ssg.ssgssag.domain.InventoryVO;
import com.ssg.ssgssag.dto.CategoryFilterDTO;
import com.ssg.ssgssag.dto.InventoryListDTO;
import com.ssg.ssgssag.dto.InventorySearchDTO;
import com.ssg.ssgssag.dto.WareHouseZoneDTO;
import com.ssg.ssgssag.service.InventoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

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

        List<InventoryListDTO> inventoryList = inventoryService.selectAllInventory();
        model.addAttribute("inventoryList", inventoryList);

        return "inventory/inventory-list";
    }

    @GetMapping("/list/detail/{pkInventorySeq}")
    @ResponseBody
    public InventoryHistoryVO getInventoryHistoryBySeq(@PathVariable Integer pkInventorySeq) {
        log.info("호출");
        InventoryHistoryVO inventoryHistoryVO = inventoryService.getInventoryHistoryBySeq(pkInventorySeq);
        return inventoryHistoryVO;
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

    @PostMapping("/search")
    @ResponseBody
    public List<InventoryListDTO> getInventoryByCategoryAndWarehouse(@RequestBody InventorySearchDTO inventorySearchDTO) {
        log.info("검색 {}", inventorySearchDTO);

        List<InventoryListDTO> inventoryList = inventoryService.getInventoryByCategoryAndWarehouse(inventorySearchDTO);
//        inventoryList.stream().forEach(System.out::println);
        return inventoryList;
    }

}
