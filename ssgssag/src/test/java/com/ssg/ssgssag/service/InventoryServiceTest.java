package com.ssg.ssgssag.service;

import com.ssg.ssgssag.domain.InventoryVO;
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
        List<InventoryVO> voList = inventoryService.selectAllInventory();
        log.info(voList);
    }
}
