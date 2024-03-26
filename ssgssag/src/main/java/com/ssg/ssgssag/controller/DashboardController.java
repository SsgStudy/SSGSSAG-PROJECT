package com.ssg.ssgssag.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ssg.ssgssag.dto.AnalysisAndSuggestionDTO;
import com.ssg.ssgssag.dto.BestCategoryDTO;
import com.ssg.ssgssag.dto.BestProductDTO;
import com.ssg.ssgssag.dto.ChatGptResponseDto;
import com.ssg.ssgssag.dto.DailyPurchaseCountDTO;
import com.ssg.ssgssag.dto.IncomingDTO;
import com.ssg.ssgssag.dto.QuestionRequestDto;
import com.ssg.ssgssag.dto.StatusCountDTO;
import com.ssg.ssgssag.service.ChatGptService;
import com.ssg.ssgssag.service.DashboardService;
import io.swagger.v3.oas.annotations.Operation;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Log4j2
@Controller
@RequiredArgsConstructor
@RequestMapping("/")
public class DashboardController {

    @Autowired
    private final DashboardService dashboardService;
    @Autowired
    private final ChatGptService chatGptService;


    List<BestCategoryDTO> bestCategoryDTOList = null;
    List<BestCategoryDTO> worstCategoryDTOList = null;
    ObjectMapper objectMapper = new ObjectMapper();

    @GetMapping()
    @Operation(summary = "대시보드 정보 조회", description = "대시보드 내 필요 데이터를 조회합니다.")
    public String showDashboardPage(Model model) {
        log.info("Load dashboard page");

        List<StatusCountDTO> statusCountDTOList = dashboardService.getAllStatusCount();
        List<BestProductDTO> bestProductDTOList = dashboardService.getBestProducts();
        List<DailyPurchaseCountDTO> dailyPurchaseCountDTOList = dashboardService.getDailyPurchaseStatistics();
        bestCategoryDTOList = dashboardService.getBestCategoryList();
        worstCategoryDTOList = dashboardService.getWorstCategoryList();

        String bestCategoryJson;
        try {
            bestCategoryJson = objectMapper.writeValueAsString(bestCategoryDTOList);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        String worstCategoryJson;
        try {
            worstCategoryJson = objectMapper.writeValueAsString(worstCategoryDTOList);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }


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
        model.addAttribute("bestCategoryJson", bestCategoryJson);

        //인기 카테고리 파이 차트 데이터 준비
        List<String> categoryNames = bestCategoryDTOList.stream()
                .map(BestCategoryDTO::getCategoryName)
                .collect(Collectors.toList());
        List<Integer> categoryCounts = bestCategoryDTOList.stream()
                .map(BestCategoryDTO::getCategoryCnt)
                .collect(Collectors.toList());

        model.addAttribute("categoryNames", categoryNames);
        model.addAttribute("categoryCounts", categoryCounts);

        //비인기 카테고리 파이 차트 데이터 준비
        List<String> worstCategoryNames = worstCategoryDTOList.stream()
                .map(BestCategoryDTO::getCategoryName)
                .toList();
        List<Integer> worstCategoryCounts = worstCategoryDTOList.stream()
                .map(BestCategoryDTO::getCategoryCnt)
                .toList();

        model.addAttribute("worstCategoryNames", worstCategoryNames);
        model.addAttribute("worstCategoryCounts", worstCategoryCounts);

        List<Double> predictedPurchases = predictPurchase(dailyPurchases);
        model.addAttribute("predictedPurchases", predictedPurchases);
        log.info(predictedPurchases);
        return "main/main";
    }

    //    @PostMapping("/question")
//    @ResponseBody
//    public ChatGptResponseDto sendQuestion(@RequestBody QuestionRequestDto requestDto,
//        Model model) {
//        log.info("gpt 요청값 : "+requestDto.toString());
//        ChatGptResponseDto response = chatGptService.askQuestion(requestDto);
//        response.getChoices().forEach(choice -> {
//            String contentJson = choice.getMessage().getContent();
//            AnalysisAndSuggestionDTO analysisAndSuggestion = parseContentToJson(contentJson);
//            model.addAttribute("analysis", analysisAndSuggestion.getAnalysis());
//            model.addAttribute("suggestion", analysisAndSuggestion.getSuggestion());
//        });
//        log.info("gpt 호출함.");
//        log.info(model.getAttribute("analysis"));
//        log.info(model.getAttribute("suggestion"));
//
//        return response;
//    }
    @PostMapping("/question")
    @ResponseBody
    public ResponseEntity<?> sendQuestion(@RequestBody QuestionRequestDto requestDto) {
        log.info("GPT 요청값: " + requestDto.toString());
        ChatGptResponseDto response = chatGptService.askQuestion(requestDto);

        List<AnalysisAndSuggestionDTO> analysisAndSuggestions = response.getChoices().stream()
                .map(choice -> parseContentToJson(choice.getMessage().getContent()))
                .toList();

        if (!analysisAndSuggestions.isEmpty()) {
            AnalysisAndSuggestionDTO analysisAndSuggestion = analysisAndSuggestions.get(0);
            Map<String, Object> responseBody = new HashMap<>();
            responseBody.put("analysis", analysisAndSuggestion.getAnalysis());
            responseBody.put("suggestion", analysisAndSuggestion.getSuggestion());

            return ResponseEntity.ok(responseBody);
        } else {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body("No analysis and suggestion available.");
        }
    }


    public AnalysisAndSuggestionDTO parseContentToJson(String contentJson) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.readValue(contentJson, AnalysisAndSuggestionDTO.class);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return new AnalysisAndSuggestionDTO(); // 예외 발생 시 빈 객체 반환
        }
    }

    private List<Double> predictPurchase(List<Integer> dailyPurchases) {
        List<Double> predictions = new ArrayList<>();
        for (Integer purchase : dailyPurchases) {
            predictions.add(purchase.doubleValue());
        }

        int daysCount = dailyPurchases.size();

        for (int futureDay = 1; futureDay <= 7; futureDay++) {
            int[] days = new int[daysCount + futureDay - 1];
            for (int i = 0; i < days.length; i++) {
                days[i] = i + 1;
            }

            double[] stocks = predictions.stream().mapToDouble(Double::doubleValue).toArray();
            double[] model = linearRegression(days, stocks);

            double predictedStock = model[0] * (daysCount + futureDay) + model[1];
            predictedStock = Math.round(predictedStock * 10) / 10.0;

            predictions.add(predictedStock);

            System.out.printf("예측된 %d일차 판매수량: %.2f개\n", daysCount + futureDay, predictedStock);
        }
        return predictions;
    }


    private static double[] linearRegression(int[] x, double[] y) {
        int n = x.length;
        double sumX = 0, sumY = 0, sumXx = 0, sumXy = 0;

        for (int i = 0; i < n; i++) {
            sumX += x[i];
            sumY += y[i];
            sumXx += x[i] * x[i];
            sumXy += x[i] * y[i];
        }

        double slope = (n * sumXy - sumX * sumY) / (n * sumXx - sumX * sumX);
        double intercept = (sumY - slope * sumX) / n;

        return new double[]{slope, intercept};
    }

    @GetMapping("/about")
    public String showAboutPage() {
        return "main/about";
    }

}
