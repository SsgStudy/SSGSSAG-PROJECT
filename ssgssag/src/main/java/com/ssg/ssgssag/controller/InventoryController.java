package com.ssg.ssgssag.controller;

import com.ssg.ssgssag.domain.InventoryHistoryVO;
import com.ssg.ssgssag.domain.InventoryVO;
import com.ssg.ssgssag.dto.*;
import com.ssg.ssgssag.service.InventoryService;
import io.swagger.v3.oas.annotations.Operation;
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
    @Operation(summary = "재고 목록 출력", description = "재고 조회 리스트 모두 출력")
    public String showInventoryListPage(Model model) {
        log.info("[controller] inventory all list");

        List<InventoryListDTO> inventoryList = inventoryService.selectAllInventory();
        model.addAttribute("inventoryList", inventoryList);

        return "inventory/inventory-list";
    }

    @GetMapping("/list/detail/{pkInventorySeq}")
    @Operation(summary = "재고 조회 : 모달 출력", description = "재고 번호에 따른 재고 이력 모달 출력")
    @ResponseBody
    public List<InventoryHistoryVO> getInventoryHistoryBySeq(@PathVariable Integer pkInventorySeq) {
        log.info("호출");
        List<InventoryHistoryVO> inventoryHistoryList = inventoryService.getInventoryHistoryBySeq(pkInventorySeq);
        return inventoryHistoryList;
    }

    @GetMapping("/warehouse")
    @Operation(summary = "재고 조회 : 창고 및 구역", description = "창고, 창고에 따른 구역 리스트")
    @ResponseBody
    public List<WareHouseZoneDTO> getWarehouseAndZone() {
        List<WareHouseZoneDTO> wareHouseZoneList = inventoryService.selectAllWareHouseZone();

        return wareHouseZoneList;
    }

    @GetMapping("/category")
    @Operation(summary = "재고 조회 : 카테고리", description = "카테고리 대분류, 대분류에 따른 중분류, 중분류에 따른 소분류 리스트")
    @ResponseBody
    public List<CategoryFilterDTO> getCategoryHierarchy() {
        List<CategoryFilterDTO> categoryList = inventoryService.selectCategoryHierarchy();
        return categoryList;
    }

    @PostMapping("/search")
    @Operation(summary = "재고 조회 결과 반환", description = "카테고리 대분류 중분류 소분류, 창고, 구역")
    @ResponseBody
    public List<InventoryListDTO> getInventoryByCategoryAndWarehouse(@RequestBody InventorySearchDTO inventorySearchDTO) {
        log.info("검색 {}", inventorySearchDTO);

        List<InventoryListDTO> inventoryList = inventoryService.getInventoryByCategoryAndWarehouse(inventorySearchDTO);
        return inventoryList;
    }


    // 2. 재고 조정
    @GetMapping("/adjustment")
    @Operation(summary = "재고 조정", description = "재고 조정 페이지")
    public String showInventoryAdjustmentPage(Model model) {
        List<InventoryListDTO> inventoryList = inventoryService.selectAllInventory();
        model.addAttribute("inventoryList", inventoryList);
        return "inventory/inventory-adjustment";
    }

    @PostMapping("/adjustment")
    @Operation(summary = "재고 조정 값 반환", description = "번호, 수량, 상태 반환")
    @ResponseBody
    public void selectedInventory(@RequestBody InventoryAdjustmentDTO dto) {
        inventoryService.updateInventoryWithHistory(dto);
    }

    // 3. 재고 이동
    @GetMapping("/movement")
    @Operation(summary = "재고 이동", description = "재고 이동 페이지")
    public String showInventoryMovementPage(Model model) {
        List<InventoryListDTO> inventoryList = inventoryService.selectAllInventory();
        model.addAttribute("inventoryList", inventoryList);
        return "inventory/inventory-movement";
    }

    @PostMapping("/movement")
    @Operation(summary = "재고 이동 값 반환", description = "번호, 창고, 구역 반환")
    @ResponseBody
    public void selectedInventoryMovement(@RequestBody InventoryMovementDTO dto) {
        inventoryService.updateInventoryWithHistoryMove(dto);
    }

}
