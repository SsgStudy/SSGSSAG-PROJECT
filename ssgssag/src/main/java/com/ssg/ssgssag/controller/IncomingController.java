package com.ssg.ssgssag.controller;

import com.ssg.ssgssag.dto.IncomingDTO;
import com.ssg.ssgssag.dto.IncomingDetailDTO;
import com.ssg.ssgssag.dto.IncomingProductUpdateRequestDTO;
import com.ssg.ssgssag.dto.OrderSupplierDTO;
import com.ssg.ssgssag.service.IncomingService;
import io.swagger.v3.oas.annotations.Operation;
import java.util.Date;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Log4j2
@Controller
@RequiredArgsConstructor
@RequestMapping("/incoming")
public class IncomingController {

    private final IncomingService incomingService;

    //입고 현황 리스트 조회
    @GetMapping("/list")
    @Operation(summary = "입고 현황 리스트 페이지 이동", description = "입고 리스트 페이지 이동")
    public String showIncomingListPage(Model model) {
        log.info("incoming controller test");

        List<IncomingDTO> incomingList = incomingService.getAllIncomingProductsWithDetails();
        model.addAttribute("incomingList", incomingList);

        return "incoming/incoming-list";
    }

    //입고 현황 리스트 동적 필터링 조회
    @PostMapping("/list/filter")
    @Operation(summary = "입고 현황 리스트 필터링 조회", description = "기간,창고,거래처,상태로 입고 현황 리스트 필터링 조회")
    @ResponseBody
    public ResponseEntity<List<IncomingDTO>> getIncomingListWithFilters(
        @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date startDate,
        @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date endDate,
        @RequestParam String warehouseCd,
        @RequestParam String supplierNm,
        @RequestParam String status) {

        log.info("Filtering incoming list with startDate: {}, endDate: {}, warehouseCd: {}, supplierNm: {}, status: {}",
            startDate, endDate, warehouseCd, supplierNm, status);

        List<IncomingDTO> filteredList = incomingService.getAllIncomingProductsWithDetailsByOption(
            startDate, endDate, warehouseCd, supplierNm, status);

        return ResponseEntity.ok(filteredList);
    }
    //입고 미승인 리스트 동적 필터링 조회
    @PostMapping("/list/unconfirm-filter")
    @Operation(summary = "입고 미승인 리스트 필터링 조회", description = "기간,창고,거래처,상태로 입고 미승인 리스트 필터링 조회")
    @ResponseBody
    public ResponseEntity<List<IncomingDTO>> getUnconfirmIncomingListWithFilters(
        @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date startDate,
        @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date endDate,
        @RequestParam String warehouseCd,
        @RequestParam String supplierNm,
        @RequestParam String status) {

        log.info("Filtering incoming list with startDate: {}, endDate: {}, warehouseCd: {}, supplierNm: {}, status: {}",
            startDate, endDate, warehouseCd, supplierNm, status);

        List<IncomingDTO> filteredList = incomingService.getAllUnconfirmIncomingProductsWithDetailsByOption(
            startDate, endDate, warehouseCd, supplierNm, status);

        return ResponseEntity.ok(filteredList);
    }

    //발주 입고 등록
    @GetMapping("/register")
    @Operation(summary = "발주 입고 등록 페이지 이동", description = "미등록 리스트와 함께 발주 입고 페이지 이동")
    public String showOrderedIncomingRegisterPage(Model model) {
        log.info("incoming controller test");

        List<IncomingDTO> incomingList = incomingService.getAllIncomingProgressProductsWithDetails();
        model.addAttribute("incomingList", incomingList);

        return "incoming/ordered-incoming-register";
    }

    //입고 승인
    @GetMapping("/confirm")
    @Operation(summary = "입고 승인 처리 페이지 이동", description = "리스트와 함께 입고 승인페이지 이동")
    public String showIncomingConfirmPage(Model model) {
        log.info("incoming controller test");

        List<IncomingDTO> incomingList = incomingService.getAllIncomingProgressProductsWithDetails();
        model.addAttribute("incomingList", incomingList);

        return "incoming/incoming-confirm";
    }

    //입고 상세 조회
    @GetMapping("/details/{pkIncomingProductSeq}")
    @Operation(summary = "입고 상세 조회", description = "코드로 입고 상세 정보 조회")
    @ResponseBody
    public IncomingDetailDTO getIncomingDetailByPk(@PathVariable String pkIncomingProductSeq) {
        return incomingService.getIncomingDetailByCode(pkIncomingProductSeq);
    }

    //입고 승인(확정)
    @PostMapping("/confirm")
    @Operation(summary = "입고 승인 처리", description = "입고 최종 승인 처리")
    @ResponseBody
    public ResponseEntity<?> confirmIncomingProducts(@RequestBody List<String> pkIncomingProductSeqs) {
        incomingService.confirmIncomingProducts(pkIncomingProductSeqs);
        return ResponseEntity.ok().build();
    }

    //매입 거래처 리스트 조회
    @GetMapping("/supplier")
    @Operation(summary = "매입 거래처 리스트 조회", description = "모든 매입 거래처 이름 조회")
    @ResponseBody
    public List<OrderSupplierDTO> getAllOrderSupplierName() {
        return incomingService.getAllOrderSupplierName();
    }
    @PostMapping("/update-status-for-register")
    @Operation(summary = "입고 상태 업데이트", description = "입고 상태를 확정으로 업데이트")
    public ResponseEntity<?> updateIncomingProductStatusForRegister(@RequestBody List<IncomingProductUpdateRequestDTO> updateRequests) {
        try {
            for (IncomingProductUpdateRequestDTO request : updateRequests) {
                incomingService.fetchIncomingProductStatusForRegister(
                    request.getPkIncomingProductSeq(),
                    request.getVzoneCd(),
                    request.getDtIncomingProductDate()
                );
            }
            return ResponseEntity.ok().body("입고 상태가 성공적으로 업데이트되었습니다.");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("입고 상태 업데이트 중 오류가 발생했습니다: " + e.getMessage());
        }
    }

    @GetMapping("/zones/{warehouseCd}")
    @Operation(summary = "창고 구역 정보 조회", description = "창고 코드로 창고 구역 정보를 조회")
    @ResponseBody
    public ResponseEntity<?> getZonesByWarehouseCode(@PathVariable String warehouseCd) {
        List<String> zones = incomingService.getZonesByWarehouseCode(warehouseCd);
        return ResponseEntity.ok(zones);

    }
}
