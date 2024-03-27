package com.ssg.ssgssag.service;

import com.ssg.ssgssag.dto.ProductDTO;
import com.ssg.ssgssag.mapper.ProductMapper;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
@Log4j2
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {
  @Autowired
  private final ProductMapper productMapper;

  @Cacheable(value = "products")
  @Override
  public List<ProductDTO> getProductList(int page, int size) {
    int offset = (page - 1) * size;
    return productMapper.selectProductList(offset, size);
  }

  @Override
  public int getProductCount() {
    return productMapper.selectProductCount();
  }
}