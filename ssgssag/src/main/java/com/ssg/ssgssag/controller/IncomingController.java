package com.ssg.ssgssag.controller;

import com.ssg.ssgssag.dto.IncomingDTO;
import com.ssg.ssgssag.dto.IncomingDetailDTO;
import com.ssg.ssgssag.dto.IncomingProductUpdateRequestDTO;
import com.ssg.ssgssag.dto.OrderSupplierDTO;
import com.ssg.ssgssag.service.IncomingService;
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
    public String showIncomingListPage(Model model) {
        log.info("incoming controller test");

        List<IncomingDTO> incomingList = incomingService.getAllIncomingProductsWithDetails();
        model.addAttribute("incomingList", incomingList);

        return "incoming/incoming-list";
    }

    //입고 현황 리스트 동적 필터링 조회
    @PostMapping("/list/filter")
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
    public String showOrderedIncomingRegisterPage(Model model) {
        log.info("incoming controller test");

        List<IncomingDTO> incomingList = incomingService.getAllIncomingProgressProductsWithDetails();
        model.addAttribute("incomingList", incomingList);

        return "incoming/ordered-incoming-register";
    }

    //입고 승인
    @GetMapping("/confirm")
    public String showIncomingConfirmPage(Model model) {
        log.info("incoming controller test");

        List<IncomingDTO> incomingList = incomingService.getAllIncomingProgressProductsWithDetails();
        model.addAttribute("incomingList", incomingList);

        return "incoming/incoming-confirm";
    }

    //입고 상세 조회
    @GetMapping("/details/{pkIncomingProductSeq}")
    @ResponseBody
    public IncomingDetailDTO getIncomingDetailByPk(@PathVariable String pkIncomingProductSeq) {
        return incomingService.getIncomingDetailByCode(pkIncomingProductSeq);
    }

    //입고 승인(확정)
    @PostMapping("/confirm")
    @ResponseBody
    public ResponseEntity<?> confirmIncomingProducts(@RequestBody List<String> pkIncomingProductSeqs) {
        incomingService.confirmIncomingProducts(pkIncomingProductSeqs);
        return ResponseEntity.ok().build();
    }

    //매입 거래처 리스트 조회
    @GetMapping("/supplier")
    @ResponseBody
    public List<OrderSupplierDTO> getAllOrderSupplierName() {
        return incomingService.getAllOrderSupplierName();
    }
    @PostMapping("/update-status-for-register")
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
}
