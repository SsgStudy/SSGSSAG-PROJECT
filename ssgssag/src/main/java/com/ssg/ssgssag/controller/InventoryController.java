package com.ssg.ssgssag.controller;

import com.ssg.ssgssag.domain.InventoryVO;
import com.ssg.ssgssag.service.InventoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Log4j2
@Controller
@RequiredArgsConstructor
@RequestMapping("/inventory")
public class InventoryController {

    private final InventoryService inventoryService;

    @GetMapping("/list")
    public String showInventoryListPage(Model model) {
        log.info("[controller] inventory all list");

        List<InventoryVO> inventoryList = inventoryService.selectAllInventory();
        model.addAttribute("inventoryList", inventoryList);

        return "inventory/inventory-list";
    }
}
