package com.ssg.ssgssag.mapper;

import com.ssg.ssgssag.dto.ProductDTO;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface ProductMapper {

  List<ProductDTO> selectProductList(@Param("offset")int offset, @Param("size")int size);
  int selectProductCount();
}
