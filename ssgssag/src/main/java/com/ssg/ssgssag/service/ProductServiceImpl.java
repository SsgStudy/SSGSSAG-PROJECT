package com.ssg.ssgssag.service;

import com.ssg.ssgssag.dto.ProductDTO;
import com.ssg.ssgssag.mapper.ProductMapper;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Log4j2
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService{

  @Autowired
  private final ProductMapper productMapper;

  @Override
  public List<ProductDTO> getAllProduct() {
    return productMapper.selectAllProduct();
  }
}
