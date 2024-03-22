package com.ssg.ssgssag.service;

import com.ssg.ssgssag.domain.InventoryVO;
import com.ssg.ssgssag.dto.CategoryFilterDTO;
import com.ssg.ssgssag.dto.InventoryListDTO;
import com.ssg.ssgssag.dto.InventorySearchDTO;
import com.ssg.ssgssag.dto.WareHouseZoneDTO;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
@Log4j2
public class InventoryServiceTest {

    @Autowired
    private final InventoryService inventoryService;

    @Autowired
    public InventoryServiceTest(InventoryService inventoryService) {
        this.inventoryService = inventoryService;
    }

    @Test
    public void selectAllInventory() {
        List<InventoryListDTO> voList = inventoryService.selectAllInventory();
        voList.forEach(System.out::println);
    }

    @Test
    public void selectAllWareHouseZone() {
        List<WareHouseZoneDTO> dtoList = inventoryService.selectAllWareHouseZone();
        dtoList.forEach(System.out::println);
    }

    @Test
    public void selectCategoryHierarchy() {
        List<CategoryFilterDTO> dtoList = inventoryService.selectCategoryHierarchy();
        dtoList.forEach(System.out::println);
    }

    @Test
    public void getInventoryByCategoryAndWarehouse() {

        InventorySearchDTO inventorySearchDTO = InventorySearchDTO.builder()
                .vMainCategoryNm("스포츠 용품")
//                .vSubCategoryNm("스마트폰")
//                .vDetailCategoryNm("갤럭시 시리즈")
//                .vWarehouseNm("부산 수출입 창고")
//                .vZoneNm("B-2구역")
                .build();

        List<InventoryListDTO> dtoList1 = inventoryService.getInventoryByCategoryAndWarehouse(inventorySearchDTO);
        log.info("조건 3 : 카테고리, 창고");
        dtoList1.forEach(System.out::println);



    }
}