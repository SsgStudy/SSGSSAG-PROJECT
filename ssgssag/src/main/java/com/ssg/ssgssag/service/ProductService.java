package com.ssg.ssgssag.service;

import com.ssg.ssgssag.dto.ProductDTO;
import java.util.List;

public interface ProductService {

  List<ProductDTO> getProductList(int page, int size);

  public int getProductCount();
}
