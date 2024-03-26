package com.ssg.ssgssag.mapper;

import com.ssg.ssgssag.dto.ProductDTO;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ProductMapper {

  List<ProductDTO> selectAllProduct();

}
