package com.ssg.ssgssag.controller;

import com.ssg.ssgssag.dto.BestCategoryDTO;
import com.ssg.ssgssag.dto.BestProductDTO;
import com.ssg.ssgssag.dto.DailyPurchaseCountDTO;
import com.ssg.ssgssag.dto.IncomingDTO;
import com.ssg.ssgssag.dto.StatusCountDTO;
import com.ssg.ssgssag.service.DashboardService;
import io.swagger.v3.oas.annotations.Operation;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Log4j2
@Controller
@RequiredArgsConstructor
@RequestMapping("/")
public class DashboardController {

    private final DashboardService dashboardService;

    @GetMapping()
    @Operation(summary = "대시 보드 정보 조회", description = "대시 보드 내 필요 데이터를 조회합니다.")
    public String showDashboardPage(Model model) {
        log.info("Load dashboard page");

        List<StatusCountDTO> statusCountDTOList = dashboardService.getAllStatusCount();
        List<BestProductDTO> bestProductDTOList = dashboardService.getBestProducts();
        List<DailyPurchaseCountDTO> dailyPurchaseCountDTOList = dashboardService.getDailyPurchaseStatistics();
        List<BestCategoryDTO> bestCategoryDTOList = dashboardService.getBestCategoryList();

        log.info(bestCategoryDTOList);

        model.addAttribute("incoming", statusCountDTOList.get(0).getCnt());
        model.addAttribute("outgoing", statusCountDTOList.get(1).getCnt());
        model.addAttribute("purchase", statusCountDTOList.get(2).getCnt());
        model.addAttribute("order", statusCountDTOList.get(3).getCnt());
        model.addAttribute("inventory", statusCountDTOList.get(4).getCnt());
        model.addAttribute("warehouse", statusCountDTOList.get(5).getCnt());

        model.addAttribute("bestProducts", bestProductDTOList);

        // 라인 차트 데이터 준비
        List<String> purchaseDates = dailyPurchaseCountDTOList.stream()
            .map(dto -> new SimpleDateFormat("yyyy-MM-dd").format(dto.getPurchaseDate()))
            .collect(Collectors.toList());
        List<Integer> dailyPurchases = dailyPurchaseCountDTOList.stream()
            .map(DailyPurchaseCountDTO::getDailyPurchaseCount)
            .collect(Collectors.toList());

        model.addAttribute("purchaseDates", purchaseDates);
        model.addAttribute("dailyPurchases", dailyPurchases);

        //인기 카테고리 파이 차트 데이터 준비
        List<String> categoryNames = bestCategoryDTOList.stream()
            .map(BestCategoryDTO::getCategoryName)
            .collect(Collectors.toList());
        List<Integer> categoryCounts = bestCategoryDTOList.stream()
            .map(BestCategoryDTO::getCategoryCnt)
            .collect(Collectors.toList());

        model.addAttribute("categoryNames", categoryNames);
        model.addAttribute("categoryCounts", categoryCounts);

        return "main/main";
    }
}
