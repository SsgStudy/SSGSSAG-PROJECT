package com.ssg.ssgssag.service;

import com.ssg.ssgssag.dto.ProductDTO;
import java.util.List;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@Log4j2
public class ProductServiceTest {

  @Autowired
  private final ProductService productService;

  @Autowired
  public ProductServiceTest(ProductService productService) {
    this.productService = productService;
  }

//  @Test
//  public void getAllProductTest(){
//
//    List<ProductDTO> productList = productService.getAllProduct();
//    log.info(productList);
//
//  }
}
