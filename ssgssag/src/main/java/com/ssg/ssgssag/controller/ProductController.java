package com.ssg.ssgssag.controller;

import com.ssg.ssgssag.dto.ProductDTO;
import com.ssg.ssgssag.service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Log4j2
@Controller
@RequiredArgsConstructor
@RequestMapping("/product")
public class ProductController {

    private final ProductService productService;

    @GetMapping()
    @Operation(summary = "상품 목록 조회", description = "모든 상품 목록을 조회합니다.")
    public String showProductListPage(Model model,
        @RequestParam(defaultValue = "1") int page,
        @RequestParam(defaultValue = "10") int size) {
        List<ProductDTO> productList = productService.getProductList(page, size);
        int totalCount = productService.getProductCount();
        int totalPages = (int) Math.ceil((double) totalCount / size);

        int maxPagesToShow = 10;
        int startPage = Math.max(1, page - maxPagesToShow / 2);
        int endPage = Math.min(startPage + maxPagesToShow - 1, totalPages);

        model.addAttribute("productList", productList);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", totalPages);
        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage", endPage);

        return "product/product-list";
    }

}