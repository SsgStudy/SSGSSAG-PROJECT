package com.ssg.ssgssag.controller;

import com.ssg.ssgssag.dto.ProductDTO;
import com.ssg.ssgssag.dto.WareHouseDTO;
import com.ssg.ssgssag.service.ProductService;
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
@RequestMapping("/product")
public class ProductController {

  private final ProductService productService;

  @GetMapping()
  @Operation(summary = "상품 목록 조회", description = "모든 상품 목록을 조회합니다.")
  public String showProductListPage(Model model) {

    log.info("Product controller test");

    List<ProductDTO> productList = productService.getAllProduct();

    log.info(productList);
    model.addAttribute("productList", productList);

    return "product/product-list";

  }

}
