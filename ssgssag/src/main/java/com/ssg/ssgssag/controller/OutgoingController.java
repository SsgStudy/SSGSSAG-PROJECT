package com.ssg.ssgssag.controller;

import com.ssg.ssgssag.domain.OutgoingProductVO;
import com.ssg.ssgssag.dto.IncomingDTO;
import com.ssg.ssgssag.service.OutgoingService;
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
  public String showOutgoingListPage(Model model) {
    log.info("outgoing controller test");

    List<OutgoingProductVO> outgoingProductList = outgoingService.getAllOutgoingProductsWithDetails();
    model.addAttribute("outgoingList", outgoingProductList);
log.info(outgoingProductList);
    return "outgoing/outgoing-list";
  }

}
