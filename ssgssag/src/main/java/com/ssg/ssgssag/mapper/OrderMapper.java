package com.ssg.ssgssag.mapper;

import com.ssg.ssgssag.domain.ProductVO;
import org.apache.ibatis.annotations.Mapper;
import java.time.LocalDate;

@Mapper
public interface OrderMapper {
    ProductVO selectOne();
}
