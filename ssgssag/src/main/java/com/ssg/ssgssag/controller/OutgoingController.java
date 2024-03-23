package com.ssg.ssgssag.controller;

import com.ssg.ssgssag.domain.OutgoingProductVO;
import com.ssg.ssgssag.dto.IncomingDTO;
import com.ssg.ssgssag.service.OutgoingService;
import io.swagger.v3.oas.annotations.Operation;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Log4j2
@Controller
@RequiredArgsConstructor
@RequestMapping("/outgoing")
public class OutgoingController {

  private final OutgoingService outgoingService;

  @GetMapping()
  @Operation(summary = "출고 목록 조회", description = "전체 출고 목록을 조회합니다.")
  public String showOutgoingListPage(Model model) {
    log.info("outgoing controller test");

    List<OutgoingProductVO> outgoingProductList = outgoingService.getAllOutgoingProductsWithDetails();
    model.addAttribute("outgoingList", outgoingProductList);
log.info(outgoingProductList);
    return "outgoing/outgoing-list";
  }

}
